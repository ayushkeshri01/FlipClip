package FlipClip;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Receiver implements Runnable {
    private InputStream inputStream;
    public Receiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(inputStream);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            while (true) {
                int dataType = input.readInt();
                if (dataType == 1) {
                    String clipboardText = input.readUTF();
                    StringSelection selection = new StringSelection(clipboardText);
                    if (!clipboardText.equals(clipboardData.previousClipboardText)) {
                        clipboard.setContents(selection, null);
                        System.out.println("Received text data: ");
                        clipboardData.previousClipboardText = clipboardText;
                        Thread.sleep(1000);
                    }
                } else if (dataType == 2) {
                    int imageSize = input.readInt();
                    byte[] imageBytes = new byte[imageSize];
                    input.readFully(imageBytes, 0, imageSize);
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    BufferedImage bufferedImage = ImageIO.read(bais);

                    ImageSelection selection = new ImageSelection(bufferedImage);
                    if (!Arrays.equals(imageBytes, clipboardData.previousClipboardImage)) {
                        clipboard.setContents(selection, null);
                        System.out.println("Received image data.");
                        clipboardData.previousClipboardImage = imageBytes.clone();
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
