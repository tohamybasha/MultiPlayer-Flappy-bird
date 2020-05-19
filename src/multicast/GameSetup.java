package multicast;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;

import game.FlappyBirdView;
import game.Main;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameSetup extends Application{

	public static void main(String[] args) {
		Application.launch(args);
		
	} 
//Socket s =null;
NewUDPClientSelfBroadCasting rec;
	@Override
	public void start(Stage pStage) throws Exception {
		//List of rooms 
		// Create the Lists for the ListViews
        ObservableList<String> roomsList = FXCollections.<String>observableArrayList();
        //roomsList.add("Desha , 9999");
		// Create the ListView for the seasons
        ListView<String> rooms = new ListView<>(roomsList);
		
		
		Button create = new Button("Create Room");
		Button refresh = new Button("Refresh");
		Button connect = new Button("Connect");
		HBox pane = new HBox();
		pane.getChildren().addAll(create,refresh,connect,rooms);
		Scene scene = new Scene(pane,600,500);
		create.setOnAction(e->{
			try {
			rec = new NewUDPClientSelfBroadCasting(1, "Moustafa");
			FlappyBirdView game = new FlappyBirdView(rec, rec.getSocket());
			game.start(pStage);
//			game.setStage(pStage);
			//game.start(pStage);
//			Thread th = new Thread(game);
//			th.start();
			//Thread th = new Thread(cl);
		    //th.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});
		
		refresh.setOnAction(e->{
			try {
				if(rec == null)
					rec = new NewUDPClientSelfBroadCasting(2,"Fawzy");
				else rec.receive();
				List<String> conn = rec.getConnections();
				for (String st :conn) {
					if(!roomsList.contains(st))
						roomsList.add(st);
				}
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		connect.setOnAction(e->{
			String con = rooms.getSelectionModel().getSelectedItem();
			String[] parts = con.split(",");
//			String name = parts[0];
			String port = parts[0];
			String ip = parts[1];
			//System.out.println(con);
			try {
				Socket s = new Socket(ip, Integer.parseInt(port));
				rec.passingDataFromSecond(s,"0");
				rec.setSocket(s);
				System.out.println(ip+"Connected! "+port);
				FlappyBirdView game = new FlappyBirdView(rec, s);
//				game.setStage(pStage);
				game.start(pStage);
//				Thread th = new Thread(game);
//				th.start();
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		pStage.setScene(scene);
	pStage.show();
	pStage.setTitle("");
	}

	
}
