package FlipClip;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Create extends JFrame {
    public JLabel statusLabel;

    public Create() {
        FlipClip.serverRunning = true;
        setTitle("FlipClip");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 160, 20, 20)); // Add space between components

        JPanel Instructionpanel = new JPanel();
        Instructionpanel.setLayout(new BoxLayout(Instructionpanel, BoxLayout.Y_AXIS));

        JLabel modeLabel = new JLabel("On the Other divice :");
        JLabel modeLabel1 = new JLabel("Step 1: Choose Bidirectional Mode.");
        JLabel modeLabel2 = new JLabel("Step 2: Click Join.");
        JLabel modeLabel3 = new JLabel("Step 3: Enter IP Address to connect.");

        modeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        modeLabel3.setAlignmentX(Component.LEFT_ALIGNMENT);

        modeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        modeLabel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        modeLabel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        modeLabel3.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        modeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        modeLabel1.setFont(new Font("Serif", Font.PLAIN, 20));
        modeLabel2.setFont(new Font("Serif", Font.PLAIN, 20));
        modeLabel3.setFont(new Font("Serif", Font.PLAIN, 20));

        Instructionpanel.add(modeLabel);
        Instructionpanel.add(modeLabel1);
        Instructionpanel.add(modeLabel2);
        Instructionpanel.add(modeLabel3);

        panel.add(Instructionpanel);

        JLabel ownIpLabel = new JLabel("IP Address:");
        ownIpLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ownIpLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Add space below component
        ownIpLabel.setFont(new Font("Serif", Font.PLAIN, 25)); // Change font and size

        panel.add(ownIpLabel);

        // Panel to display IP Address with a copy icon next to it
        JPanel ipPanel = new JPanel();
        ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.X_AXIS));
        ipPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ipPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        ipPanel.setBackground(Color.WHITE);

        JTextField ipTextField = new JTextField(30); // Set the size of the text field
        ipTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        ipTextField.setAlignmentY(Component.CENTER_ALIGNMENT);
        ipTextField.setMaximumSize(new Dimension(200, 50));
        ipTextField.setText(getOwnIpAddress());
        ipTextField.setEditable(false);
        ipTextField.setFont(new Font("Serif", Font.PLAIN, 20));
        ipTextField.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 8));

        ipPanel.add(ipTextField);

        ImageIcon copyIcon = new ImageIcon("FlipClip/src/copy.png");
        copyIcon.setImage(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JButton button = new JButton("", copyIcon);
        button.setPreferredSize(new Dimension(30, 50));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(null); // Remove the button border
        button.setBackground(Color.WHITE);
        button.setFocusable(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> copyIpAddress(ipTextField.getText()));

        ipPanel.add(button);
        panel.add(ipPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components

        // Message label for "Waiting for connection"
        statusLabel = new JLabel("Waiting for connection....");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 28));
        panel.add(statusLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between components
        JButton back = new JButton("Back");
        back.setFont(new Font("Serif", Font.PLAIN, 20));
        back.setPreferredSize(new Dimension(150, 50));
        back.setVerticalTextPosition(SwingConstants.BOTTOM);
        back.setHorizontalTextPosition(SwingConstants.CENTER);
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.setAlignmentY(Component.CENTER_ALIGNMENT);
        back.setBackground(Color.WHITE);
        back.setFocusable(false);
        back.addActionListener(e -> goBack());

        panel.add(back);

        add(panel);

    }

    

    private void goBack() {
        // Code to go back to the previous frame goes here
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
        dispose();
        BiDirectional biDirectionalFrame = new BiDirectional();
        biDirectionalFrame.setVisible(true);

    }

    private void copyIpAddress(String ipAddress) {
        // Code to copy the IP address to clipboard
        StringSelection selection = new StringSelection(ipAddress);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        JOptionPane.showMessageDialog(this, "IP Address copied!", "Copied", JOptionPane.INFORMATION_MESSAGE, null);

    }

    private static String getOwnIpAddress() {
        // Code to get own IP address goes here
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
            // ignore and fall through to return loopback address
            e.printStackTrace();
        }
        return (InetAddress.getLoopbackAddress()).toString().replace("/", "");
    }

}
