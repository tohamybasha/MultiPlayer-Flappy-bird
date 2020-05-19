//package test2;
//import java.io.IOException;
//import java.net.*;
//import java.util.*; 
//
//public class UDPClient {
//	static MulticastSocket ds;
//	List<Integer> TCP_Ports = new ArrayList<>();
//	public UDPClient() throws IOException {
//		 ds = new MulticastSocket(4444);
//		 receive();
//		 System.out.println("I have all ports now");
//	}
//	  
//	  public static void main(String[] args) throws Exception {
//		  UDPClient cl = new UDPClient();
//		  
//	  }
//	public void receive() throws IOException {
//		int createOrJoin = 1;//1 to create room , 2 to join
//		 
//		  //I want to send a signal to tell UDP broadcaster that I want to broadcast my room info also
//		  
//		  
//		  //I want just to know all the available rooms
//		//Join the chatting group
//		InetAddress group = InetAddress.getByName("233.255.255.255");
//		ds.joinGroup(group);
//		
//		if(createOrJoin == 1) {
//		    //Get address of the broadcaster
//		    //InetAddress address = dp.getAddress();
//		    //System.out.println(InetAddress.getLocalHost().getHostAddress());
//		    //System.out.println(address.getHostAddress());
//		     String msg = "Hello everybody I am device-Beta, you can connect to me via TCP on port 6565";
//	    	 DatagramPacket addMe = new DatagramPacket(msg.getBytes(), msg.length(),group, 4444);
//	    	 ds.send(addMe);
//		}
//	  	while(true) {
//	  		//Receive first time
//		    //DatagramSocket ds = new DatagramSocket(4444);
//	  		//System.out.println(ds.getInetAddress());
//	  		
//		    byte[] buf = new byte[1024];  
//		    DatagramPacket dp = new DatagramPacket(buf, 1024);  
//		    ds.receive(dp);  
//		    String str = new String(dp.getData(), 0, dp.getLength());  
//		    System.out.println(str);
//		    //ds.close();
//		    
////		    if(createOrJoin == 1 && !str.equalsIgnoreCase("end")) {
////		    //Get address of the broadcaster
////		    //InetAddress address = dp.getAddress();
////		    //System.out.println(InetAddress.getLocalHost().getHostAddress());
////		    //System.out.println(address.getHostAddress());
////		     String msg = "Hello everybody I am device-Beta, you can connect to me via TCP on port 9898";
////	    	 DatagramPacket addMe = new DatagramPacket(msg.getBytes(), msg.length(),group, 6789);
////	    	 ds.send(addMe);
////		    
////		    //Send a message to broadcaster to include my 
////		    }
////		    else 
//		    	if(!str.equalsIgnoreCase("end")){ // Get the TCP ports out of the messages
//		    	String[] msg = str.split(" ");
//		    	System.out.println(msg[msg.length-1]);
//		    	TCP_Ports.add(new Integer(msg[msg.length-1]));
//		    	
//		    }
//		    else if (str.equalsIgnoreCase("end")) {
//		    	return ;
//		    }
//	  	}
//	}
//}
