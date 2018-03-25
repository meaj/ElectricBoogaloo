package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import utility.ViewManager;

public class ContainerController implements Initializable {
	@FXML private MenuBar menuBar;
	@FXML private MenuItem menuItemMatches;
	@FXML private MenuItem menuItemRules;
	@FXML private MenuItem menuItemExit;
	@FXML private BorderPane rootPane;
	

	
	public ContainerController(){
		
	}
	
	public void activateMenuProperties(){
		menuItemMatches.setDisable(false);
		menuItemRules.setDisable(false);
	}
	
	@FXML private void HomeClicked(ActionEvent event) throws IOException {
		

	}
	
	@FXML private void MatchesClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.MATCH_PAGE, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML private void RulesClicked(ActionEvent event) throws IOException {

	}
	
	@FXML private void ExitClicked(ActionEvent event) throws IOException {
        int warningButton=JOptionPane.showConfirmDialog (null, "Are you sure you would like to exit?","Warning",JOptionPane.OK_CANCEL_OPTION);
        if(warningButton == JOptionPane.OK_OPTION){
           System.exit(0);
        }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		menuBar.setFocusTraversable(true);
		menuItemMatches.setDisable(true);
		menuItemRules.setDisable(true);
	}
}
