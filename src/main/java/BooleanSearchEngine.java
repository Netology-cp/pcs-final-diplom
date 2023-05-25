package org.example;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.*;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    protected Map<String, List<PageEntry>> wordsIndex;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        wordsIndex = IndexWord.getStorage().getIndexWord();

        File[] listFiles = new File(String.valueOf(pdfsDir)).listFiles();
        for (int i = 0; i < Objects.requireNonNull(listFiles).length; i++) {
            var doc = new PdfDocument(new PdfReader(listFiles[i]));

            for (int k = 0; k < doc.getNumberOfPages(); k++) {
                var file = doc.getPage(k + 1);
                var text = PdfTextExtractor.getTextFromPage(file);

                String[] words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> frequency = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
                }

                String nameFilePDF = doc.getDocumentInfo().getTitle();
                for (Map.Entry<String, Integer> entry :
                        frequency.entrySet()) {
                    String entryKey = entry.getKey();
                    Integer entryValue = entry.getValue();
                    List<PageEntry> objectList = new ArrayList<>();
                    objectList.add(new PageEntry(nameFilePDF, k + 1, entryValue));

                    if (wordsIndex.containsKey(entryKey)) {
                        wordsIndex.get(entryKey).add(new PageEntry(nameFilePDF, k + 1, entryValue));
                    } else {
                        wordsIndex.put(entryKey, objectList);
                    }

                }
            }
        }
    }
    @Override
    public List<PageEntry> search(String word) {
        String wordToLowerCase = word.toLowerCase();
        List<PageEntry> pageEntryList = wordsIndex.getOrDefault(wordToLowerCase, Collections.emptyList());

        Collections.sort(pageEntryList);
        return pageEntryList;
    }
}