import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooleanSearchEngine implements SearchEngine {
    private Memory memory = new Memory();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        if (pdfsDir.isDirectory()) {
            for (File file : pdfsDir.listFiles()) {
                PdfDocument doc = new PdfDocument(new PdfReader(file));
                textReap(doc, file);
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        Collections.sort(memory.getMainMap().get(word));
        return memory.getMainMap().get(word);
    }

    public void textReap(PdfDocument doc, File file) {
        int lastPage = doc.getNumberOfPages();
        for (int i = 0; i < lastPage; i++) {
            Map<String, PageEntry> countMap = new HashMap<>();
            String[] arrStr = PdfTextExtractor
                    .getTextFromPage(doc.getPage(i + 1))
                    .split("\\P{IsAlphabetic}+");
            for (String item : arrStr) {
                if (item.isEmpty()) {
                    continue;
                }
                item = item.toLowerCase();
                countMap.put(item, countMap
                        .getOrDefault(item, new PageEntry(file.getName(), i + 1, 0))
                        .addCountAndGetPE());
            }
            memory.addToMemory(countMap);
        }
    }

}
