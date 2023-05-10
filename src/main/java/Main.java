import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;



public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File(ServerConfigurations.pdfsDir));
        System.out.println(engine.search("стена"));

//        try (ServerSocket serverSocket = new ServerSocket(ServerConfigurations.PORT)) { // стартуем сервер один(!) раз
//
//            System.out.println("Сервер запущен");
//
//            while (true) { // в цикле(!) принимаем подключения
//                try (
//                        Socket socket = serverSocket.accept();
//                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        PrintWriter out = new PrintWriter(socket.getOutputStream());
//                ) {
//                    System.out.println("Новая покупка");
//                    String str = in.readLine();
//                    Gson gson = new Gson();
//
//
//                    Purchase purchase = gson.fromJson(str, Purchase.class);
//                    System.out.println(purchase.toString());
//                    statistics.addPurchase(purchase);
//
//                    GsonBuilder builder = new GsonBuilder();
//                    out.write(builder.setPrettyPrinting().create().toJson(statistics.statistic()));
//                    System.out.println(builder.setPrettyPrinting().create().toJson(statistics.statistic()));
//
//
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        } catch (
//                IOException | ClassNotFoundException e) {
//            System.out.println("Не могу стартовать сервер");
//            e.printStackTrace();
//        }

    }
}