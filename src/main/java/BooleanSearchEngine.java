import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private Memory memory = new Memory();
    private final static File STOPLISTFILE = new File("stop-ru.txt");

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        if (pdfsDir.isDirectory()) {
            for (File file : pdfsDir.listFiles()) {
                PdfDocument doc = new PdfDocument(new PdfReader(file));
                textReap(doc, file);
            }
        }
    }

    @Override

    public List<PageEntry> search(String words) {
        return searchWords(words);
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
            memory.addToMemory(countMap);//запись в
        }
    }

    //сумматор списков для массива строк
    public List<PageEntry> searchWords(String words) {
        List<PageEntry> list = new ArrayList<>();
        List<String> listWordsIn = checkWords(words);
        for (String word : listWordsIn) {
            if (memory.getMainMap().containsKey(word)) {
                list.addAll(memory.getMainMap().get(word));
            }
        }
        if (list.size() > 0) {
            Map<String, Integer> integerMap = list.stream()
                    .collect(Collectors.groupingBy(PageEntry::generateKey, Collectors.summingInt(PageEntry::getCount)));
            list = integerMap.entrySet()
                    .stream()
                    .map(this::convertToPageEntry)
                    .collect(Collectors.toList());
        } else {
            list.add(new PageEntry("Ничего не найдено", 0, 0));
        }
        Collections.sort(list);
        return list;
    }

    //преобразование элемента countMap в объект PageEntry
    private PageEntry convertToPageEntry(Map.Entry<String, Integer> a) {
        return new PageEntry(a.getKey().split(":")[0],
                Integer.parseInt(a.getKey().split(":")[1]), a.getValue());
    }
    //удаление слов стоп листа из входящих строк
    public List<String> checkWords(String words) {
        String[] arrStr = words.split("\\P{IsAlphabetic}+");
        ArrayList<String> listWords = new ArrayList(Arrays.asList(arrStr));
        List<String> listSrtStop = StopListGenerator.loadFromTxtFile(STOPLISTFILE);
        listWords.removeAll(listSrtStop);
        return listWords;
    }
}
