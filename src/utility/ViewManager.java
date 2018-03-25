package utility;

import java.net.URL;
import java.sql.Connection;

import controller.ContainerController;
import controller.GameSettingsController;
import controller.GeneralController;
import controller.LobbyHostController;
import controller.LobbyPlayerController;
import controller.LoginController;
import controller.MatchPageController;
import controller.RunningGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.UserGateway;
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
	public void changeView(int viewType, Object arg) throws Exception{
		try {
			GeneralController controller = null;
			URL fxmlFile = null;
			switch(viewType) {
				case LOGIN:
					fxmlFile = this.getClass().getResource("../view/Login.fxml");
					controller = new LoginController(container, new UserGateway(connection));
					break;
				case MATCH_PAGE:
					fxmlFile = this.getClass().getResource("../view/MatchPage.fxml");
					controller = new MatchPageController();
					break;
				case RUNNING_GAME:
					fxmlFile = this.getClass().getResource("../view/RunningGameView.fxml");
					controller = new RunningGameController(user);
					break;
				case LOBBY_HOST:
					fxmlFile = this.getClass().getResource("../view/LobbyHostView.fxml");
					controller = new LobbyHostController(user);
					break;
				case LOBBY_PLAYER:
					fxmlFile = this.getClass().getResource("../view/LobbyPlayerView.fxml");
					controller = new LobbyPlayerController();
					break;
				case GAME_SETTINGS:
					fxmlFile = this.getClass().getResource("../view/GameSettings.fxml");
					controller = new GameSettingsController();
					break;
				case RULES:
					fxmlFile = this.getClass().getResource("../view/RulesView.fxml");
					controller = new RulesController();
					break;
			}
		
			FXMLLoader loader = new FXMLLoader(fxmlFile);
			loader.setController(controller);
		
			Parent viewNode = loader.load();
			rootNode.setCenter(viewNode);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public void setConnection(Connection con) {
		connection = con;
	}
	
	public Connection getConnection() {
		return connection;
	}

}