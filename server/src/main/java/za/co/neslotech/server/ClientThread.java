package za.co.neslotech.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class ClientThread extends Thread {

    private final Socket serverClient;
    private final int clientNo;
    private final String clientId;

    ClientThread(Socket inSocket, int counter) {
        serverClient = inSocket;
        clientNo = counter;
        clientId = UUID.randomUUID().toString();
    }

    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());

            String clientMessage = "";
            while (!"close".equals(clientMessage)) {
                System.out.println("Waiting for message from client.");
                clientMessage = inStream.readUTF();

                System.out.println("From Client - " + clientId + ": Request is: " + clientMessage);

                outStream.writeUTF(clientMessage);
                outStream.flush();

                System.out.println("Sending to Client - " + clientId + ": Response is: " + clientMessage);
            }

            inStream.close();
            outStream.close();
            serverClient.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.println("Client - " + clientNo + " is exiting.");
        }
    }

}
