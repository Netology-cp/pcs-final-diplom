import java.util.ArrayList;
import java.util.List;

public class MyListUtil {

    public static List<PageEntry>addAndGetExistList(PageEntry pageEntry,List<PageEntry>list){
        list.add(pageEntry);
        return list;
    }
}
