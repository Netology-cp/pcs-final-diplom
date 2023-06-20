import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfWork {


    public static List<String> getDocList(File pdfsDir) {

        List<String> result = new ArrayList<>();

        if (pdfsDir.exists() && pdfsDir.isDirectory()) {
            for (File file: pdfsDir.listFiles()) {
                String fileName = file.getName();
                String fullFileName = file.getPath() + "/" + fileName;
                String[] splitedFileName = fileName.split("\\.");
                if (!file.isDirectory() && splitedFileName.length > 1 && splitedFileName[splitedFileName.length-1].equals("pdf")) {
                    result.add(fileName);
                }
            }
        }

        return result;
    }


}
