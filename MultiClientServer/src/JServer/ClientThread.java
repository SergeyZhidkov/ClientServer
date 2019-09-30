package JServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable{

    private static Socket client;

    public ClientThread(Socket client){
        ClientThread.client = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            System.out.println("DataInputStream and DataOutputStream created ");

            while (!client.isClosed()){
                System.out.println("Server reading from channel");
                String entry = in.readUTF();
                System.out.println("READ from client messege - "+entry);

                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - "+entry+" - OK");
//                    Thread.sleep(3000);
                    break;
                }

                System.out.println("Server try writing to channel");
                out.writeUTF("Server reply - "+entry+" - OK");
                System.out.println("Server wrote message to client");

                out.flush();
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels");

            in.close();
            out.close();
            client.close();

            System.out.println("Closing connections & channels - DONE");
        } catch (SocketException e){
            System.out.println("Client lost connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
