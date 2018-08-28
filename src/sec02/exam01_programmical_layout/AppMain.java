package sec02.exam01_programmical_layout;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application{

	   // 컨테이너 설정과 각종 컨트롤들을 자바코드로 만듬
	   //(프로그램적 레이아웃 형태)
	   
	   @Override
	   public void start(Stage primaryStage) throws Exception {
	      HBox hbox = new HBox();
	      hbox.setPadding(new Insets(10, 10, 10, 10)); //안쪽 여백을 설정한다.
	      hbox.setSpacing(10); //컨트롤 간의 간격 조정
	      
	      TextField textfield = new TextField();
	      textfield.setPrefWidth(200); //textfield의 가로길이
	      
	      Button button = new Button();
	      button.setText("확인");
	      
	      
	      //button.setOnAction(event -> {Platform.exit();});
	      
	      //방법 1
	      /*hbox.getChildren().add(textfield);
	      hbox.getChildren().add(button);*/
	      
	      //방법2
	      ObservableList list = hbox.getChildren();
	      //HBox컨테이너에 컨트롤 추가
	      list.add(textfield);
	      list.add(button);
	      
	      
	      //화면에 루트 컨테이너를 Parent클래스를 상속받은 HBox로 설정
	      Scene scene = new Scene(hbox);
	      primaryStage.setTitle("AppMain");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }
	   
	   
	   public static void main(String[] args) {
	      Application.launch(args);

	   }

	}