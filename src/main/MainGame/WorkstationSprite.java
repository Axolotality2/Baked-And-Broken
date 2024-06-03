package main.MainGame;

import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import main.Core.FoodProcessing.Ingredient;
import main.Core.FoodProcessing.Workstation;

/**
 *
 * @author Rafael Inigo
 */
public class WorkstationSprite extends Receptacle {

    private static final double SPAWN_DIST_PERCENT = 0.75;
    private static final ArrayList<WorkstationSprite> stationSprites = new ArrayList<>();
    private static double bottomOffset = 0d;
    private static AnchorPane counterPane;
    private static FlowPane iconTray;
    private final Workstation station;
    private final ImageView icon;
    private boolean active;

    public WorkstationSprite(Workstation station) {
        super(station.getImage());
        this.station = station;
        this.icon = new ImageView(station.getIcon());
        this.active = false;

        addIconControls();
        addStationControls();
        stationSprites.add(this);
    }

    public void draw() {
        iconTray.getChildren().add(icon);

        AnchorPane.setBottomAnchor(this, snapToGrid(bottomOffset));
        AnchorPane.setLeftAnchor(this, snapToGrid((counterPane.getBoundsInParent().getWidth() - station.getImage().getWidth()) / 2));
        counterPane.getChildren().add(this);
    }

    private void addIconControls() {
        icon.addEventHandler(MouseEvent.MOUSE_CLICKED, (final MouseEvent event) -> {
            activate();
        });

        icon.addEventHandler(MouseEvent.MOUSE_ENTERED, (final MouseEvent event) -> {
            icon.setOpacity(1);
        });

        icon.addEventHandler(MouseEvent.MOUSE_EXITED, (final MouseEvent event) -> {
            System.out.println(active);
            
            if (!active) {
                icon.setOpacity(0.6);
            }
        });
    }

    private void addStationControls() {
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    station.use();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                releaseContents();
            }
        });
    }

    private void releaseContents() {
        double spawnTranslate = 0;

        for (Ingredient i : station.getContents()) {
            DraggableIngredient di = new DraggableIngredient(i);
            Bounds thisBounds = this.localToScene(this.getBoundsInLocal());
            double ingredientWidth = di.getIngredient().getImg().getWidth();
            double center = (thisBounds.getWidth() - ingredientWidth) / 2;

            di.draw(new Point2D(
                    snapToGrid(thisBounds.getMinX() + center + spawnTranslate),
                    snapToGrid(thisBounds.getMaxY())
            ));

            di.checkForIntersections();
            spawnTranslate = spawnTranslate <= 0 ? -spawnTranslate + ingredientWidth * SPAWN_DIST_PERCENT : -spawnTranslate;
        }
    }

    public void activate() {
        for (Node n : counterPane.getChildren()) {
            if (n instanceof WorkstationSprite) {
                ((WorkstationSprite) n).active = false;
                ((WorkstationSprite) n).icon.setOpacity(0.6);
                ((WorkstationSprite) n).setImage(null);
            }
        }

        active = true;
        setImage(station.getImage());
        icon.setOpacity(1);
    }

    @Override
    public void put(DraggableIngredient ingredient) {
        station.insert(ingredient.getIngredient());
    }

    private double snapToGrid(double x) {
        return (double) (3 * Math.round(x / 3));
    }

    /**
     * @return the bottomOffset
     */
    public static double getBottomOffset() {
        return bottomOffset;
    }

    /**
     * @return the counterPane
     */
    public static AnchorPane getCounterPane() {
        return counterPane;
    }

    /**
     * @return the iconTray
     */
    public static FlowPane getIconTray() {
        return iconTray;
    }

    /**
     * @return the station
     */
    public Workstation getStation() {
        return station;
    }

    /**
     * @return the icon
     */
    public ImageView getIcon() {
        return icon;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param aBottomOffset the bottomOffset to set
     */
    public static void setBottomOffset(double aBottomOffset) {
        bottomOffset = aBottomOffset;
    }

    /**
     * @param aCounterPane the counterPane to set
     */
    public static void setCounterPane(AnchorPane aCounterPane) {
        counterPane = aCounterPane;
    }

    /**
     * @param aIconTray the iconTray to set
     */
    public static void setIconTray(FlowPane aIconTray) {
        iconTray = aIconTray;
    }

    /**
     * @return the stationSprites
     */
    public static ArrayList<WorkstationSprite> getStationSprites() {
        return stationSprites;
    }

}
