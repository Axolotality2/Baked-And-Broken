package BakeOrBreak.model;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Customer extends ItemReceiver {

    private final ArrayList<IngredientData> order;
    private final String[] allergies;
    private final long orderTime;
    private long prepTime, leaveTime;
    private int netRating, netSpeed;
    private static GameMngr gameManager = GameMngr.getGameManager();

    public Customer(ArrayList<Product> order, String[] allergies, long leaveTime) {
        this.order = new ArrayList<>();
        this.allergies = allergies;
        this.leaveTime = leaveTime;
        orderTime = GameMngr.getGameManager().getLevelMngr().getTime();

        for (Product p : order) {
            this.order.add(p.getIngredientData());
        }
    }

    public Customer(ArrayList<Product> order, String[] allergies) {
        this(order, allergies, 0);
        for (Product p : order) {
            prepTime += p.getPrepTime();
        }

        leaveTime = orderTime + prepTime;
    }

    public Customer(Difficulty d) {
        order = new ArrayList<>();
        orderTime = GameMngr.getGameManager().getLevelMngr().getTime();
        allergies = BaseIngredient.getALLERGENS().pickRandom(d.getRandAllergyCount()); 

        for (int i = 0; i < d.getRandOrderSize(); i++) {
            Product[] orderable = Product.filterByComplexity(d.getRandComplexity());
            int index = (int) (Math.random() * (orderable.length - 1));

            order.add(orderable[index].getIngredientData());
            prepTime += orderable[index].getPrepTime();
        }

        leaveTime = orderTime + prepTime;

        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/customer.png")));
        itemZones.add(this);
    }

    public int rateProduct(Product product) throws Exception {
        long currentTime = GameMngr.getGameManager().getLevelMngr().getTime();
        int score = 2;
        netSpeed = (int) (100 * (currentTime - orderTime) / prepTime);

        if (!order.remove(product.getIngredientData())) {
            order.remove(0);
        }

        for (String allergy : allergies) { // Test for allergies
            score = Arrays.asList(product.getIngredientData().getAllergens()).contains(allergy) ? 2 : 3;
        }

        score += netSpeed < 0.90 ? 1 : 0;
        score += netSpeed < 0.60 ? 1 : 0;

        this.netRating += score;
        return score;
    }

    @Override
    public void put(DragIngredient ingredient) {
        try {
            rateProduct((Product) ingredient);
            ((Pane) ingredient.getParent()).getChildren().remove(ingredient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public float getOverallRating() {
        return netRating / order.size();
    }

    public float getOverallSpeed() {
        return netSpeed / order.size();
    }

    public long getLeaveTime() {
        return leaveTime;
    }

    public ArrayList<IngredientData> getOrder() {
        return order;
    }

    public void setLeaveTime(long leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setLeaveTime(float multiplier) {
        this.leaveTime *= multiplier;
    }
}
