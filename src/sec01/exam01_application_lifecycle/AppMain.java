package sec01.exam01_application_lifecycle;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppMain extends Application{

	public AppMain() {
		System.out.println("생성자 호출");
		System.out.println("실행스레드: "+Thread.currentThread());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("init실행");
		System.out.println("실행 스레드: "+Thread.currentThread().getName());
		Application.launch(args);
	}
	//javafx Launcher스레드가 실행함
	@Override
	public void init() throws Exception{
		//init()에서는 ui생성 및 변경하는 코드가 절대로 오면 안된다
		//init()은 주로 실행 매개값을 javafx application스레드에 전할 목적으로 사용되곤 했다
		
	System.out.println("init실행");
	System.out.println("실행 스레드: "+Thread.currentThread().getName());
	}
	//
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	
		System.out.println("start()_실행");
		System.out.println("실행 스레드: "+Thread.currentThread().getName());
		primaryStage.show();
	}
	@Override
	public void stop() throws Exception{
		
		//원도우창의 X표시를 클릭시 자동실행
		//application의 종료시 수행햐야할 코드들을 주로 작성한다
		System.out.println("stop()_실행");
		System.out.println("실행 스레드: "+Thread.currentThread().getName());
	}
	
}
