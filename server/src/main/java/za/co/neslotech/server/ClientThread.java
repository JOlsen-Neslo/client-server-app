package za.co.neslotech.server;

import za.co.neslotech.server.calculation.EquationCalculator;
import za.co.neslotech.server.exceptions.CalculationException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class ClientThread extends Thread {

    private final Socket serverClient;
    private final int clientNo;
    private final String clientId;
    private final EquationCalculator calculator;

    ClientThread(Socket inSocket, int counter) {
        serverClient = inSocket;
        clientNo = counter;
        clientId = UUID.randomUUID().toString();
        calculator = new EquationCalculator();
    }

    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());

            String clientMessage = "", serverMessage;
            while (!"close".equals(clientMessage)) {
                System.out.println("Waiting for message from client.");
                clientMessage = inStream.readUTF();

                System.out.println("From Client - " + clientId + ": Request is: " + clientMessage);

                try {
                    serverMessage = calculator.execute(clientMessage);
                } catch (CalculationException | ReflectiveOperationException e) {
                    System.err.println("An error occurred when performing the calculation: " + e.getMessage());
                    serverMessage = "Failed to perform the calculation. " + e.getMessage();
                }

                outStream.writeUTF(serverMessage);
                outStream.flush();

                System.out.println("Sending to Client - " + clientId + ": Response is: " + serverMessage);
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
