
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executor.execute(new ClientHandler(socket, engine));
                } catch (IOException e) {
                    System.out.println("Error accepting client connection");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to start the server");
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private BooleanSearchEngine engine;

    public ClientHandler(Socket socket, BooleanSearchEngine engine) {
        this.socket = socket;
        this.engine = engine;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request = in.readLine();
            String answer = engine.search(request).toString();
            out.println(answer);
        } catch (IOException e) {
            System.out.println("Error handling client request");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket");
                e.printStackTrace();
            }
        }
    }
}

