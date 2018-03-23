package utility;

import java.net.URL;

import controller.ContainerController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Launcher extends Application {

	
	
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
		stage.setTitle("Mafia Game my dudes");
		stage.setScene(scene);
		stage.show();
		
		fxmlFile = this.getClass().getResource("../view/Login.fxml");
		loader = new FXMLLoader(fxmlFile);
		LoginController lc = new LoginController();
		
		loader.setController(lc);
		
		Parent contentView = loader.load();
		rootNode.setCenter(contentView);
	}
}
