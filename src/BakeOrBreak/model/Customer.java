package BakeOrBreak.model;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Customer extends ItemReceiver {

    private final ArrayList<Product> order;
    private final BaseIngredient[] allergies;
    private final long orderTime;
    private long prepTime, leaveTime;
    private int netRating, netSpeed;
    private static GameMngr gameManager = GameMngr.getGameManager();

    public Customer(Difficulty d) {
        order = new ArrayList<>();
        orderTime = GameMngr.getGameManager().getLevelMngr().getTime();
        allergies = BaseIngredient.getALLERGENS().pickRandom(d.getAllergyTable().pickRandom());

        for (int i = 0; i < d.getOrderSizeTable().pickRandom(); i++) {
            Product[] orderable = Product.filterByComplexity(d.getComplexityTable().pickRandom());
            int index = (int) (Math.random() * (orderable.length - 1));

            order.add(orderable[index]);
            prepTime += orderable[index].getPrepTime();
        }

        leaveTime = orderTime + prepTime;

        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/customer.png")));
        itemZones.add(this);
    }

    public Customer(ArrayList<Product> order, BaseIngredient[] allergies, long leaveTime) {
        this.order = order;
        this.allergies = allergies;
        this.leaveTime = leaveTime;
        orderTime = GameMngr.getGameManager().getLevelMngr().getTime();
    }

    public Customer(ArrayList<Product> order, BaseIngredient[] allergies) {
        this.order = order;
        this.allergies = allergies;
        orderTime = gameManager.getLevelMngr().getTime();

        for (Product p : order) {
            prepTime += p.getPrepTime();
        }

        leaveTime = orderTime + prepTime;
    }

    public int rateProduct(Product product) throws Exception {
        long currentTime = GameMngr.getGameManager().getLevelMngr().getTime();
        int score = 2;
        netSpeed = (int) (100 * (currentTime - orderTime) / prepTime);

        if (!order.remove(product)) {
            order.remove(0);
        }

        for (BaseIngredient allergy : allergies) // Test for allergies
        {
            score = Arrays.asList(product.getAllergens()).contains(allergy) ? 2 : 3;
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

    public ArrayList<Product> getOrder() {
        return order;
    }

    public void setLeaveTime(long leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setLeaveTime(float multiplier) {
        this.leaveTime *= multiplier;
    }
}
