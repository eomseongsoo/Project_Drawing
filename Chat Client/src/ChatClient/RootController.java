package ChatClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RootController implements Initializable{
	
	@FXML TextArea textArea;
	@FXML TextField userName;
	@FXML TextField iptext;
	@FXML TextField porttext;
	@FXML Button connectionButton;
	@FXML Button sendButton;
	@FXML TextField input;
	
	Socket socket;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		input.setOnAction(event->{
			send(userName.getText()+":"+input.getText()+"\n");
			input.setText("");
			input.requestFocus();
		
		});
		sendButton.setDisable(true);
		sendButton.setOnAction(event->{

			send(userName.getText()+":"+input.getText()+"\n");
			input.setText("");
			input.requestFocus();
		});
		connectionButton.setOnAction(event->{
			if(connectionButton.getText().equals("�����ϱ�")) {
				int port=Integer.parseInt(porttext.getText());
				
				startClient(iptext.getText(), port);
				Platform.runLater(()->{
					textArea.appendText("[ä�ù� ����]\n");
				});
				connectionButton.setText("�����ϱ�");
				input.setDisable(false);
				sendButton.setDisable(false);
				input.requestFocus();
			}else {
				stopClient();
				Platform.runLater(()->{
					textArea.appendText("[ä�ù� ����]\n");
				});
				connectionButton.setText("�����ϱ�");
				input.setDisable(true);
				sendButton.setDisable(true);
			}
		});
		connectionButton.requestFocus();

	}
	

	// Ŭ���̾�Ʈ ���α׷��� �۵��� �����ϴ� �޼ҵ� �Դϴ�
	public void startClient(String ip,int port) {
		Thread thread=new Thread() {
			public void run() {
				try {
					socket=new Socket(ip, port);
				
					receive();
				}catch(Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[���� ���� ����]");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}
	//Ŭ���̾�Ʈ ���α׷��� �۵��� �����ϴ� �޼ҵ� �Դϴ�
	public void stopClient() {
		try {
			if(socket!=null&&!socket.isClosed()) {
				socket.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	// �����κ��� �޽����� ���޹޴� �޼ҵ��Դϴ�
	public void receive() {

		while(true) {
			try {

				InputStream in=socket.getInputStream();
				byte[] buffer=new byte[512];
				int length=in.read(buffer);
				if(length==-1) throw new IOException();
				String message=new String(buffer,0,length,"utf-8");
				Platform.runLater(()->{
					textArea.appendText(message);

				});
			}catch(Exception e) {
				stopClient();
				break;
			}
		}
	}
	//������ ���� �޼����� �����ϴ� �޼ҵ��Դϴ�
	public void send(String message) {
		Thread thread=new Thread() {
			public void run() {
				try {
					OutputStream out=socket.getOutputStream();
					byte[] buffer=message.getBytes("utf-8");
					out.write(buffer);
					out.flush();
				}catch(Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}

}
