package sec01.exam03_stage_scane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppMain extends Application{
	   //���ེ����� JavaFX Application
	   @Override
	   public void start(Stage primaryStage) throws Exception{
	      //VBox�� �����̳� Ŭ������ �� �����̸� �������� ����(��Ʈ��)����
	      //��ġ�ϴ� �����̳��̴�.(Parent�� ��۹޴´�)
	      VBox root = new VBox();
	      root.setPrefWidth(500); //���� ����
	      root.setPrefHeight(350); //���� ����
	      root.setAlignment(Pos.CENTER); //�߾� ����
	      root.setSpacing(20); //��ġ�Ǵ� ��Ʈ���� ������ 20�ȼ��� �ϰڴٶ�� �ڵ�
	      
	      Label label = new Label();
	      label.setText("�ȳ�! �ڹ�FX"); //Label�� ���뼳��
	      label.setFont(new Font(50)); //����ũ��
	      
	      Button button = new Button(); //��ư����
	      button.setText("Ȯ��"); //��ư ���� ����
	      
	      
	      //���ٽ����� ��ư�� Ŭ���� ����ǵ��� �̺�Ʈ ó���� ������ �߰���
	      button.setOnAction(event -> Platform.exit());
	      //���ٽ�-> �Լ��� �������̽�
	      //�ްԺ��� �ϳ��̸� ��ȣ ���� ����
	      //vbox�������� 
	      //hbox��������
	   /*   
	      button.setOnAction(event->{
	    	  System.out.println("Ǯ���� ����");
	    	  Platform.exit();
	      });
	      button.setOnAction(new EventHandler<ActionEvent>() {
			//�͸�����ü���� �����Ͽ� �̺�Ʈ����
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
			}
		});*/
	      //VBox(��Ʈ�����̳�)�� ��Ʈ�ѵ��� ��ġ�Ѵ�.
	      root.getChildren().add(label);
	      root.getChildren().add(button);
	      
	      //Scene�� �������� �Ű����� Parent��� �߻�Ŭ�����̴�.
	      //�Ͽ� ��ӹ޴� ����Ŭ���� ��� �����̳� Ŭ�������̴�.
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("AppMain"); //������ Ÿ��Ʋ ����
	      primaryStage.setScene(scene); //Stage�� scene(���)�� �߰�
	      primaryStage.show(); //������ �����ֱ�
	      /*
	      **�� ������ ����
	      1. ���� Stage�� ������ �� �� ������â�� �ش��Ѵ�.
	      2. �׸��� Stage�� �����Ǹ� ��� �� Scene�� �������ش�.
	      3. �׸��� Scene�� �����Ҷ� �Ű����� ��� ParentŬ������ ���µ�,
	      �߻�Ŭ�����̹Ƿ� ���� �����̳� Ŭ������ �Ѱ��ش�.
	      4. ParentŬ������ ��ӹ޴� �����̳ʿ� ���� ��Ʈ��(����)�� �߰���ġ�Ѵ�.
	      �������� �����ϸ� �ȴ�. �ϳ��ϳ� �������ΰ��� �ڿ��� ���� ���´�.
	      */
	   }
	   public static void main(String[] agrs) {
	      Application.launch(agrs);
	   }
	}
