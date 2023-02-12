import java.io.File;
import java.util.Arrays;

public class Main {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8989;
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        engine.search("для").forEach(a-> System.out.println(a.toString()));
        //System.out.println(engine.search("бизнес"));

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    }

}