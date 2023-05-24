package FlipClip;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class Sender implements Runnable {
    private OutputStream outputStream;

    public Sender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            DataOutputStream output = new DataOutputStream(outputStream);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            while (true) {
                Transferable currentData = clipboard.getContents(null);
                if (currentData.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                    Image currentClipboardImage = (Image) currentData.getTransferData(DataFlavor.imageFlavor);
                    BufferedImage bufferedImage = new BufferedImage(currentClipboardImage.getWidth(null),
                            currentClipboardImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = bufferedImage.createGraphics();
                    g.drawImage(currentClipboardImage, 0, 0, null);
                    g.dispose();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);
                    byte[] imageBytes = baos.toByteArray();

                    if (!Arrays.equals(imageBytes, clipboardData.previousClipboardImage)
                            || clipboardData.previousDataType == 't') {
                        output.writeInt(2);
                        output.writeInt(imageBytes.length);
                        output.write(imageBytes);
                        output.flush();
                        clipboardData.previousClipboardImage = imageBytes.clone();
                        clipboardData.previousDataType = 'i';
                    }

                } else {
                    String currentClipboardText = (String) currentData.getTransferData(DataFlavor.stringFlavor);
                    if (!clipboardData.previousClipboardText.equals(currentClipboardText)
                            || clipboardData.previousDataType == 'i') {
                        output.writeInt(1);
                        output.writeUTF(currentClipboardText);
                        output.flush();
                        clipboardData.previousClipboardText = currentClipboardText;
                        clipboardData.previousDataType = 't';
                    }
                }
                Thread.sleep(2000);
            }
        } catch (Exception e) {
        }
    }
}

