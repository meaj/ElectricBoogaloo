package utility;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import controller.ContainerController;
import controller.GameSettingsController;
import controller.GeneralController;
import controller.LobbyHostController;
import controller.LobbyPlayerController;
import controller.LoginController;
import controller.MatchPageController;
import controller.RulesController;
import controller.RunningGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.UserGateway;
import model.LobbyGateway;
import model.MessageGateway;
import model.RoleGateway;
import model.User;

public class ViewManager {
	
	private static ViewManager singleton = null;
	private BorderPane rootNode;
	private ContainerController container;
	private Connection connection;
	private User user;
	
	public static final int LOGIN = 1;
	public static final int MATCH_PAGE = 2;
	public static final int RUNNING_GAME = 3;
	public static final int LOBBY_HOST = 4;
	public static final int LOBBY_PLAYER = 5;
	public static final int GAME_SETTINGS = 6;
	public static final int RULES = 7;

	private ViewManager() {

	}

	public static ViewManager getInstance() {
		if(singleton == null) {
			singleton = new ViewManager();
		}

		return singleton;
	}
	public void setContainer(ContainerController c){
		container = c;
	}
	
	public void setPane(BorderPane root) {
		rootNode = root;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
	public void changeView(int viewType, Object arg) {
		GeneralController controller = null;
		URL fxmlFile = null;
		switch(viewType) {
			case LOGIN:
				fxmlFile = this.getClass().getResource("../view/Login.fxml");
				controller = new LoginController(container, new UserGateway(connection));
				break;
			case MATCH_PAGE:
				fxmlFile = this.getClass().getResource("../view/MatchPage.fxml");
				controller = new MatchPageController(user, new LobbyGateway(connection), new UserGateway(connection), new RoleGateway(connection));
				break;
			case RUNNING_GAME:
				fxmlFile = this.getClass().getResource("../view/RunningGameView.fxml");
				controller = new RunningGameController(arg, user, new MessageGateway(connection));
				break;
			case LOBBY_HOST:
				fxmlFile = this.getClass().getResource("../view/LobbyHostView.fxml");
				controller = new LobbyHostController(arg, new MessageGateway(connection), new LobbyGateway(connection), new RoleGateway(connection));
				break;
			case LOBBY_PLAYER:
				fxmlFile = this.getClass().getResource("../view/LobbyPlayerView.fxml");
				controller = new LobbyPlayerController(arg, new MessageGateway(connection), new LobbyGateway(connection), new RoleGateway(connection));
				break;
			case GAME_SETTINGS:
				fxmlFile = this.getClass().getResource("../view/GameSettings.fxml");
				controller = new GameSettingsController(user, new LobbyGateway(connection), new UserGateway(connection), new RoleGateway(connection));
				break;
			case RULES:
				fxmlFile = this.getClass().getResource("../view/RulesView.fxml");
				controller = new RulesController();
				break;
		}
		FXMLLoader loader = new FXMLLoader(fxmlFile);
		loader.setController(controller);
		Parent viewNode;
		try {
			viewNode = loader.load();
			rootNode.setCenter(viewNode);
		} catch (IOException e) {
			System.out.println("Failed to load views to view number " + viewType);
			e.printStackTrace();
		}
		
	}
	
	public void setConnection(Connection con) {
		connection = con;
	}
	
	public Connection getConnection() {
		return connection;
	}

}