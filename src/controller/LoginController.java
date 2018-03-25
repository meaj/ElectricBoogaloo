package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;

public class LoginController implements Initializable, GeneralController {
	
	@FXML private TextField loginUsername;
	@FXML private TextField signupUsername;
	@FXML private PasswordField loginPassword;
	@FXML private PasswordField signupPassword;
	
	private User user;
	private ContainerController parent;
	public LoginController(ContainerController parent){
		user = new User();
		this.parent = parent;
	}
	
	@FXML void LoginClicked(){
		user.setUsername(loginUsername.getText());
		user.setPassword(loginPassword.getText());
		System.out.println(user.getUsername() + " logged in with password: " + user.getPassword());
		parent.activateMenuProperties();
	}
	
	@FXML void SignUpClicked(){
		user.setUsername(signupUsername.getText());
		user.setPassword(signupPassword.getText());
		System.out.println(user.getUsername() + " logged in with password: " + user.getPassword());
		parent.activateMenuProperties();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
