package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class RunningGameController implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private ListView<Message> chatListView;
	@FXML private ObservableList<Message> chatLog; 
	
	private MessageGateway gateway;
	
	public RunningGameController(User user, MessageGateway gate){
		chatLog = FXCollections.observableArrayList();
		this.gateway = gate;
	}
	
	@FXML void onEnter(ActionEvent ae){
		try {
		if(!chatTextField.getText().equals("")) {
				Message m = new Message();
				m.setMessage(chatTextField.getText());
				m.setLobbyId(1);
				m.setSendUser(ViewManager.getInstance().getUser().getUsername());
				gateway.insert(m);
				chatLog = gateway.getMessages();
				chatTextField.clear();
				chatListView.setItems(chatLog);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatListView.setItems(chatLog);
		}
	}

