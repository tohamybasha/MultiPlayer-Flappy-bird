package multicast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

import game.FlappyBirdView;

public class NewUDPClientSelfBroadCasting implements Runnable{
	
	public static void main(String[] args) throws IOException {
		NewUDPClientSelfBroadCasting cl1 = new NewUDPClientSelfBroadCasting(1,"Moustafa");
	}
	public int playerNumber ;
	boolean gameOver = false;
	MyTCPConnection tcp;
	Thread th;
	String myScore;
	private Socket socket=null;
	public DatagramSocket ds;
	private int BroadCast_port = 4445;
	private InetAddress BroadCast_IP= null;
	private List<Integer> TCP_Ports = new ArrayList<>();
	private List<String> Names = new ArrayList<>();
	private List<String> connections = new ArrayList();
	public NewUDPClientSelfBroadCasting(int createOrJoin,String name) throws IOException  {
		
		if(createOrJoin == 2)
			{
			receive();
			playerNumber = 2;
			}
		else if( createOrJoin == 1)
			{
			playerNumber = 1;
			createRoomAndBroadCast(name);
			}
	}
	
	public void receive() throws IOException {
		  //I want to send a signal to tell UDP broadcaster that I want to broadcast my room info also
			if(ds == null)
				ds = new DatagramSocket(4445);//, InetAddress.getByName("0.0.0.0")
//			int num = 0;
//			while(true){

		    byte[] buf = new byte[1024];  
		    DatagramPacket dp = new DatagramPacket(buf, 1024);  
		    ds.receive(dp);  
		    String str = new String(dp.getData(), 0, dp.getLength());  
		    System.out.println(str);
		    //Get address of the broadcaster
		    BroadCast_IP = dp.getAddress();
	    	if(!str.equalsIgnoreCase("end")){ // Get the TCP ports out of the messages
	    	String[] msg2 = str.split(",");
	    	//System.out.println(msg2[msg2.length-1]+" Name: "+msg2[4]);
	    	Integer port = new Integer(msg2[msg2.length-1]);
	    	if(!TCP_Ports.contains(port))
	    		{
	    		//num++;
	    		TCP_Ports.add(port);
	    		String name = msg2[1].substring(3, msg2.length-1);
	    		Names.add(name);
	    		System.out.println(BroadCast_IP.getHostAddress());
	    		connections.add(port+","+BroadCast_IP.getHostAddress());
	    		}
		    }
//		    else if (str.equalsIgnoreCase("end")) {
//		    	break ;
//		    }
//	  	}
	  	
	  	
	}
	
	public void createRoomAndBroadCast(String name) throws SocketException, UnknownHostException {
		if(ds == null)
			{
			ds = new DatagramSocket();
			ds.setBroadcast(true);
			}
        
		Random random = new Random(System.currentTimeMillis());
		int num = random.nextInt(65536);
		int myPort = num<0?num*-1:num;
		while(socket == null){
		 //Broadcast the message over all the network interfaces
		  Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
		  while (interfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
		    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
		      continue; // Don't want to broadcast to the loop back interface
		      //A loopback interface is a logical, virtual interface in a Cisco Router. 
		      //A loopback interface is not a physical interface like Fast Ethernet interface or Gigabit Ethernet interface.

		    }
		    for (InterfaceAddress interfaceAddr : networkInterface.getInterfaceAddresses()) {
		      InetAddress broadcast = interfaceAddr.getBroadcast();
		      if (broadcast == null) {
		        continue;
		      }
		      try {
		    	  	String msg ="Hello everyone,I'm "+name+",connect through my port number: ,"+myPort;
			    	 DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(),broadcast, BroadCast_port);
			    	 ds.send(packet);
//			    	 String endmsg ="end";
//			    	 packet = new DatagramPacket(msg.getBytes(), msg.length(),broadcast, BroadCast_port);
//			    	 ds.send(packet);
		      } catch (Exception e) {
		      }
		      System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
		    }
		  }
		  System.out.println(getClass().getName() + ">>> Done looping over all network interfaces.");
//		  socket = TCP_Connection(myPort);
//		  System.out.println("\nClient connected");
		  if(th == null )
		  {
			  tcp  = new MyTCPConnection(myPort);
			  th = new Thread(tcp);th.start();
			  
		  }
		  socket = tcp.getPlaySocket(); 
		  try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		  try {
			  passingDataFromFirst(socket,"0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String passingDataFromFirst(Socket s, String score) throws IOException {
		//Reading characters from the client via input stream on the socket
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		//Get character output stream to client ( for headers )
		PrintWriter out = new PrintWriter(s.getOutputStream());
		//Get binary output stream to client (for requested data )
		//dataOut = new BufferedOutputStream(s.getOutputStream());
		out.println(score);
		out.flush();
		String enemyScore = in.readLine();
		//System.out.println(enemyScore);
		return enemyScore;
		
	}
	public String passingDataFromSecond(Socket s,String score) throws IOException {
		//Get character output stream to client ( for headers )
		PrintWriter out = new PrintWriter(s.getOutputStream());
		//Reading characters from the client via input stream on the socket
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		//Get binary output stream to client (for requested data )
		//dataOut = new BufferedOutputStream(s.getOutputStream());
		out.println(score);
		out.flush();
		String enemyScore = in.readLine();
		//System.out.println(enemyScore);
		return enemyScore;
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

	public int getPlayerNumber() {
		return playerNumber;
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getMyScore() {
		return myScore;
	}

	public void setMyScore(String myScore) {
		this.myScore = myScore;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String score="";
		if (this.getPlayerNumber() == 1){
			try {
				if(!gameOver)
				score = passingDataFromSecond(this.socket, ""+myScore);
				else score = passingDataFromSecond(this.socket,"-1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(this.getPlayerNumber() == 2){
			try {
				if(!gameOver)
					score = passingDataFromFirst(this.socket, ""+myScore);
					else score = passingDataFromFirst(this.socket,"-1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FlappyBirdView.enemyScor = score;
		
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	

}
