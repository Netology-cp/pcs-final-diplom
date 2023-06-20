import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> index = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы

        List<String> docList = PdfWork.getDocList(pdfsDir);

        System.out.println(docList);
        for (String docName : docList) {
            var doc = new PdfDocument(new PdfReader(new File(pdfsDir.getName() + "/" + docName)));

            for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                var textFromPage = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                var words = textFromPage.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (String word : freqs.keySet()) {
                    List<PageEntry> entries = index.remove(word);
                    if ( entries == null ) {
                        entries = new ArrayList<>();
                    }
                    PageEntry pageEntry = new PageEntry(docName, i, freqs.get(word));
                    entries.add(pageEntry);
                    Collections.sort(entries);
                    index.put(word, entries);
                }
            }
        }


    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        return index.get(word);
    }


}
