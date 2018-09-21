package ChatClient;



import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application{

	private RootController root;

	//실제로 프로그램을 작동시키는 메소드 입니다
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("start메서드 호출");
		Parent parent=FXMLLoader.load(getClass().getResource("root.fxml"));
		Scene scene=new Scene(parent);

		primaryStage.setTitle("[채팅클라이언트]");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event->root.stopClient());
		
		primaryStage.show();
		//종료시(람다식)
		primaryStage.setOnCloseRequest(event->System.out.println("종료클릭"));
		
	
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
