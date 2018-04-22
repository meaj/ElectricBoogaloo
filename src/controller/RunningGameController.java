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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.Lobby;
import model.LobbyGateway;
import model.Message;
import model.MessageGateway;
import model.Role;
import model.RoleGateway;
import model.User;
import model.UserGateway;
import utility.AlertHelper;
import utility.ViewManager;

public class RunningGameController extends Thread implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private ListView<Message> chatListView;
	@FXML private ListView<User> playerList;
	@FXML private ObservableList<Message> chatLog; 
	@FXML private Button voteButton;
	@FXML private Label labelRolesInMatch;
	@FXML private CheckBox readyUpButton;
	@FXML private Button specialActionButton;
	private UserGateway userGateway;
	private MessageGateway messageGateway;
	private Thread thread;
	private Lobby lobby;
	private ObservableList<User> users;
	private LobbyGateway lobbyGateway;
	private RoleGateway roleGateway;
	private User player;
	private int turnCount;
	private boolean newTurn;
	private int numVampires;
	private int alivePlayers;
	
	public RunningGameController(Object lobby, User user, MessageGateway gate, LobbyGateway lobbygw, RoleGateway rolegw, UserGateway usergw){
		this.lobby = (Lobby) lobby;
		this.player = user;
		this.lobbyGateway = lobbygw;
		this.roleGateway = rolegw;	
		this.messageGateway = gate;
		this.userGateway = usergw;
		chatLog = FXCollections.observableArrayList();
		users = FXCollections.observableArrayList();
		turnCount=1;
		newTurn=false;
		numVampires=0;
		alivePlayers=this.lobby.getMaxPlayers();
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
		sendMessage("GameMaster", player.getUsername()+ " has voted to kill " + selected.getUsername());
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
			Role role = new Role("Villager", -1);
			try {
				role = roleGateway.getRoleByUserId(highestUser.getId());
			} catch (SQLException e2) {

				e2.printStackTrace();
			}
			sendMessage("GameMaster",highestUser.getUsername() + " has been jailed." + " He was the " + role );
			
		}
	}
	
	@FXML void onEnter(ActionEvent ae){
		if(!chatTextField.getText().equals("")) {
			sendMessage(ViewManager.getInstance().getUser().getUsername(),chatTextField.getText());
			try {
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				chatTextField.clear();
				chatListView.setItems(chatLog);
			} catch (SQLException e) {
				System.out.println("Failed to update chatlog");
				e.printStackTrace();
			}
		}
	}
	
	@FXML void specialActionPressed(){
		switch(player.getRole()){
			case "Vampire":
				handleVampireAction();
				break;
			case "Detective":
				handleDetectiveAction();
				break;
			case "Priest":
				handlePriestAction();
				break;
			case "Villager":
				handleVillagerAction();
				break;
		}
		specialActionButton.setDisable(true);

	}
	
	private void handleVillagerAction() {
		// TODO Auto-generated method stub
		
	}

	private void handlePriestAction() {
		// TODO Auto-generated method stub
		
	}

	private void handleDetectiveAction() {
		User selectedUser = playerList.getSelectionModel().getSelectedItem();
		if(selectedUser != null){
			try {
				Role selectedRole = roleGateway.getRoleByUserId(selectedUser.getId());
				System.out.println("Selected User " +selectedUser + " Role " +selectedRole);
				if(selectedRole.getRole().equals("Vampire")){
					AlertHelper.showWarningMessage("Detective.", "GUILTY", "After a thorough investigate, you have determined " + selectedUser + " to be a filthy vampire");
				}else{
					AlertHelper.showWarningMessage("Detective.", "INNOCENT", "After a thorough investigate, you have determined " + selectedUser + " is an innocent man. Relatively speaking.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			AlertHelper.showWarningMessage("Detective.", "No player selected", "Please choose a player from the list of players to investigate");
		}
		
	}

	private void handleVampireAction() {
		int sum = 0;
		User highestUser = new User();
		User selected = playerList.getSelectionModel().getSelectedItem();
		try {
			userGateway.voteForUser(selected.getUsername());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		specialActionButton.setDisable(true);
		for(User user: users){
			try {
				sum += userGateway.getNumVotesForUser(user.getUsername());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(sum == numVampires){
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
			
			try {
				userGateway.updateUserAlive(highestUser, 0);
				alivePlayers--;
				for(User user: users){
						userGateway.resetUserVotes(user.getUsername());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void setSpecialActionText(){
		switch(player.getRole()){
			case "Vampire":
				specialActionButton.setText("Devour");
				labelRolesInMatch.setText("Player: "+player.getUsername()+" \t\t\tYour Role: Vampire");
				specialActionButton.setTooltip(new Tooltip("Select a player in the lobby to devour"));
				break;
			case "Detective":
				specialActionButton.setText("Investigate");
				labelRolesInMatch.setText("Player: "+player.getUsername()+" \t\t\tYour Role: Detective");
				specialActionButton.setTooltip(new Tooltip("Select a player in the lobby to Investigate"));
				break;
			case "Villager":
				specialActionButton.setText("Panic.");
				labelRolesInMatch.setText("Player: "+player.getUsername()+" \t\t\tYour Role: Villager");
				specialActionButton.setTooltip(new Tooltip("Panic."));
				break;
			case "Priest":
				specialActionButton.setText("Protect");
				labelRolesInMatch.setText("Player: "+player.getUsername()+" \t\t\tYour Role: Priest");
				specialActionButton.setTooltip(new Tooltip("Protect a player of your choosing through the night"));
				break;
		}
	}
	
	public void run() {
		while(true){
			try {
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				users = lobbyGateway.getUsersByLobbyId(lobby.getId());
				//change later will need to account for DEATH
				if(lobbyGateway.getReadyCount(lobby.getId())==alivePlayers) {
					player.setAlive(userGateway.getAlive(player.getId()));
					turnCount++;
					readyUpButton.setSelected(false);
					player.setReady(false);
					newTurn=true;
					if(player.getAlive() == 0){
						disableButtons();
					}
					else if(turnCount%2==0) {
						this.specialActionButton.setDisable(false);
						this.chatTextField.setDisable(true);
						this.voteButton.setDisable(true);
					}
					else {
						this.specialActionButton.setDisable(true);
						this.chatTextField.setDisable(false);
						this.voteButton.setDisable(false);
					}
					Thread.sleep(2000);
					lobbyGateway.resetReadyCount(lobby.getId());
				}
				
				Thread.sleep(1000);
			} catch (Exception e) {
				  e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				@Override public void run() {
					chatListView.setItems(chatLog);
					if(newTurn) {
						newTurn=false;
						AlertHelper.showWarningMessage("New turn alert.","It is now turn "+turnCount+".","");
					}
				
				}
			});
			
		}
	}
	
	public void disableButtons(){
		voteButton.setDisable(true);
		readyUpButton.setDisable(true);
		chatTextField.setDisable(true);
		specialActionButton.setDisable(true);
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


		try {
			this.lobbyGateway.resetReadyCount(this.lobby.getId());
			this.lobbyGateway.setAliveCount(lobby.getId(), lobby.getMaxPlayers());
			this.userGateway.updateUserAlive(this.player, this.player.getAlive());
			users = lobbyGateway.getUsersByLobbyId(lobby.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(User user : users) {
			try {
				
				Role r=roleGateway.getRoleByUserId(user.getId());
				if(r.getRole().equals("Vampire")) {
					numVampires++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
		setSpecialActionText();
		this.specialActionButton.setDisable(true);


	}
	
	public void sendMessage(String sendUser, String content){
		Message msg = new Message();
		msg.setMessage(content);
		msg.setLobbyId(lobby.getId());
		msg.setSendUser(sendUser);
		try {
			messageGateway.insert(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

