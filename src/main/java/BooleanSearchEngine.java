import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry> library= new HashMap<>(); //здесь ставим мапу;
    public BooleanSearchEngine(File pdfsDir) throws IOException {
        var doc = new PdfDocument(new PdfReader(pdf)); //вставляем для указания объекта;
        int countPages; //создаю интовое значение по счету страниц;
        countPages = doc.getNumberOfPages(); //вызываю;
        for (int i = 1; i <= doc.getNumberOfPages() ; i++) {
            //буду прогонять через цикл for i;
            var page = doc.getPage(i); //получаем номер страницы через переменную i;
            var text = PdfTextExtractor.getTextFromPage(page); //получаем текст со страницы;
            var words = text.split("\\P{IsAlphabetic}+"); //разбиваем текст на слова
        }{
            
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
