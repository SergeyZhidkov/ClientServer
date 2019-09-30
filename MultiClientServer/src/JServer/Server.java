package JServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService service = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(3345)){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Server socket created");
            System.out.println("Command console reader for listen to server commands");

            while (!server.isClosed()){
                if(br.ready()){
                    System.out.println("Main Server found any messages in channel");

                    String entry = br.readLine();
                    if (entry.equalsIgnoreCase("quit")){
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }

                Socket client = server.accept();

                service.execute(new ClientThread(client));
                System.out.println("connection accepted.");
            }
            br.close();
            service.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
