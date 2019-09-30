package JServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    /**
     *
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {

        try(ServerSocket server = new ServerSocket(3345)){

            Socket client = server.accept();
            System.out.println("Connection accepted.");
            System.out.println("...");

            DataOutputStream os = new DataOutputStream(client.getOutputStream());
            DataInputStream is = new DataInputStream(client.getInputStream());

            System.out.println("DataInputStream & DataOutputStream were created");

            while (!client.isClosed()){

                System.out.println("Server reading from channel");
                String entry = is.readUTF();

                System.out.println("READ from client message "+entry);
                System.out.println("Server try writing to channel");

                if (entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");

                    os.writeUTF("Server reply - "+entry+" - OK");
                    os.flush();
                    break;
                }

                os.writeUTF("Server reply - "+entry+" - OK");
                System.out.println("Server wrote message to client.");
                os.flush();
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels on server.");

            is.close();
            os.close();
            client.close();

            System.out.println("Closing connections & channels on server - DONE.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
