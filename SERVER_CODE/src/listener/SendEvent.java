package listener;
import screen.Panel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
public class SendEvent implements MouseListener, KeyListener, MouseMotionListener {
    Socket socket;
    ObjectOutputStream  os;
    Panel panel;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public SendEvent(Panel panel, Socket socket) throws IOException {
        this.socket = socket;
        this.panel = panel;
        os =  new ObjectOutputStream(this.socket.getOutputStream());
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Clavier presser");
        try {
            os.writeObject(Commands.PRESS_KEY.getAbbrev());
            os.writeObject(e.getKeyCode());
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        try {
            os.writeObject(Commands.RELEASED_KEY.getAbbrev());
            os.writeObject(e.getKeyCode());
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("souris presser");
        try {
            int xbtn = 1024;
            if(e.getButton() == MouseEvent.BUTTON3)
                xbtn = 4096;
            os.writeObject(Commands.PRESS_MOUSE.getAbbrev());
            os.writeObject(xbtn);
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            int xbtn = 1024;
            if(e.getButton() == MouseEvent.BUTTON3) {
                xbtn = 4096;
            }
            os.writeObject(Commands.RELEASED_MOUSE.getAbbrev());
            os.writeObject(xbtn);
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Souris deplacer");
        double xscale = panel.getWidth() / dim.width;
        double yscale = panel.getHeight() / dim.height;
        try {
            os.writeObject(Commands.MOUVE_MOUSE.getAbbrev());
            os.writeObject((int)((e.getX()-10) * xscale));
            os.writeObject((int)((e.getY()-25) * yscale));
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
