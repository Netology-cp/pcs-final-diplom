import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {

    private static final int PORT = 8989;
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();   // Ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String word = in.readLine().toLowerCase();
                    String result = searchToJSON(engine.search(word));
                    System.out.println(result);
                    out.println(result);
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String searchToJSON(List<PageEntry> search) {
        if (search.isEmpty()) return "";

        String result = "[";
        for (PageEntry pageEntry : search) {
            result += "\n" + pageEntry.toStringAsJSON() + ",";
        }
        result += "]";
        result = result.replace(",]", "\n]");
        return result;
    }
}