package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPBroadcasting {
	
	private static DatagramSocket socket = null;
	 
    public static void main(String[] args) throws IOException {
    	Scanner s = new Scanner(System.in);
    	while(true) {
    		broadcast("Hello", InetAddress.getByName("239.255.255.255"));
    		System.out.println("Send it !: ");
    		s.nextLine();
    	}
        
    }
 
    public static void broadcast(
      String broadcastMessage, InetAddress address) throws IOException {
    	
        socket = new DatagramSocket();
        socket.setBroadcast(true);
 
        byte[] buffer = broadcastMessage.getBytes();
 
        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, 4444);
        socket.send(packet);
        socket.close();
    }
}
