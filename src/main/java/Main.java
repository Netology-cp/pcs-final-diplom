import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8989;

    private static Gson gsn = new Gson();


    public static void main(String[] args) throws IOException {
        BooleanSearchEngine engine= new BooleanSearchEngine(new File("pdfs"));;
        engine.checkWords("за в на").forEach(a -> System.out.println(a.toString()));
        //engine.search("микросервис").forEach(a -> System.out.println(a.toString()));
        //System.out.println(engine.search("бизнес"));
        //startSrv(engine);
    }


    public static void startSrv(BooleanSearchEngine engine) throws IOException {
        System.out.println("Starting server at " + PORT + "...");
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                System.out.println("Подключен клиент:" + clientSocket.getPort());
                String in = bufferedReader.readLine();
                if (in != null && in.equals("Q")) {
                    System.out.println("Получена команда на отключение!");
                    break;
                } else if (in == null) {
                    System.out.println("Получен от клиента null!!!");
                    continue;
                }
                System.out.println("Получено сообщение от клиента: " + in);
                String out=gsn.toJson(engine.search(in));

                System.out.println("Отправлено:" + out);
                printWriter.println(out);
            }
        }
    }

    // здесь создайте сервер, который отвечал бы на нужные запросы
    // слушать он должен порт 8989
    // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
}

