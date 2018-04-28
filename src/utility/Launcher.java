package utility;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import controller.ContainerController;
import controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.LobbyGateway;
import model.RoleGateway;
import model.User;
import model.UserGateway;

public class Launcher extends Application {

	private Connection conn;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		URL fxmlFile = this.getClass().getResource("../view/ViewContainer.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlFile);
		ContainerController cc = new ContainerController();
		
		loader.setController(cc);
		BorderPane rootNode = loader.load();
		
		Scene scene = new Scene(rootNode, 800, 600);
		stage.setTitle("Vampire");
		stage.setScene(scene);
		stage.show();
		
		fxmlFile = this.getClass().getResource("../view/Login.fxml");
		loader = new FXMLLoader(fxmlFile);
		LoginController lc = new LoginController(cc, new UserGateway(conn));
		loader.setController(lc);
		
		ViewManager viewManager = ViewManager.getInstance();
		viewManager.setConnection(conn);
		viewManager.setPane((BorderPane) rootNode);
		viewManager.setContainer(cc);
		
		Parent contentView = loader.load();
		rootNode.setCenter(contentView);
	}
	
	@Override
	public void init() throws Exception {
		super.init();

		System.out.println("Creating connection...");
		
		try {
			conn = ConnectionFactory.createConnection();
		} catch (Exception e) {
			System.out.println("Cannot connect to db");
			Platform.exit();
		}
	}
	
	@Override
	public void stop() {
		User user = ViewManager.getInstance().getUser();
		if(user != null) {
			UserGateway usergate = new UserGateway(conn);
			try {
				usergate.updateUserLobby(ViewManager.getInstance().getUser(), -1);
				if(user.getLobbyId() > 0) {
					LobbyGateway lobbygate = new LobbyGateway(conn);
					ObservableList<User> users = lobbygate.getUsersByLobbyId(user.getLobbyId());
					if(users.size() == 0) {
						lobbygate.delete(user.getLobbyId());
					} else {
						if(user.getReady() == true) {
							lobbygate.updateReadyCount(user.getLobbyId(), -1);
						}
					}
				}
				usergate.resetUserVotes(user.getUsername());
				RoleGateway roleGate = new RoleGateway(conn);
				roleGate.updateRemoveUser(user.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	
}
