import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    //???

    public BooleanSearchEngine(File pdfsDir) throws IOException {
//        File folder = new File("/path/to/folder");
        File[] pdfFiles = pdfsDir.listFiles();
        for (File pdfFile : pdfFiles) {
            if (pdfFile.isFile()) {
                System.out.println(pdfFile.getName());
                var pdfDocument = new PdfDocument(new PdfReader(pdfFile));
                System.out.println(pdfDocument.getNumberOfPages());
                for (int page = 1; page <= pdfDocument.getNumberOfPages(); page++) {
                    var text = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(page));
                    var words = text.split("\\P{IsAlphabetic}+");
                }
            }
        }
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        return Collections.emptyList();
    }
}
