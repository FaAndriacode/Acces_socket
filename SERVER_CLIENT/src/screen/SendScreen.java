package screen;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
public class SendScreen extends Thread {
    Socket socket;
    OutputStream os;
    boolean loop = true;
    int x = 0;
    int y =0;
    byte[] size = null;
    ByteArrayOutputStream byteArrayOutputStream = null;
    public SendScreen(Socket socket){
        this.socket = socket;
        start();
    }
    public Image getCursor() throws IOException {
        return ImageIO.read(new File(".\\src\\image\\Curseur.png"));
    }
    public void run() {
        Robot robot = null;
        Image image = null;
        Rectangle sizeScreen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            robot = new Robot();
            os = socket.getOutputStream();
        } catch (IOException | AWTException e) {
            throw new RuntimeException(e);
        }
        try {
            while (loop) {
                image = robot.createScreenCapture(sizeScreen);
                byteArrayOutputStream = new ByteArrayOutputStream();

                //gére les graphics Emplacemnt du souris
                Graphics g = image.getGraphics();
                x = (int) MouseInfo.getPointerInfo().getLocation().getX();
                y = (int) MouseInfo.getPointerInfo().getLocation().getY();
                g.drawImage(getCursor(),x,y, 16,16,null);

                //dessin le souris sur l'image a envoyer
                ImageIO.write((RenderedImage) image, "jpeg", byteArrayOutputStream);
                size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                os.write(size);
                os.write(byteArrayOutputStream.toByteArray());
                os.flush();
                Thread.sleep(400);
            }
        }catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
