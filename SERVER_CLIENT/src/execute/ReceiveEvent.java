package execute;
import java.awt.*;
import java.io.*;
import java.net.Socket;
public class ReceiveEvent extends Thread {
    Socket socketEvent;
    Robot robot;
    ObjectInputStream is;
    boolean loop = true;
    public ReceiveEvent(Socket socket) throws AWTException, IOException {
        socketEvent = socket;
        robot = new Robot();
        is =  new ObjectInputStream(socketEvent.getInputStream());
        start();
    }

    public void run() {
        while (loop) {
            try {
                int command = (int) is.readObject();
                switch (command) {
                    case 1 -> robot.keyPress((Integer) is.readObject());
                    case 2 -> robot.mousePress((Integer) is.readObject());
                    case -1 -> robot.keyRelease((Integer) is.readObject());
                    case -2 -> robot.mouseRelease((Integer) is.readObject());
                    case 3 -> robot.mouseMove((Integer) is.readObject(), (Integer) is.readObject());
                }
            } catch (IOException | ClassNotFoundException ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
