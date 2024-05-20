package BakeOrBreak.Item.Processor;

import BakeOrBreak.GameData.GameMngr;
import BakeOrBreak.GameData.LevelStatistic;
import BakeOrBreak.Item.DragIngredient;
import BakeOrBreak.Item.IngredientData;
import BakeOrBreak.Item.BaseIngredient;
import BakeOrBreak.GameData.CustomerStatistic;
import BakeOrBreak.GameData.Difficulty;
import BakeOrBreak.GameData.OrderStatistic;
import BakeOrBreak.Item.Product;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Customer extends ItemReceiver {

    private final ArrayList<IngredientData> order;
    private final String[] allergies;
    private final long orderTime;
    private LevelStatistic levelData;
    private double prepTime;
    private CustomerStatistic customerStatistic;
    private static GameMngr gameManager = GameMngr.getGameManager();

    public Customer(ArrayList<Product> order, String[] allergies, long waitTime) {
        this.order = new ArrayList<>();
        this.allergies = allergies;
        this.orderTime = GameMngr.getGameManager().getLevelMngr().getTime();
        this.prepTime = waitTime;

        for (Product p : order) {
            this.order.add(p.getIngredientData());
        }

        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/customer.png")));
        itemZones.add(this);
    }

    public Customer(ArrayList<Product> order, String[] allergies) {
        this.order = new ArrayList<>();
        this.allergies = allergies;
        this.orderTime = GameMngr.getGameManager().getLevelMngr().getTime();
        this.prepTime = 0;
        
        for (Product p : order) {
            prepTime += p.getIngredientData().getPrepTimeSec();
        }
        
        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/customer.png")));
        itemZones.add(this);
    }

    public Customer(Difficulty d) {
        order = new ArrayList<>();
        orderTime = GameMngr.getGameManager().getLevelMngr().getTime();
        allergies = BaseIngredient.getAllergen(d.getAllergyCount());

        for (int i = 0; i < d.getOrderSize(); i++) {
            Product p = Product.getProduct();
            prepTime += p.getIngredientData().getPrepTimeSec();
            order.add(p.getIngredientData());
        }

        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/customer.png")));
        itemZones.add(this);
    }
    
    public OrderStatistic rateProduct(Product product) throws Exception {
        long currentTime = GameMngr.getGameManager().getLevelMngr().getTime();
        int score = 2;
        boolean allergicReaction = false;
        
        double netSpeed = (int) (100 * (currentTime - orderTime) / prepTime);

        if (!order.remove(product.getIngredientData())) {
            order.remove(0);
        }

        for (String allergy : allergies) { // Test for allergies
            if (Arrays.asList(product.getIngredientData().getAllergens()).contains(allergy)) {
                allergicReaction = true;
            }
        }
        
        score += allergicReaction ? 0 : 1;
        score += netSpeed < 0.90 ? 1 : 0;
        score += netSpeed < 0.60 ? 1 : 0;

        OrderStatistic orderStatistic = new OrderStatistic(score, netSpeed, allergicReaction);
        
        customerStatistic.getorderStatistics().add(orderStatistic);
        return orderStatistic;
    }

    @Override
    public void put(DragIngredient ingredient) {
        try {
            rateProduct((Product) ingredient);
            ((Pane) ingredient.getParent()).getChildren().remove(ingredient);
        } catch (Exception e) {
            ingredient.returnToOriginalPos();
        }
    }

    public CustomerStatistic getCustomerStatistic() {
        return customerStatistic;
    }

    public ArrayList<IngredientData> getOrder() {
        return order;
    }
    
    public double getPrepTime() {
        return prepTime;
    }
}
