package FlipClip;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class FlipClip {
    static boolean clientRunning = true;
    static boolean serverRunning = false;
    static boolean continueAcceptingConnections = true;
    static Thread senderThread;
    static Thread receiverThread;
    static DisconnectServer connected;
    static ServerSocket serverSocket;
    static Socket socket;
    static Socket clientSocket;


    // This method returns the IP address of the current machine
    private static String getOwnIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            List<NetworkInterface> interfaceList = Collections.list(interfaces);
            for (NetworkInterface intf : interfaceList) {
                if (!intf.isUp() || intf.isLoopback() || intf.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = intf.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && !addr.isMulticastAddress()) {
                        return addr.toString().replace("/", "");
                    }
                }
            }
        } catch (Exception e) {
        }
        return (InetAddress.getLoopbackAddress()).toString().replace("/","");
    }

    public static void StartServer() {
        int PORT = 1234;
        System.out.println("Starting server...");
        try {
            //get ipv4 of wifi card
            String SERVER_IP = getOwnIpAddress();
            System.out.println("Your IP address is: " + SERVER_IP);
            System.out.println("Your port is: " + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        while (continueAcceptingConnections) { // Check the flag before accepting new connections
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Waiting for connection...");
                socket = serverSocket.accept();
                DisconnectServer.hostName=socket.getInetAddress().getHostName();
                if (socket.isConnected()){
                    BiDirectional.createFrame.dispose();
                    connected = new DisconnectServer("server");
                    connected.setVisible(true);
                    System.out.println("Connected to the client.");
                }
                serverSocket.close();
                afterConnect(socket);
            } catch (Exception e) {
                System.out.println("Client disconnected.\nRestarting server in 1 second...");
                try {
                    Thread.sleep(1000);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                continue;
            }
        }
    }
    

    public static void closeServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static boolean ableToConnectWithIP(String ip) {
        try {
            Socket socket = new Socket(ip, 1234);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public static void CreateClient(String SERVER_IP, int PORT) {
        while (clientRunning) {
            try {
                System.out.println("Trying to connect...");
                Socket socket = new Socket(SERVER_IP, PORT);
                DisconnectServer.hostName=socket.getInetAddress().getHostName();
                System.out.println("Connected to the server.");
                // open DisconnectServer frame
                connected = new DisconnectServer("client");
                BiDirectional.joinFrame.dispose();
                connected.setVisible(true);
                afterConnect(socket);
                
            } catch (Exception e) {
                System.out.println("Reconnecting in 1 seconds...");
                try {
                    Thread.sleep(1000);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void afterConnect(Socket sockeT) throws Exception {
        try {
            clientSocket = sockeT;
            Sender sender = new Sender(clientSocket.getOutputStream());
            Receiver receiver = new Receiver(clientSocket.getInputStream());

            senderThread = new Thread(sender);
            receiverThread = new Thread(receiver);
            senderThread.start();
            receiverThread.start();

            while (true) {
                if (!senderThread.isAlive() || !receiverThread.isAlive()) {
                    throw new Exception();
                }
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.out.println("Connection lost.");
        }
    }
    public static void Serverdisconnect() {
        // Perform disconnect action here
        System.out.println("Disconnecting...");
        FlipClip.continueAcceptingConnections = false; // Stop accepting new connections
        if (FlipClip.socket != null) {
            try {
                FlipClip.socket.close(); // Close the existing socket
            } catch (IOException e) {
                e.printStackTrace();
            }
            FlipClip.socket = null; // Set the socket to null
        }
        if (FlipClip.serverSocket != null) {
            try {
                FlipClip.serverSocket.close(); // Close the existing server socket
            } catch (IOException e) {
                e.printStackTrace();
            }
            FlipClip.serverSocket = null; // Set the server socket to null
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverRunning = false;
        connected.dispose();
        BiDirectional biDirectionalFrame = new BiDirectional();
        biDirectionalFrame.setVisible(true);
    }

    public static void Clientdisconnect() {
        // Perform disconnect action here
        System.out.println("Disconnecting...");
        if (FlipClip.socket != null) {
            try {
                FlipClip.socket.close(); // Close the existing socket
            } catch (IOException e) {
                e.printStackTrace();
            }
            FlipClip.socket = null; // Set the socket to null
        }
        if (FlipClip.serverSocket != null) {
            try {
                FlipClip.serverSocket.close(); // Close the existing server socket
            } catch (IOException e) {
                e.printStackTrace();
            }
            FlipClip.serverSocket = null; // Set the server socket to null
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientRunning = false;

        connected.dispose();
        BiDirectional biDirectionalFrame = new BiDirectional();
        biDirectionalFrame.setVisible(true);
        
    }
}





