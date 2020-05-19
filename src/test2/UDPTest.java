package test2;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPTest {
    EchoClient client;
    public static void main(String[] args) throws IOException {
		UDPTest t = new UDPTest();
		t.setup();
		t.whenCanSendAndReceivePacket_thenCorrect();
	}

    public void setup() throws SocketException, UnknownHostException{
        //new EchoServer().start();
        client = new EchoClient();
    }
 

    public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
        String echo = client.sendEcho("hello server");
        //assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        //assertFalse(echo.equals("hello server"));
    }
 

    public void tearDown() throws IOException {
        client.sendEcho("end");
        client.close();
    }
}