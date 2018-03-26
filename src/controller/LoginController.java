package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import model.UserGateway;
import utility.AlertHelper;
import utility.ViewManager;

public class LoginController implements Initializable, GeneralController {
	
	@FXML private TextField loginUsername;
	@FXML private TextField signupUsername;
	@FXML private PasswordField loginPassword;
	@FXML private PasswordField signupPassword;
	
	private User user;
	private ContainerController parent;
	private UserGateway gateway;
	
	public LoginController(ContainerController parent, UserGateway gate){
		user = new User();
		this.gateway = gate;
		this.parent = parent;
	}
	
	@FXML void LoginClicked(){
		user.setUsername(loginUsername.getText());
		user.setPassword(loginPassword.getText());
		try{
			if(gateway.findUser(user.getUsername())){
				if(gateway.authenticateUser(user)){
					parent.activateMenuProperties();
				}else{
					AlertHelper.showWarningMessage("Error", 
							"Incorrect password", 
							"Please enter the correct password and try again");
				}
			} else{
				AlertHelper.showWarningMessage("Error", 
						"Username is not signed up", 
						"Please sign up below and try again");
			}
		} catch(SQLException e){
				e.printStackTrace();
		}
	}
	
	@FXML void SignUpClicked(){
		user.setUsername(signupUsername.getText());
		user.setPassword(signupPassword.getText());
		if(user.getUsername().equals(""))
			return;
		try{
			if(gateway.findUser(user.getUsername())){
				AlertHelper.showWarningMessage("Error", 
						"Username is already in use", 
						"Please enter a new Username and try again");
			} else{
				gateway.insert(user);
				System.out.println("Added "+user.getUsername()+" to table");
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		parent.activateMenuProperties();
		ViewManager.getInstance().setUser(user);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
