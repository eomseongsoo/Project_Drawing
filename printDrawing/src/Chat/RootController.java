package Chat;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RootController implements Initializable{

	@FXML Button toggleButton;
	@FXML TextArea textArea;

	public static ExecutorService threadPool;
	public static Vector<ChatClient> clients=new Vector<ChatClient>();
	ServerSocket serverSocket;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		String ip="192.168.0.21";
		int port=7000;
		toggleButton.setOnAction(event->{
			if(toggleButton.getText().equals("시작하기")) {
				startServer(ip, port);
				Platform.runLater(()->{
					String message=String.format("[서버시작]\n",ip,port);
					textArea.appendText(message);
					toggleButton.setText("종료하기");
				});
			}else {
				stopServer();
				Platform.runLater(()->{
					String message=String.format("[서버종료]\n",ip,port);
					textArea.appendText(message);
					toggleButton.setText("시작하기");
				});
			}
		});

	}

	//서버를 구동시켜 클라이언트의 연결을 기다리는 메소드 입니다
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
		//클라이언트가 접속할때까지 계속 기다리는 쓰레드입니다
		Runnable thread=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Socket socket=serverSocket.accept();
						clients.addElement(new ChatClient(socket));
						System.out.println("[클라이언트 접속]"
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
	//서버를 작동을 중지시키는 메서드 입니다
	public void stopServer() {
		try {
			//현재 작동중인 모든 소켓닫기
			Iterator<ChatClient> iterator=clients.iterator();
			while(iterator.hasNext()) {
				ChatClient client=iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//서버 소켓 객체 닫기
			if(serverSocket!=null&&!serverSocket.isClosed())
				serverSocket.close();
			//쓰레드 풀 종료하기
			if(threadPool!=null&&!threadPool.isShutdown())
				threadPool.shutdownNow();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
