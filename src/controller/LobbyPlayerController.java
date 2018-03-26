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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Lobby;
import model.LobbyGateway;
import model.Message;
import model.MessageGateway;
import model.Role;
import model.RoleGateway;
import model.User;
import utility.ViewManager;

public class LobbyPlayerController extends Thread implements Initializable, GeneralController {
	
	@FXML private TextField chatTextField;
	@FXML private Button startGameButton;
	@FXML private ListView<Message> pregameChatListView;
	@FXML private ListView<User> playerListView;
	@FXML private Label playersLabel;
	@FXML private ListView<Role> roleListView;
	
	private ObservableList<Message> chatLog;
	private ObservableList<User> users;
	private ObservableList<Role> roles;
	private MessageGateway messageGateway;
	private LobbyGateway lobbyGateway;
	private RoleGateway roleGateway;
	private Thread thread;
	private Lobby lobby;
	private User user;
	private Role role = null;
	
	public LobbyPlayerController(Object lobby, MessageGateway messageGate, LobbyGateway lobbyGate, RoleGateway roleGate){
		this.lobby = (Lobby) lobby;
		this.messageGateway = messageGate;
		this.lobbyGateway = lobbyGate;
		this.roleGateway = roleGate;
		chatLog = FXCollections.observableArrayList();
		this.user = ViewManager.getInstance().getUser();
		this.start();
	}

	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				users = lobbyGateway.getUsersByLobbyId(lobby.getId());
				role = roleGateway.getRoleByUserId(user.getId());
			}  catch (InterruptedException e) {
				break;
			}	catch (Exception e) {
				  e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				@Override public void run() {
					pregameChatListView.setItems(chatLog);
					playerListView.setItems(users);
					playersLabel.setText("Players: " + users.size() + "/" + lobby.getMaxPlayers());
					if(role != null) {
						user.setRole(role.getRole());
						user.setLobbyId(lobby.getId());
						ViewManager.getInstance().changeView(ViewManager.RUNNING_GAME, lobby);
						stopThread();
					}
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
	
	public void stopThread() {
		thread.interrupt();
	}
	@FXML void readyUpButtonClicked() {
		User user = ViewManager.getInstance().getUser();
		try {
			if(user.getReady() == false) {
				user.setReady(true);
				lobbyGateway.updateReadyCount(lobby.getId(), 1);
			} else {
				user.setReady(false);
				lobbyGateway.updateReadyCount(lobby.getId(), -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML void onEnter(ActionEvent ae){
		if(!chatTextField.getText().equals("")) {
			Message m = new Message();
			m.setMessage(chatTextField.getText());
			m.setLobbyId(lobby.getId());
			m.setSendUser(ViewManager.getInstance().getUser().getUsername());
			try {
				messageGateway.insert(m);
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				chatTextField.clear();
				pregameChatListView.setItems(chatLog);
			} catch (SQLException e) {
				System.out.println("Failed to update chatlog");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pregameChatListView.setItems(chatLog);
		try {
			roles = roleGateway.getRoles(this.lobby.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		roleListView.setItems(roles);
	}
}
