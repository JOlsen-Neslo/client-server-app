package za.co.neslotech.client;

import za.co.neslotech.client.exceptions.InvalidCommandException;

import java.io.*;
import java.net.Socket;

public class ClientApplication {

    public static void main(String[] args) {
        System.out.println("Client Application started!");

        String host = "localhost";
        int port = 8888;

        try {
            Socket socket = new Socket(host, port);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage;
            while (!"close".equals(clientMessage)) {
                System.out.println("Waiting for command.");
                clientMessage = performInputSanityCheck(br.readLine());
                System.out.println("Request from client: " + clientMessage);

                out.writeUTF(clientMessage);
                out.flush();

                serverMessage = in.readUTF();
                System.out.println("Response from server: " + serverMessage);
            }

            out.close();
            out.close();
            socket.close();

            System.out.println("Client application is closing.");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        System.exit(1);
    }

    private static String performInputSanityCheck(final String inputString) throws InvalidCommandException {
        if (inputString == null || "".equals(inputString)) {
            System.err.println("The command supplied is empty. Please send a command!");
            throw new InvalidCommandException("Illegal command issued.");
        }

        return inputString;
    }

}
