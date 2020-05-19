package multicast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyTCPConnection implements Runnable{
	Socket playSocket = null;
	int port ;
	public MyTCPConnection(int port){
		this.port = port;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket welcome = new ServerSocket(port);
			playSocket = welcome.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nClient connected");
	}
	public Socket getPlaySocket() {
		return playSocket;
	}
	public void setPlaySocket(Socket playSocket) {
		this.playSocket = playSocket;
	}
	
}
