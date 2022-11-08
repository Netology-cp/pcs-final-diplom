import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private Map<String, List<PageEntry>> searchResults = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        File[] listOfPdfs = pdfsDir.listFiles();
        if (listOfPdfs != null) {
            for (var pdfFile : listOfPdfs) {
                var doc = new PdfDocument(new PdfReader(pdfFile));
                for (int page = 1; page <= doc.getNumberOfPages(); page++) {
                    PdfPage currentPage = doc.getPage(page);
                    var text = PdfTextExtractor.getTextFromPage(currentPage);
                    var words = text.split("\\P{IsAlphabetic}+");
                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        word = word.toLowerCase();
                        freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                    }
                    for (var word : freqs.keySet()) {
                        List<PageEntry> pageEntries = new ArrayList<>();
                        if (!searchResults.containsKey(word)) {
                            pageEntries.add(new PageEntry(pdfFile.getName(), page, freqs.get(word)));
                            searchResults.put(word, pageEntries);
                        } else {
                            List<PageEntry> value = searchResults.get(word); // word, <devops.pdf, 1, 3> + <devops.pdf, 2, 2>
                            value.add(new PageEntry(pdfFile.getName(), page, freqs.get(word)));
                            value.sort(PageEntry::compareTo);
                            searchResults.put(word, value);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        Set<String> stopList = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("stop-ru.txt"))) {
            String stopWord;
            while ((stopWord = reader.readLine()) != null) {
                stopList.add(stopWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        var lowerCaseWord = word.toLowerCase();
        var split = lowerCaseWord.split("\\P{IsAlphabetic}+");
        if (searchResults.containsKey(lowerCaseWord)) {
            return searchResults.get(lowerCaseWord);
        }
        return Collections.emptyList();
    }
}