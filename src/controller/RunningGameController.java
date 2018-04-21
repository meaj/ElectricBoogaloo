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
import model.UserGateway;
import utility.ViewManager;

public class RunningGameController extends Thread implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private ListView<Message> chatListView;
	@FXML private ListView<User> playerList;
	@FXML private ObservableList<Message> chatLog; 
	@FXML private Button voteButton;
	@FXML private Label labelRolesInMatch;
	private UserGateway userGateway;
	private MessageGateway messageGateway;
	private Thread thread;
	private Lobby lobby;
	private ObservableList<User> users;
	private LobbyGateway lobbyGateway;
	private RoleGateway roleGateway;
	private User player;
	public RunningGameController(Object lobby, User user, MessageGateway gate, LobbyGateway lobbygw, RoleGateway rolegw, UserGateway usergw){
		this.lobby = (Lobby) lobby;
		this.player = user;
		this.lobbyGateway = lobbygw;
		this.roleGateway = rolegw;	
		this.messageGateway = gate;
		this.userGateway = usergw;
		chatLog = FXCollections.observableArrayList();
		users = FXCollections.observableArrayList();
		this.player.setReady(false);
		this.start();
	}
	
	@FXML void onVoteClicked(){
		int sum = 0;
		User highestUser = new User();
		User selected = playerList.getSelectionModel().getSelectedItem();
		
		try {
			userGateway.voteForUser(selected.getUsername());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Message msg = new Message();
		msg.setMessage( player.getUsername()+ " has voted to jail " + selected.getUsername() );
		msg.setLobbyId(lobby.getId());
		msg.setSendUser("GameMaster");
		try {
			messageGateway.insert(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		voteButton.setDisable(true);

		for(User user: users){
			try {
				sum += userGateway.getNumVotesForUser(user.getUsername());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		if(sum == users.size()){
			
			int highest = 0;
			int numVotes = 0;
			for(User user: users){
				try {
					numVotes = userGateway.getNumVotesForUser(user.getUsername());
					if(numVotes > highest){
						highestUser = user;
						highest = numVotes;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			msg = new Message();
			Role role = new Role("Villager", -1);
			try {
				role = roleGateway.getRoleByUserId(highestUser.getId());
			} catch (SQLException e2) {

				e2.printStackTrace();
			}
			msg.setMessage(highestUser.getUsername() + " has been jailed." + " He was the " + role );
			msg.setLobbyId(lobby.getId());
			msg.setSendUser("GameMaster");
			try {
				messageGateway.insert(msg);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			msg = new Message();
			if(role.getRole().equals("Vampire")){
				msg.setMessage("Game Over! Villagers win!" );
			}else{
				msg.setMessage("Game Over! Vampires win!" );
			}
			msg.setLobbyId(lobby.getId());
			msg.setSendUser("GameMaster");
			
			try {
				messageGateway.insert(msg);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
				chatLog =messageGateway.getMessagesForLobby(lobby.getId());
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
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				users = lobbyGateway.getUsersByLobbyId(lobby.getId());
				Thread.sleep(1000);
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
	
	@FXML void readyUpButtonClicked() {
		try {
			if(player.getReady() == false) {
				player.setReady(true);
				lobbyGateway.updateReadyCount(lobby.getId(), 1);
			} else {
				player.setReady(false);
				lobbyGateway.updateReadyCount(lobby.getId(), -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		playerList.setItems(users);
		try {
			labelRolesInMatch.setText("Your Role: "+roleGateway.getRoleByUserId(player.getId()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

