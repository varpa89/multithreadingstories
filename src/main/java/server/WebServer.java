package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Pavel S Varchenko
 * @since 04.05.2018
 */
public class WebServer {

    private static final int NTHREADS = 1;
    private static final Executor exec
            = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8500);
        System.out.println("Listening for connection on port 8500 ....");

        while (true) {
            final Socket socket = server.accept();
            Runnable task = () -> {
                try (socket) {
                    handleRequest(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            exec.execute(task);
            //new Thread(task).start();
        }
    }

    private static void handleRequest(Socket socket) throws IOException {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(Thread.currentThread().getName());
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        while (!line.isEmpty()) {
            //System.out.println(line);
            line = reader.readLine();
        }
        LocalDateTime today = LocalDateTime.now();
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
        socket.getOutputStream()
              .write(httpResponse.getBytes("UTF-8"));

    }
}
