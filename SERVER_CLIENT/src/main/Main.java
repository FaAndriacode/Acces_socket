package main;
import execute.ReceiveEvent;
import screen.SendScreen;
import javax.swing.*;
import java.awt.AWTException;
import java.io.IOException;
import java.net.Socket;
public class Main {
    public static void main(String[] args){
        Windows frame = new Windows();
        String ip = JOptionPane.showInputDialog(frame,"Entrer l'adresse ip du serveur");
        try {
            Socket socket = new Socket(ip, 5000);
            System.out.println("Connection etablie au server" +ip);
            new SendScreen(socket);
            new ReceiveEvent(socket);
        }catch (Exception e){
            System.out.println("Vousd avez sortie de l'application");
        }

    }
}
