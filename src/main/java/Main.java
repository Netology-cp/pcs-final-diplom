package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    static final int SERVER_PORT = 8989;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {
            System.out.println("Запуск сервера в " + SERVER_PORT + "...");

            BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
            System.out.println(engine.search("навыки"));

            Gson gson = new GsonBuilder().create();

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    String word = in.readLine();
                    List<PageEntry> pageEntryList = engine.search(word);

                    if (pageEntryList == null) {
                        out.println("Нет совпадений");
                    }

                    String json = gson.toJson(pageEntryList);
                    out.println(json);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't start the server");
            e.printStackTrace();
        }
    }
}