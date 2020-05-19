package test3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Broadcaster {

	    private static DatagramSocket socket = null;
	    static int numOfRooms;
	    static ArrayList<String> messages = new ArrayList();
	    static ArrayList<String> ports = new ArrayList();
	    public static void main(String[] args) throws IOException, InterruptedException {
	    	socket = new DatagramSocket(4445, InetAddress.getByName("0.0.0.0"));
	        socket.setBroadcast(true);
	        
	    	messages.add("Hello everybody I am device-Larry, you can connect to me via TCP using port 6758");
	    	messages.add("Hello everybody I am device-Alpha, you can connect to me via TCP on port 8768");
	    	//messages.add("Hello everybody I am device-Beta, you can connect to me via TCP on port 9898");
	    	messages.add("end");
	    	numOfRooms = messages.size();
	    	while(true) {
	    		 byte[] buf = new byte[1000];
		    	 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		    	 socket.receive(recv);
		    	 String str = new String(recv.getData(), 0, recv.getLength());  
				 System.out.println(str);
				 InetAddress address = recv.getAddress();
		         int port = recv.getPort();
				 if(str.contains("Hello")) {
	    		//Send the available rooms
		    	for (int i =0;i<numOfRooms;i++)
		    		//broadcast(messages.get(i), InetAddress.getByName("255.255.255.255"));//228.5.6.7
		    	{
		    		byte[] buffer = messages.get(i).getBytes();
		    		
		            recv = new DatagramPacket(buffer, buffer.length, address, port);
		            
		            socket.send(recv);
		    	}
		    	
		    	System.out.println("Done broadcasting");
		    	//Thread.sleep(4000);
		    	//get their responses!
				 }
				 else if(str.contains("Create")) {
					 String[] parts = str.split(" ");
					 String name = parts[1];String port2 = parts[2];
					 if(!ports.contains(port2))
					 {
						 messages.remove(messages.size()-1);
						 messages.add("Hello everybody I am "+name+", you can connect to me via TCP using port "+port2);
						 messages.add("end");
						 System.out.println("Created new room with port = "+port2);
					 }
					 else System.out.println("Room already exist");
					 String response = "end";
					 byte[] buffer = response.getBytes();
					 recv = new DatagramPacket(buffer, buffer.length, address, port);
			         socket.send(recv);
			         
				 }
	    	
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