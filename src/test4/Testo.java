package test4;

public class Testo {
	
	
	
	public static void main(String[] args) {
		Thread discoveryThread = new Thread(BroadCastServer.getInstance());
		    discoveryThread.start();

		    
	}
}
