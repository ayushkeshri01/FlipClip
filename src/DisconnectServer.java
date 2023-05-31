// package FlipClip;
import javax.swing.*;
import java.awt.*;

public class DisconnectServer extends JFrame {
    static String hostName = "Unknown";
    private JLabel hostNameLabel;
    private JButton disconnectbutton;

    public DisconnectServer(String check) {
        setTitle("Host Details");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // Create the host name label
        hostNameLabel = new JLabel();
        hostNameLabel.setFont(new Font("Serif", Font.PLAIN, 24)); // Increase font size to 24
        hostNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setHostName(hostName);

        // Create the disconnect button
        disconnectbutton = Menu.createButton("Disconnect", "FlipClip/images/disconnect.png");
        disconnectbutton.addActionListener(e -> {
            if (check.equals("server")) {
                FlipClip.Serverdisconnect();
            }
            else {
                FlipClip.Clientdisconnect();
            }
        } );
        disconnectbutton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(100));
        panel.add(hostNameLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(disconnectbutton);

        add(panel);
    }

    // public void disconnect() {
    //     // Perform disconnect action here
    //     System.out.println("Disconnecting...");
    //     FlipClip.continueAcceptingConnections = false; // Stop accepting new connections
    //     if (FlipClip.socket != null) {
    //         try {
    //             FlipClip.socket.close(); // Close the existing socket
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //         FlipClip.socket = null; // Set the socket to null
    //     }
    //     if (FlipClip.serverSocket != null) {
    //         try {
    //             FlipClip.serverSocket.close(); // Close the existing server socket
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //         FlipClip.serverSocket = null; // Set the server socket to null
    //     }
    //     BiDirectional biDirectionalFrame = new BiDirectional();
    //     biDirectionalFrame.setVisible(true);
    //     dispose();
    // }

    public void setHostName(String hostName) {
        hostNameLabel.setText("Connected to: " + hostName);
    }

    
}
