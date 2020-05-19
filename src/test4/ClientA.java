package test4;


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientA extends JApplet implements ActionListener, Runnable {

 JTextField tf;
JTextArea ta;
 MulticastSocket socket;
 InetAddress group;
 String name="";

public void start()  {
    try {
socket = new MulticastSocket(7777);
group = InetAddress.getByName("233.0.0.1");
socket.joinGroup(group); 
socket.setTimeToLive(255);
Thread th = new Thread(this);
th.start();
name =JOptionPane.showInputDialog(null,"Please enter your name.","What is your name?",JOptionPane.PLAIN_MESSAGE);
tf.grabFocus();
    }catch(Exception e) {e.printStackTrace();}
}

public void init() {

JPanel p = new JPanel(new BorderLayout());
ta = new JTextArea();
ta.setEditable(false);
ta.setLineWrap(true);
JScrollPane sp = new JScrollPane(ta);
p.add(sp,BorderLayout.CENTER);
JPanel p2 = new JPanel();
tf = new JTextField(30);
tf.addActionListener(this);
p2.add(tf);
JButton b = new JButton("Send");
b.addActionListener(this);
p2.add(b);
p.add(p2,BorderLayout.SOUTH);
add(p);

}

public void actionPerformed(ActionEvent ae) {
String message = name+":"+tf.getText();
tf.setText("");
tf.grabFocus();
byte[] buf = message.getBytes();
DatagramPacket packet = new DatagramPacket(buf,buf.length, group,7777);
try {
socket.send(packet);
}
catch(Exception e) {}
}



public void run() {
while(true) {
byte[] buf = new byte[256];
String received = "";
  DatagramPacket packet = new DatagramPacket(buf, buf.length);
try {
            socket.receive(packet);
             received = new String(packet.getData()).trim();
}
catch(Exception e) {}
ta.append(received +"\n");
ta.setCaretPosition(ta.getDocument().getLength());
}
}

}

