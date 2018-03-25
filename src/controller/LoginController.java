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
				System.out.println("USER IS IN THE TABLE MY GUY!");
				if(gateway.authenticateUser(user)){
					System.out.println("SUCCESSFULLY LOGGED IN!");
					ViewManager.getInstance().setUser(user);
					parent.activateMenuProperties();
				}else{
					System.out.println("WRONG PASSWORD MY DUDE!");
				}
			} else{
				System.out.println("USER IS NOT IN THE TABLE MY GUY!");
			}
		} catch(SQLException e){
				e.printStackTrace();
		}

		//System.out.println(user.getUsername() + " logged in with password: " + user.getPassword());
	}
	
	@FXML void SignUpClicked(){
		user.setUsername(signupUsername.getText());
		user.setPassword(signupPassword.getText());
		System.out.println(user.getUsername() + " logged in with password: " + user.getPassword());
		parent.activateMenuProperties();
		ViewManager.getInstance().setUser(user);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
