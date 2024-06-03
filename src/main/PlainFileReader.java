    package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Rafael Inigo
 */
public class PlainFileReader {
    
    public static String getFileContents(String location) {
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
}
