package Chat;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatMain extends Application{

	RootController root=new RootController();
	
	//ui�� �����ϰ� ���������� ���α׷��� �۵���Ű�� �޼����Դϴ�
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start�޼��� ȣ��");
		Parent parent=FXMLLoader.load(getClass().getResource("root.fxml"));
		Scene scene=new Scene(parent);
		primaryStage.setTitle("[ä�ü���]");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event->root.stopServer());

		//�����(���ٽ�)
		primaryStage.setOnCloseRequest(event->System.out.println("����Ŭ��"));
	}
	public static void main(String[] args) {
		launch(args);
	}

}
