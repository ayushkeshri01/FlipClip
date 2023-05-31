// package FlipClip;
import javax.swing.*;
import java.awt.*;


public class BiDirectional extends JFrame {
    static Create createFrame;
    static Join joinFrame;
    public static Thread serverThread ;
    public BiDirectional() {
        setTitle("FlipClip");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel contentPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        JLabel modeLabel = new JLabel("Select Role :");
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        modeLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        JButton createButton = createButton("Create", "FlipClip/images/create.png");
        JButton JoinButton = createButton("Join", "FlipClip/images/join.png");
        
        createButton.addActionListener(e -> openCreateFrame());
        JoinButton.addActionListener(e -> openJoinFrame());
        
        panel.add(createButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(JoinButton);

        panel2.add(Box.createVerticalStrut(70));
        panel2.add(modeLabel);
        // JButton back = new JButton("Back");
        // back.setFont(new Font("Serif", Font.PLAIN, 20));
        // back.setPreferredSize(new Dimension(150, 50));
        // back.setVerticalTextPosition(SwingConstants.BOTTOM);
        // back.setHorizontalTextPosition(SwingConstants.CENTER);
        // back.setAlignmentX(Component.CENTER_ALIGNMENT);
        // back.setAlignmentY(Component.CENTER_ALIGNMENT);
        // back.setBackground(Color.WHITE);
        // back.setFocusable(false);        back.addActionListener(e -> goBack());
        panel2.add(panel);

        // panel2.add(back);
        contentPane.add(panel2);


    }

    private JButton createButton(String text, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JButton button = new JButton(text, icon);
        button.setFont(new Font("Serif", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(150, 100));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBackground(Color.WHITE);
        button.setFocusable(false);

        return button;
    }

    public void StartServer() {
        // Code to start the server goes here
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FlipClip.continueAcceptingConnections = true;
                FlipClip.StartServer();  // Start the server
            }
        });
        serverThread.start();
    }

    public void openCreateFrame() {
        // Code to open Create frame and StartServer() using threads
        
        StartServer();
        createFrame = new Create();
        createFrame.setVisible(true);
        dispose();
    }

    private void openJoinFrame() {
        // Code to open the MultiServer frame
        joinFrame = new Join();
        joinFrame.setVisible(true);
        dispose();
    }
    // private void goBack() {
    //     // Code to go back to the previous frame goes here
    //     Menu menuFrame = new Menu();
    //     menuFrame.setVisible(true);
    //     dispose();
    // }
}
