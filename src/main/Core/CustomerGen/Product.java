package main.Core.CustomerGen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Product {

    private static WeightDist<Product> validProducts;
    private static final int BASE_TIME = 2500, ADD_TIME = 500;
    private String name;
    private Set<String> allergens;
    private int complexity;
    private long prepTimeMillis;

    public Product(String name, String[] allergens, int complexity, long prepTimeMillis) {
        this.name = name;
        this.allergens = new HashSet<>(Arrays.asList(allergens));
        this.complexity = complexity;
        this.prepTimeMillis = prepTimeMillis;
    }

    public Product(String name, int complexity, long prepTimeMillis) {
        this(name, new String[0], complexity, prepTimeMillis);
    }

    public Product(String name, String[] allergens, int complexity) {
        this(name, allergens, complexity, BASE_TIME + ADD_TIME * complexity);
    }

    public Product(String name, int complexity) {
        this(name, new String[0], complexity, BASE_TIME + ADD_TIME * complexity);
    }

    public static void setValidProducts(String location) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Product[] products = gson.fromJson(getFileContents(location), Product[].class);
        validProducts = new WeightDist<>();
        
        for (Product product : products) {
            validProducts.addEntry(product, product.complexity);
        }
    }
    
    private static String getFileContents(String location) {
        FileReader ingFile = null;
        String contents = "";
        
        try {
            ingFile = new FileReader(location);

            for (int i = ingFile.read(); i != -1; i = ingFile.read()) {
                contents += (char) i;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ingFile.close();
            } catch (IOException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return contents;
    }

    public static Product getProduct() {
        return validProducts.pickRandom();
    }
    
    /**
     * @return the products
     */
    public static ArrayList<Product> getValidProducts() {
        return validProducts.getValues();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the allergens
     */
    public Set<String> getAllergens() {
        return allergens;
    }

    /**
     * @return the complexity
     */
    public int getComplexity() {
        return complexity;
    }

    /**
     * @return the prepTimeMillis
     */
    public long getPrepTimeMillis() {
        return prepTimeMillis;
    }
}
