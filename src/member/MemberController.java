package member;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MemberController {

    @FXML
    private Label lblStatus;
    
    @FXML
    private TextField txtUserName;
    
    @FXML
    private TextField txtPassword;
    
    public void Login(ActionEvent event) throws Exception{
        MemberDAO memberdao=new MemberDAO();
    	if(memberdao.login(txtUserName.getText(),txtPassword.getText())){
            lblStatus.setText("Login Success");
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource(".css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }else{
            lblStatus.setText("Login Failed");
        }
    }

}
