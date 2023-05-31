// package FlipClip;

import javax.swing.*;
import java.awt.*;



public class Menu extends JFrame {
    public BiDirectional bidirectionalFrame ;
    public Menu() {
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

        JLabel modeLabel = new JLabel("Select Mode :");
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        modeLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        JButton bidirectionalButton = createButton("Bidirectional", "FlipClip/src/BiDir.png");
        JButton multiServerButton = createButton("MultiServer", "FlipClip/src/Multi.png");
        
        bidirectionalButton.addActionListener(e -> openBidirectionalFrame());
        multiServerButton.addActionListener(e -> openMultiServerFrame());
        
        panel.add(bidirectionalButton);
        panel.add(multiServerButton);

        panel2.add(Box.createVerticalStrut(70));
        panel2.add(modeLabel);
        panel2.add(panel);
        
        contentPane.add(panel2);
    }

    public static JButton createButton(String text, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JButton button = new JButton(text, icon);
        button.setBackground(Color.WHITE);
        button.setFocusable(false);
        button.setFont(new Font("Serif", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(150, 100));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }

    public void openBidirectionalFrame() {
        bidirectionalFrame= new BiDirectional();
        bidirectionalFrame.setVisible(true);
        dispose();
    }

    private void openMultiServerFrame() {
        // Code to open the MultiServer frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Menu menuGUI = new Menu();
            // menuGUI.setVisible(true);
            BiDirectional bidirectionalFrame = new BiDirectional();
            bidirectionalFrame.setVisible(true);
        });
    }
}
