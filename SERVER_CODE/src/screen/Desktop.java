package screen;
import listener.SendEvent;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.Socket;
public class Desktop extends Thread {
    Socket socket;
    JFrame frame = new JFrame();
    JDesktopPane desktop = new JDesktopPane();
    Panel panel = new Panel();
    SendEvent se;
    public Desktop(Socket socket)  {
        this.socket = socket;
        start();
    }
    public void drawGUI() throws PropertyVetoException {
        frame.add(desktop);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        desktop.add(panel);

        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setFocusable(true);

        frame.addKeyListener(se);
        frame.addMouseListener(se);
        frame.addMouseMotionListener(se);

        frame.setVisible(true);
    }

    @Override
    public void run() {
        try {
            se = new SendEvent(panel, socket);
            new ReceiveScreen(panel, socket);
            drawGUI();
        } catch (PropertyVetoException | IOException e) {
            e.printStackTrace();
        }
    }
}
