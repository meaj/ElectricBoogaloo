package utility;

import javafx.scene.layout.BorderPane;

public class ViewManager {
	
	private static ViewManager singleton = null;
	private BorderPane rootNode;

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
}
