import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    public static void main(String[] args) throws Exception {

        try (Socket clientSocket = new Socket(HOST,PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            String response = in.readLine();
            System.out.println(response);
            out.println("бизнес");
            response = in.readLine();
            System.out.println(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



//        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
//        System.out.println(engine.search("бизнес"));
//
//
//        ArrayList<PageEntry> response = engine.search("бизнес");
//
//
//
//
//        List<JSONObject> responseAsJson = new ArrayList<>();
//
//        for (PageEntry pageEntry: response) {
//            JSONObject jsonResponse = new JSONObject();
//            jsonResponse.put("pdfName", pageEntry.getPdfName());
//            jsonResponse.put("page", pageEntry.getPage());
//            jsonResponse.put("count", pageEntry.getCount());
//            responseAsJson.add(jsonResponse);
//        }
//
//        responseAsJson.stream().forEach(System.out::println);

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    }
}