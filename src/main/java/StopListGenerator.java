import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopListGenerator {

    static List<String> loadFromTxtFile(File FILENAME) {
        List<String> listStr = new ArrayList<>(3);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String promVar;
            while ((promVar = reader.readLine()) != null) {
                listStr.add(promVar);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return listStr;
    }
}
