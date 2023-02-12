import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memory {
    Map<String, List> mainMap = new HashMap<>();

    public void addToMemory(Map<String, PageEntry> countMap) {
        countMap.forEach((a, b) -> mainMap.put(a, MyListUtil
                .addAndGetExistList(b, mainMap.getOrDefault(a, new ArrayList()))));
    }

    public Map<String, List> getMainMap() {
        return mainMap;
    }

    public void printMainMap() {
        mainMap.forEach((a, b) -> {
            System.out.println(a + ":");
            b.forEach(c -> System.out.println(c.toString()));
        });
    }
}

