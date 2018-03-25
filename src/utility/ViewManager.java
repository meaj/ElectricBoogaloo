package utility;

import java.net.URL;

import controller.GeneralController;
import controller.LoginController;
import controller.MatchPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class ViewManager {
	
	private static ViewManager singleton = null;
	private BorderPane rootNode;
	
	public static final int LOGIN = 1;
	public static final int MATCH_PAGE = 2;
	public static final int MATCH_PLAYER = 3;
	public static final int LOBBY_HOST = 4;
	public static final int LOBBY_PLAYER = 5;

	private ViewManager() {
		
	}

	public static ViewManager getInstance() {
		if(singleton == null) {
			singleton = new ViewManager();
		}

		return singleton;
	}
	
	public void setPane(BorderPane root) {
		rootNode = root;
	}
	
	public void changeView(int viewType, Object arg) throws Exception{
		try {
			GeneralController controller = null;
			URL fxmlFile = null;
			switch(viewType) {
				case LOGIN:
					fxmlFile = this.getClass().getResource("../view/Login.fxml");
					controller = new LoginController();
					break;
				case MATCH_PAGE:
					fxmlFile = this.getClass().getResource("../view/MatchPage.fxml");
					controller = new MatchPageController();
					break;
				case MATCH_PLAYER:
					fxmlFile = this.getClass().getResource("../view/MatchPlayer.fxml");
					//controller = new BookListController(new BookGateway(conn));
					break;
				case LOBBY_HOST:
					fxmlFile = this.getClass().getResource("../view/LobbyHostView.fxml");
					//controller = new BookDetailController((Book) arg, publishers);
					break;
				case LOBBY_PLAYER:
					fxmlFile = this.getClass().getResource("../view/LobbyPlayerView.fxml");
					//controller = new AuditListController(new BookGateway(conn), (Book)arg );
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

}