package test3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

public class NewUDPClientSelfBroadCasting {
	
	
	public static void main(String[] args) throws IOException {
		NewUDPClientSelfBroadCasting cl1 = new NewUDPClientSelfBroadCasting(2,"Fawzy");

	}
	
	public DatagramSocket ds;
	private int BroadCast_port = 4445;
	private InetAddress BroadCast_IP= null;
	private List<Integer> TCP_Ports = new ArrayList<>();
	private List<String> Names = new ArrayList<>();
	private List<String> connections = new ArrayList();
	public NewUDPClientSelfBroadCasting(int createOrJoin,String name) throws IOException {
		
		if(createOrJoin == 2)
			receive();
		else if( createOrJoin == 1)
			createRoomAndBroadCast(name);
	}
	
	public void receive() throws IOException {
		  //I want to send a signal to tell UDP broadcaster that I want to broadcast my room info also
			/*
			 * In the context of servers, 0.0.0.0 means "all IPv4 addresses on the local machine".
			 *  If a host has two Ip addresses, 192.168.1.1 and 10.1.2.1, and a server running on the host listens on 0.0.0.0,
			 *   it will be reachable at both of those IPs.
			 * */
			ds = new DatagramSocket(4445);
			int num = 0;
			while(true){

		    byte[] buf = new byte[1024];  
		    DatagramPacket dp = new DatagramPacket(buf, 1024);  
		    ds.receive(dp);  
		    String str = new String(dp.getData(), 0, dp.getLength());  
		    System.out.println(str);
		    //Get address of the broadcaster
		    BroadCast_IP = ds.getInetAddress();
	    	if(!str.equalsIgnoreCase("end")){ // Get the TCP ports out of the messages
	    	String[] msg2 = str.split(",");
	    	//System.out.println(msg2[msg2.length-1]+" Name: "+msg2[4]);
	    	Integer port = new Integer(msg2[msg2.length-1]);
	    	if(!TCP_Ports.contains(port))
	    		{
	    		num++;
	    		TCP_Ports.add(port);
	    		String name = msg2[1].substring(3, msg2.length-1);
	    		Names.add(name);
	    		connections.add(num+","+name+","+port+","+BroadCast_IP);
	    		}
		    }
		    else if (str.equalsIgnoreCase("end")) {
		    	break ;
		    }
	  	}
	  	
	  	
	}
	public void createRoomAndBroadCast(String name) throws SocketException, UnknownHostException {
				ds = new DatagramSocket();
        ds.setBroadcast(true);
        
		Random random = new Random(System.currentTimeMillis());
		int num = random.nextInt(65536);
		int myPort = num<0?num*-1:num;
		
		 //Broadcast the message over all the network interfaces
		  Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
		  while (interfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
		    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
		      continue; // Don't want to broadcast to the loop back interface
		    }
		    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
		      InetAddress broadcast = interfaceAddress.getBroadcast();
		      if (broadcast == null) {
		        continue;
		      }
		      try {
		    	  	String msg ="Hello everyone,I'm "+name+",connect through my port number: ,"+myPort;
			    	 DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(),broadcast, BroadCast_port);
			    	 ds.send(packet);
			    	 String endmsg ="end";
			    	 packet = new DatagramPacket(msg.getBytes(), msg.length(),broadcast, BroadCast_port);
			    	 ds.send(packet);
		      } catch (Exception e) {
		      }
		      System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
		    }
		  }
		  System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
		  //TCP_Connection(myPort);
		
	}
	public Socket TCP_Connection(int port) {
		Socket playSocket = null;
		try {
			ServerSocket welcome = new ServerSocket(port);
			playSocket = welcome.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return playSocket;
	}

	public int getBroadCast_port() {
		return BroadCast_port;
	}

	public InetAddress getBroadCast_IP() {
		return BroadCast_IP;
	}

	public List<String> getConnections() {
		return connections;
	}
	

}
