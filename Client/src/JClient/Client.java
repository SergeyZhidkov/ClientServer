package JClient;

import java.io.*;
import java.net.Socket;

public class Client {
    /**
     *
     * @param args
     * @thtows InterruptedException
     */

    public static void main(String[] args) throws InterruptedException{
        try(Socket socket = new Socket("localhost", 3345)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            DataInputStream is = new DataInputStream(socket.getInputStream());

            System.out.println("Client connected to socket.");
            System.out.println("...");
            System.out.println("Client writing channel = os & reading channel = is initialized");

            while (!socket.isOutputShutdown()){

                if (br.ready()){
                    System.out.println("Client start writing in channel...");
                    String clientCommand = br.readLine();

                    os.writeUTF(clientCommand);
                    System.out.println("Server sent message "+is.readUTF()+" to client.");
                    os.flush();
                    System.out.println("Client sent message "+clientCommand+" to server.");

                    if(clientCommand.equalsIgnoreCase("quit")){
                        System.out.println("Client disconnected");

                        if (is.read() > -1){
                            System.out.println("reading...");
                            String in = is.readUTF();
                            System.out.println(in);
                        }

                        break;
                    }

                    System.out.println("Client sent message & start writing for data from server...");
                }
            }

            System.out.println("Closing connections & channels on client side...");

            br.close();
            is.close();
            os.close();

            System.out.println("Closing connections & channels on client side - DONE.");
        } catch (EOFException e){
            System.out.println("EOFException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
