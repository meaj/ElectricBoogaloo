package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;

public class RunningGameController implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private ListView<String> chatListView;
	@FXML private ObservableList<String> chatLog; 
	
	private User user;
	
	public RunningGameController(User user){
		this.user = user;
		chatLog = FXCollections.observableArrayList();
	}
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
		    @Override
		    public void handle(KeyEvent ke){
		      if (ke.getCode().equals(KeyCode.ENTER)) {
		    	  chatLog.add(user.getUsername() + ": " + chatTextField.getText());
		    	  chatTextField.clear();
		         }
		      }
		    });
			chatListView.setItems(chatLog);
		}

		
	}

