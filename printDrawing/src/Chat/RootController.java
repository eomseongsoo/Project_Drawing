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
	public static Vector<Chat> clients=new Vector<Chat>();
	ServerSocket serverSocket;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		String ip="192.168.0.21";
		int port=7000;
		toggleButton.setOnAction(event->{
			if(toggleButton.getText().equals("�����ϱ�")) {
				startServer(ip, port);
				Platform.runLater(()->{
					String message=String.format("[��������]\n",ip,port);
					textArea.appendText(message);
					toggleButton.setText("�����ϱ�");
				});
			}else {
				stopServer();
				Platform.runLater(()->{
					String message=String.format("[��������]\n",ip,port);
					textArea.appendText(message);
					toggleButton.setText("�����ϱ�");
				});
			}
		});

	}

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
						clients.addElement(new Chat(socket));
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
			Iterator<Chat> iterator=clients.iterator();
			while(iterator.hasNext()) {
				Chat client=iterator.next();
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
	public void removeClientThread(Chat ct) {
		clients.remove(ct);
	}

	//�Ű������� id�� ClientThread ã�Ƽ� ����
	public Chat findThread(String id2){
		Chat ct = null;
		for (int i = 0; i < clients.size(); i++) {
			ct = clients.get(i);
			if(ct.id.equals(id2)) break;
		}
		return ct;
	}

	//���ӵ� ��� id�� ����(; ����) ex)aaa,bbb,ȫ�浿
	public String getIds(String room){
		String ids = "";
		for (int i = 0; i < clients.size(); i++) {
			if(clients.get(i).room.equals(room)){
				Chat ct = clients.get(i);
				ids+=ct.id+",";//aaa,bbb,ccc,ddd,
			}
		}
		return ids;
	}
	
	

}
