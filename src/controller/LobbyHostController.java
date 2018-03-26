package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;
import utility.ViewManager;

public class LobbyHostController implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private Button startGameButton;
	@FXML private Button signupPassword;
	@FXML private ListView<String> pregameChatListView;
	@FXML private ListView<User> playerListView;
	private ObservableList<String> chatLog; 
	private User user;
	public LobbyHostController(User user){
		this.user = user;
		chatLog = FXCollections.observableArrayList();
	}

	@FXML private void startGameButtonClicked() {
		try {
			ViewManager.getInstance().changeView(ViewManager.RUNNING_GAME, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		pregameChatListView.setItems(chatLog);
	}

}
