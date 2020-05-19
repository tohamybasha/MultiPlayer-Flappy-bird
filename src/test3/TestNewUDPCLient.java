package test3;

import java.io.IOException;

public class TestNewUDPCLient {

	public static void main(String[] args) {
		try {
			NewUDPClientSelfBroadCasting cl1 = new NewUDPClientSelfBroadCasting(1,"Moustafa");
			NewUDPClientSelfBroadCasting cl2 = new NewUDPClientSelfBroadCasting(2,"Fawzy");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
