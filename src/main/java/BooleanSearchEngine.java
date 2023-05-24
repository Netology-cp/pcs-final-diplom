import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    Map<String, List<PageEntry>> indexing = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) {
        String[] pdfNames = pdfsDir.list();
        for (String pdfName : pdfNames) {
            try (var doc = new PdfDocument(new PdfReader(pdfName))) {
                for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                    String text = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                    var words = text.split("\\P{IsAlphabetic}+");
                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        word = word.toLowerCase();
                        freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                        PageEntry pe = new PageEntry(pdfName, i, freqs.get(word));

                        if (indexing.containsKey(word)) {
                            List<PageEntry> previousValue = indexing.get(word);
                            Iterator<PageEntry> iterator = previousValue.iterator();
                            while (iterator.hasNext()) {
                                PageEntry pageEntry = iterator.next();
                                if (pageEntry.getPage() == i) {
                                    iterator.remove();
                                }
                            }
                            previousValue.add(pe);
                        } else {
                            List<PageEntry> value = new ArrayList<>();
                            value.add(pe);
                            indexing.put(word, value);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        if (indexing.containsKey(word)) {
            Collections.sort(indexing.get(word));
            return indexing.get(word);
        }
        return Collections.emptyList();
    }
}