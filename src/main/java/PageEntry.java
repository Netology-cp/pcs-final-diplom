import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.*;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return Integer.compare(o.getCount(), this.getCount());
    }

    @Override
    public String toString() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("pdfName", pdfName);
        map.put("page", page);
        map.put("count", count);
        JSONObject result = null;
        try {
            result = new JSONObject(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static List<PageEntry> sortEntries(List<PageEntry> entries) {
        Collections.sort(entries);
        return entries;
    }

    public static List<PageEntry> searchEntries(List<PageEntry> entries, String query) {
        List<PageEntry> results = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase();
        for (PageEntry entry : entries) {
            if (entry.pdfName.toLowerCase().contains(lowercaseQuery)) {
                results.add(entry);
            }
        }
        return results;
    }
}

