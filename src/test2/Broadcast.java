package test2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class Broadcast {

	    private static DatagramSocket socket = null;
	    static int numOfRooms;
	    static ArrayList<String> messages = new ArrayList();
	    
	    public static void main(String[] args) throws IOException, InterruptedException {
	    	socket = new DatagramSocket();
	        socket.setBroadcast(true);
	        
	    	messages.add("Hello everybody I am device-Larry, you can connect to me via TCP using port 6758");
	    	messages.add("Hello everybody I am device-Alpha, you can connect to me via TCP on port 8768");
	    	//messages.add("Hello everybody I am device-Beta, you can connect to me via TCP on port 9898");
	    	messages.add("end");
	    	numOfRooms = messages.size();
	    	
	    	

	    	while(true) {
	    	
	    	
		    	for (int i =0;i<numOfRooms;i++)
		    		broadcast(messages.get(i), InetAddress.getByName("233.255.255.255"));//228.5.6.7
		    	
		    	System.out.println("Done broadcasting");
		    	Thread.sleep(4000);
		    	// get their responses!
//		    	 byte[] buf = new byte[1000];
//		    	 DatagramPacket recv = new DatagramPacket(buf, buf.length);
//		    	 socket.receive(recv);
//		    	 String str = new String(recv.getData(), 0, recv.getLength());  
//				 System.out.println(str);
	    	
	    	}
	    	
	    	//socket.close();
	    
	    	  }
	    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
	        byte[] buffer = broadcastMessage.getBytes();
	        DatagramPacket packet 
	          = new DatagramPacket(buffer, buffer.length, address, 4445);
	        socket.send(packet);
	        
	        
	    }
	
}
//// Broadcast the message over all the network interfaces
//Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
//while (interfaces.hasMoreElements()) {
//  NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
//  if (networkInterface.isLoopback() || !networkInterface.isUp()) {
//    continue; // Don't want to broadcast to the loopback interface
//  }
//  for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
//    InetAddress broadcast = interfaceAddress.getBroadcast();
//    if (broadcast == null) {
//      continue;
//    }
//    // Send the broadcast package!
//    try {
//  	  byte[] buffer = messages.get(1).getBytes();
//  	  DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, broadcast, 8888);
//      socket.send(sendPacket);
//    } catch (Exception e) {
//    }
//  }
//  }
