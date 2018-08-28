package sec01.exam01_application_lifecycle;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppMain extends Application{

	public AppMain() {
		System.out.println("������ ȣ��");
		System.out.println("���ེ����: "+Thread.currentThread());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("init����");
		System.out.println("���� ������: "+Thread.currentThread().getName());
		Application.launch(args);
	}
	//javafx Launcher�����尡 ������
	@Override
	public void init() throws Exception{
		//init()������ ui���� �� �����ϴ� �ڵ尡 ����� ���� �ȵȴ�
		//init()�� �ַ� ���� �Ű����� javafx application�����忡 ���� �������� ���ǰ� �ߴ�
		
	System.out.println("init����");
	System.out.println("���� ������: "+Thread.currentThread().getName());
	}
	//
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	
		System.out.println("start()_����");
		System.out.println("���� ������: "+Thread.currentThread().getName());
		primaryStage.show();
	}
	@Override
	public void stop() throws Exception{
		
		//������â�� Xǥ�ø� Ŭ���� �ڵ�����
		//application�� ����� ��������� �ڵ���� �ַ� �ۼ��Ѵ�
		System.out.println("stop()_����");
		System.out.println("���� ������: "+Thread.currentThread().getName());
	}
	
}
