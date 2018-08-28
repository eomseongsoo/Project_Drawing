package sec02.exam02_fxml_layout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application{

	   @Override
	   public void start(Stage primaryStage) throws Exception {
	      /* 
	       * FXML을 이용한 레이아웃 배치함, 자바코드와 완전 분리됨 
	       * 지금은 자바코드에 익숙하겠지만, 복잡한 레이아웃이라면 자바코크도 이루어진것은
	       * 난해하고 이해하기도 어렵다
	       * 하지만 FXML파일로 레이아웃을 만들면 , 태그 작성에 어느정도 익숙해지면 코드파악이 매우 쉽고
	       * 아울러 자바코드 파일은 건들 필요가 없고 디자이너와 협업이 매우 쉽게 된다.
	       * 대다수의 현업에는 이렇게 이루어져 있다
	       */
	      
	      //root.fxml에 작성된ㄷ 데로 parent즉 , 컨테이너와 컨트롤을 생성해라
	      Parent parent = FXMLLoader.load(getClass().getResource("root.fxml"));
	      Scene scene = new Scene(parent);
	      primaryStage.setScene(scene);
	      primaryStage.setTitle("AnchorPaneExample");
	      primaryStage.show();
	   }
	   
	   public static void main(String[] args) {
	      launch(args);
	   }

	}
