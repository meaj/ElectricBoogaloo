package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Message;
import model.MessageGateway;
import model.User;
import utility.ViewManager;

public class RunningGameController extends Thread implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private ListView<Message> chatListView;
	@FXML private ObservableList<Message> chatLog; 
	
	private MessageGateway gateway;
	private Thread thread;
	public RunningGameController(User user, MessageGateway gate){
		chatLog = FXCollections.observableArrayList();
		this.gateway = gate;
		this.start();
	}
	
	@FXML void onEnter(ActionEvent ae){
		if(!chatTextField.getText().equals("")) {
			Message m = new Message();
			m.setMessage(chatTextField.getText());
			m.setLobbyId(1);
			m.setSendUser(ViewManager.getInstance().getUser().getUsername());
			try {
				gateway.insert(m);
				chatLog = gateway.getMessages();
				chatTextField.clear();
				chatListView.setItems(chatLog);
			} catch (SQLException e) {
				System.out.println("Failed to update chatlog");
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				chatLog = gateway.getMessages();
			} catch (Exception e) {
				  e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				@Override public void run() {
					chatListView.setItems(chatLog);
				}
			});
		}
	}
	
	public void start () {
		 if (thread == null) {
			 thread = new Thread (this);
			 thread.setDaemon(true);
			 thread.start ();
		 }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatListView.setItems(chatLog);
	}
}

