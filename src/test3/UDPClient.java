package test3;

import java.io.IOException;
import java.net.*;
import java.util.*; 

public class UDPClient {
	static DatagramSocket ds;
	List<Integer> TCP_Ports = new ArrayList<>();
	List<String> Names = new ArrayList<>();
	private int BroadCast_port = 4445;
	private InetAddress BroadCast_IP= null;
	
	public UDPClient(String name) throws IOException {
		 ds = new DatagramSocket();
		 receive(name);
		 System.out.println("I have all ports now");
	}
	  
	  public static void main(String[] args) throws Exception {
		  String myName = "Desho";
		  UDPClient cl = new UDPClient(myName);
		  
	  }
	public void receive(String name) throws IOException {
		int createOrJoin = 1;//1 to create room , 2 to join
		 
		  //I want to send a signal to tell UDP broadcaster that I want to broadcast my room info also
		 //Broadcast the message over all the network interfaces
		  Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
		  while (interfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
		    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
		      continue; // Don't want to broadcast to the loopback interface
		    }
		    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
		      InetAddress broadcast = interfaceAddress.getBroadcast();
		      if (broadcast == null) {
		        continue;
		      }
		      // Send the broadcast package!
		      try {
		    	  String msg ="Hello , server";
				     //String msg ="Create Desha "+myPort;
			    	 DatagramPacket addMe = new DatagramPacket(msg.getBytes(), msg.length(),broadcast, BroadCast_port);
			    	 ds.send(addMe);
		      } catch (Exception e) {
		      }
		      System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
		    }
		  }
		  System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
		    //Get address of the broadcaster
		    //InetAddress address = dp.getAddress();
		    //System.out.println(InetAddress.getLocalHost().getHostAddress());
		    //System.out.println(address.getHostAddress());
			Random random = new Random(System.currentTimeMillis());
			int num = random.nextInt(65536);
			int myPort = num<0?num*-1:num;
			
		    // String msg = "Hello everybody I am "+name+" , you can connect to me via TCP on port "+myPort;
			if(createOrJoin == 1) {
				//Get address of the broadcaster
			    //InetAddress address = dp.getAddress();
			    //System.out.println(InetAddress.getLocalHost().getHostAddress());
			    //System.out.println(address.getHostAddress());
			}
	    	 
//	    	 byte[] buff = new byte[1024];  
//	    	 DatagramPacket dummy = new DatagramPacket(buff, buff.length);  
//			    ds.receive(dummy);
	    	 //Receive available rooms
			
			while(true){
	  		//Receive first time
		    //DatagramSocket ds = new DatagramSocket(4444);
	  		//System.out.println(ds.getInetAddress());
	  		
		    byte[] buf = new byte[1024];  
		    DatagramPacket dp = new DatagramPacket(buf, 1024);  
		    ds.receive(dp);  
		    String str = new String(dp.getData(), 0, dp.getLength());  
		    System.out.println(str);
		    BroadCast_IP = ds.getInetAddress();
		    //ds.close();
		    
//		    if(createOrJoin == 1 && !str.equalsIgnoreCase("end")) {
//		    //Get address of the broadcaster
//		    //InetAddress address = dp.getAddress();
//		    //System.out.println(InetAddress.getLocalHost().getHostAddress());
//		    //System.out.println(address.getHostAddress());
//		     String msg = "Hello everybody I am device-Beta, you can connect to me via TCP on port 9898";
//	    	 DatagramPacket addMe = new DatagramPacket(msg.getBytes(), msg.length(),group, 6789);
//	    	 ds.send(addMe);
//		    
//		    //Send a message to broadcaster to include my 
//		    }
//		    else 
		    	if(!str.equalsIgnoreCase("end")){ // Get the TCP ports out of the messages
		    	String[] msg2 = str.split(" ");
		    	System.out.println(msg2[msg2.length-1]+" Name: "+msg2[4]);
		    	Integer port = new Integer(msg2[msg2.length-1]);
		    	if(!TCP_Ports.contains(port))
		    		{
		    		TCP_Ports.add(port);
		    		Names.add(msg2[4]);
		    		}

		    }
		    else if (str.equalsIgnoreCase("end")) {
		    	break ;
		    }
	  	}
	  	
	  	
	}
	public static void createRoom() {
		
		
	}
	
	
	
	
	
	
}

