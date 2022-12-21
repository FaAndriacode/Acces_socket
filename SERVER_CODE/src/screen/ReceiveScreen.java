package screen;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
public class ReceiveScreen extends Thread{
    Socket socket;
    Panel panel;
    InputStream in;
    boolean loop = true;
    byte[] sizeAr = null;
    byte[] imageAr = null;
    int size = 0;
    int totalRead = 0;
    int currentRead = 0;
    Image image = null;
    public ReceiveScreen(Panel panel, Socket socket) throws IOException {
        this.socket = socket;
        this.panel = panel;
        in = socket.getInputStream();
        this.start();
    }

    public void run() {
        try {
            while (loop) {
                sizeAr = new byte[4];
                in.read(sizeAr);
                size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                imageAr = new byte[size];
                totalRead = 0;
                currentRead = 0;
                while (totalRead < size && (currentRead = in.read(imageAr, totalRead, size-totalRead)) > 0) {
                    totalRead += currentRead;
                }
                image = ImageIO.read(new ByteArrayInputStream(imageAr));
                Graphics g = panel.getGraphics();
                panel.repaint();
                g.drawImage(image, 0, 0, image.getWidth(null),image.getHeight(null),null);
                //Thread.sleep(400);
            }
        }catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(new JFrame(),e.getMessage(),"Error 404",JOptionPane.WARNING_MESSAGE);
        }
    }
}
