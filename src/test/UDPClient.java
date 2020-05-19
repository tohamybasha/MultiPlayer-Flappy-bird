package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {

	 private MulticastSocket socket;
	    private InetAddress address;
	 
	    private byte[] buf;
	 
	    public UDPClient() throws IOException {
	        socket = new MulticastSocket(4445);
	        
	    }
	 
	    public String receive() throws IOException {
	    	address = InetAddress.getByName("192.168.1.255");
	    	System.out.println("Waiting for messages...");
	    	socket.joinGroup(address);
	    	
	    	while(true) {
	        buf = new byte[256];
	        //DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
	        //socket.send(packet);
	        DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        socket.receive(packet);
	        String received = new String(
	          packet.getData(), 0, packet.getLength());
	        System.out.println("Message is: "+received);
	        //return received;
	    }
	    }
	 
	    public void close() {
	        socket.close();
	    }
	}
