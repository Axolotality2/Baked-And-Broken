package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Rafael Inigo
 * @param <T>
 */
public class GsonFileReader<T> {
    
    public String getFileContents(String location) {
        FileReader ingFile = null;
        String contents = "";
        
        try {
            ingFile = new FileReader(location);

            for (int i = ingFile.read(); i != -1; i = ingFile.read()) {
                contents += (char) i;
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ingFile.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return contents;
    }
    
    public void fillArrList(ArrayList<T> arrayList, String location) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Type type = new TypeToken<T>(){}.getType();
        T[] contents = gson.fromJson(getFileContents(location), type);
        arrayList.clear();
        arrayList.addAll(Arrays.asList(contents));
    }
}
