import com.google.gson.Gson;
import com.itextpdf.io.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("бизнес"));
        int port = 8989;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket connection = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                     PrintWriter out = new PrintWriter(connection.getOutputStream(), true)) {

                    String printedInTerminal = in.readLine();
                    List<PageEntry> result = engine.search(printedInTerminal);
                    Gson gson = new Gson();
                    out.println(gson.toJson(result));

                } catch (IOException exception) {
                    System.out.println("Не могу стартовать сервер");
                    exception.printStackTrace();
                }
            }
        }
    }
}
// здесь создайте сервер, который отвечал бы на нужные запросы
// слушать он должен порт 8989
// отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
