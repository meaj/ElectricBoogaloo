package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class ContainerController implements Initializable {
	@FXML private MenuBar menuBar;
	@FXML private MenuItem menuItemHome;
	@FXML private MenuItem menuItemMatches;
	@FXML private MenuItem menuItemRules;
	@FXML private MenuItem menuItemExit;
	@FXML private BorderPane rootPane;
	
	public ContainerController(){
		
	}
	
	@FXML private void HomeClicked(ActionEvent event) throws IOException {

	}
	
	@FXML private void MatchesClicked(ActionEvent event) throws IOException {
		
	}
	
	@FXML private void RulesClicked(ActionEvent event) throws IOException {

	}
	
	@FXML private void ExitClicked(ActionEvent event) throws IOException {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		menuBar.setFocusTraversable(true);
	}
}
