package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexWord {
    protected static IndexWord storage;
    protected Map<String, List<PageEntry>> indexWord = new HashMap<>();

    public static IndexWord getStorage() {
        if (storage == null) {
            storage = new IndexWord();
        }
        return storage;
    }

    public Map<String, List<PageEntry>> getIndexWord() {
        return indexWord;
    }
}
