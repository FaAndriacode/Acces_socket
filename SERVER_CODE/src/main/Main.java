package main;
import screen.Desktop;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static String ip;
    public static void main(String[] args){
        try{
            ServerSocket socket = new ServerSocket(5000);
            System.out.println("En attente de connexion");
            Socket sc = socket.accept();
            ip = sc.getRemoteSocketAddress().toString();
            System.out.println("Connexion Etablie avec "+ip);
            new Desktop(sc);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }






    }
}
