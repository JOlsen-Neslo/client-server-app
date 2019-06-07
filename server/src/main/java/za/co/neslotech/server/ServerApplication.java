package za.co.neslotech.server;

import javax.net.ServerSocketFactory;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {

    public static void main(String[] args) {
        try {
            // TODO: implement SSL
//            SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(8888);
            ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
            ServerSocket serverSocket = serverSocketFactory.createServerSocket(8888);

            int counter = 0;
            System.out.println("Server started");
            while (true) {
                counter++;
//                SSLSocket serverClient = (SSLSocket) serverSocket.accept();
//                serverClient.setEnabledCipherSuites(serverClient.getSupportedCipherSuites());
                Socket serverClient = serverSocket.accept();
                serverClient.setSoTimeout(60 * 1000); //setting the soTimeout will disconnect the client after 60 seconds of inactivity
//                serverClient.startHandshake();

                System.out.println("Establishing connection: " + counter);

                ClientThread clientThread = new ClientThread(serverClient, counter);
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
