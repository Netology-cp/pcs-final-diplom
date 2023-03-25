import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine { //импорт;
    private final Map<String, List<PageEntry>> index = new HashMap<>(); //здесь ставим мапу;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        File[] files = pdfsDir.listFiles(); //ниже я подглянул на stackoverflow;
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.getName().contains(".pdf")) { //ставим условие по нахождению файла с ключом .pdf;
                    var doc = new PdfDocument(new PdfReader(file)); //вставляем для указания объекта;
                    int countPages = doc.getNumberOfPages(); //создаю интовое значение по счету страниц;
                    for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                        //буду прогонять через цикл for i;
                        var page = doc.getPage(i); //получаем номер страницы через переменную i;
                        var text = PdfTextExtractor.getTextFromPage(page); //получаем текст со страницы;
                        var words = text.split("\\P{IsAlphabetic}+"); //разбиваем текст на слова
                        Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                        for (var word : words) { // перебираем слова
                            if (word.isEmpty()) {
                                continue;
                            }
                            word = word.toLowerCase();
                            freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                        }
                        for (var entry : freqs.entrySet()) {
                            List<PageEntry> result;
                            if (!index.containsKey(entry.getKey())) {
                                result = new ArrayList<>();
                            } else {
                                result = index.get(entry.getKey());
                            }
                            result.add(new PageEntry(file.getName(), i, entry.getValue()));
                            Collections.sort(result, Collections.reverseOrder());
                            index.put(entry.getKey(), result);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        if (word == null) {
            return Collections.emptyList();
        } else {
            return index.get(word.toLowerCase());
        }
    }
}
