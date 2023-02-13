import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8989;
    private static String[] words = {"за", "бизнес", "микросервис", "на", "смысл", "план", "паттерн", "к", "также"};

    public static void main(String[] args) {
        Gson gsn = new Gson();
        for (String item : words) {
            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(socket.getInputStream())))) {
                if (item != null) {
                    printWriter.println(item);
                } else {
                    printWriter.println("Q");
                    break;
                }
                String strIn = bufferedReader.readLine();
                PageEntry pageEntry = gsn.fromJson(strIn, PageEntry.class);
                System.out.println("Получено сообщение от сервера: " + strIn);
            } catch (Exception exception) {
                exception.getStackTrace();
            }
        }
    }
}

