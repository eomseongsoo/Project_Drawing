package Chat;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatMain extends Application{

	RootController root=new RootController();
	
	//ui를 생성하고 실질적으로 프로그램을 작동시키는 메서드입니다
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start메서드 호출");
		Parent parent=FXMLLoader.load(getClass().getResource("root.fxml"));
		Scene scene=new Scene(parent);
		primaryStage.setTitle("[채팅서버]");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event->root.stopServer());

		//종료시(람다식)
		primaryStage.setOnCloseRequest(event->System.out.println("종료클릭"));
	}
	public static void main(String[] args) {
		launch(args);
	}

}
