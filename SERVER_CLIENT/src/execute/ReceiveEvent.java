package execute;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
public class ReceiveEvent extends Thread {
    Socket socketEvent;
    Robot robot;
    ObjectInputStream is;
    boolean loop = true;
    int command = 0;
    public ReceiveEvent(Socket socket) throws AWTException, IOException {
        socketEvent = socket;
        robot = new Robot();
        is =  new ObjectInputStream(socketEvent.getInputStream());
        start();
    }

    public void run() {
        try {
            while (loop) {
                command = (int) is.readObject();
                switch (command) {
                    case 1:
                        robot.keyPress((Integer) is.readObject());
                        break;
                    case 2 :
                        robot.mousePress((Integer) is.readObject());
                        break;
                    case -1 :
                        robot.keyRelease((Integer) is.readObject());
                        break;
                    case -2 :
                        robot.mouseRelease((Integer) is.readObject());
                        break;
                    case 3 :
                        robot.mouseMove((Integer) is.readObject(), (Integer) is.readObject());
                        break;
                }
            }
        }catch (IOException | ClassNotFoundException ignored) {
            loop = false;
            JOptionPane.showMessageDialog(new JFrame(),ignored.getMessage(),"Error 404",JOptionPane.WARNING_MESSAGE);
        }
    }
}
