package Chat;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatMain extends Application{

	public static ExecutorService threadPool;
	public static Vector<ChatClient> clients=new Vector<ChatClient>();
	ServerSocket serverSocket;
	
	//������ �������� Ŭ���̾�Ʈ�� ������ ��ٸ��� �޼ҵ� �Դϴ�
	public void startServer(String ip,int port) {
		try {
			serverSocket=new ServerSocket();
			serverSocket.bind(new InetSocketAddress(ip, port));
			
		}catch(Exception e) {
			e.printStackTrace();
			if(!serverSocket.isClosed())
				stopServer();
			return;
		}
		//Ŭ���̾�Ʈ�� �����Ҷ����� ��� ��ٸ��� �������Դϴ�
		Runnable thread=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Socket socket=serverSocket.accept();
						clients.addElement(new ChatClient(socket));
						System.out.println("[Ŭ���̾�Ʈ ����]"
								+socket.getRemoteSocketAddress()
								+":"+Thread.currentThread().getName());
						
					}catch(Exception e) {
						if(!serverSocket.isClosed())
							stopServer();
						break;
					}
				}
			}
		};
		threadPool=Executors.newCachedThreadPool();
		threadPool.submit(thread);
	}
	//������ �۵��� ������Ű�� �޼��� �Դϴ�
	public void stopServer() {
		try {
			//���� �۵����� ��� ���ϴݱ�
			Iterator<ChatClient> iterator=clients.iterator();
			while(iterator.hasNext()) {
				ChatClient client=iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//���� ���� ��ü �ݱ�
			if(serverSocket!=null&&!serverSocket.isClosed())
				serverSocket.close();
			//������ Ǯ �����ϱ�
			if(threadPool!=null&&!threadPool.isShutdown())
				threadPool.shutdownNow();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
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
		primaryStage.setOnCloseRequest(event->stopServer());

		//�����(���ٽ�)
		primaryStage.setOnCloseRequest(event->System.out.println("����Ŭ��"));
	}
	public static void main(String[] args) {
		launch(args);
	}

}
