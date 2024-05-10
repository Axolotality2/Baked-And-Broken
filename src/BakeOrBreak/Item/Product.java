package BakeOrBreak.Item;

import BakeOrBreak.Item.DragIngredient;
import BakeOrBreak.Item.ProductData;
import BakeOrBreak.GameData.WeightDist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Product extends DragIngredient {

    private static final ArrayList<Product> PROD_LIST = new ArrayList<>();
    private static WeightDist<Product> PROD_DIST = new WeightDist<>();
    private static double netWeight = 0d;
    private final ProductData productData;

    // Used for converting intermediates into complete products (i.e. Step)
    public Product(DragIngredient ingredient) {
        super(ingredient.getIngredientData());
        this.productData = new ProductData(ingredient.getIngredientData(), 0);
    }

    // Used for selecting products (i.e. Customer)
    public Product(ProductData productData) {
        super(productData);
        this.productData = productData;
    }

    public static void initPROD_LIST(String filepath) throws FileNotFoundException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader ingFile = new FileReader(filepath);
        String contents = "";
        ProductData[] jsonProds;

        int i;
        while ((i = ingFile.read()) != -1) {
            contents += (char) i;
        }

        jsonProds = gson.fromJson(contents, ProductData[].class);
        for (ProductData jsonProd : jsonProds) {
            Product p = new Product(jsonProd);
            PROD_LIST.add(p);
        }
    }

    public static void setDistribution(int maxComplexity) {
        int i = 0;
        Product p = PROD_LIST.get(i);
        PROD_DIST = new WeightDist<>();
        while (p.getIngredientData().getComplexity() <= maxComplexity) {
            PROD_DIST.addEntry(p, p.getIngredientData().getComplexity());
            i++;
        }
    }
    
    public static Product getProduct() {
        return PROD_DIST.pickRandom();
    }

    public static Product[] getProduct(int count) {
        return PROD_DIST.pickRandom(count);
    }
    
    @Override
    public ProductData getIngredientData() {
        return productData;
    }
}
