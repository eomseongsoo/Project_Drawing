package ChatClient;



import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application{

	private RootController root;

	//������ ���α׷��� �۵���Ű�� �޼ҵ� �Դϴ�
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("start�޼��� ȣ��");
		Parent parent=FXMLLoader.load(getClass().getResource("root.fxml"));
		Scene scene=new Scene(parent);

		primaryStage.setTitle("[ä��Ŭ���̾�Ʈ]");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event->root.stopClient());
		
		primaryStage.show();
		//�����(���ٽ�)
		primaryStage.setOnCloseRequest(event->System.out.println("����Ŭ��"));
		
	
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
