package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
//�Ѹ��� Ŭ���� ��Ʈ�� ����ϵ��� ���ִ� Ŭ���̾�Ʈ Ŭ���� �Դϴ�
public class ChatClient {
Socket socket;
public ChatClient(Socket socket) {
	this.socket=socket;
	receive();
}
//�ݺ������� Ŭ���̾�Ʈ�� ���� �޼����� �޴� �޼ҵ��Դϴ�
public void receive() {
	// TODO Auto-generated method stub
	Runnable thread=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true) {
					InputStream in=socket.getInputStream();
					byte[] buffer=new byte[512];
					int length=in.read(buffer);
					if(length==-1) throw new IOException();
					System.out.println("[�޽��� ���� ����]"
							+socket.getRemoteSocketAddress()
							+":"+Thread.currentThread().getName());
					String message=new String(buffer,0,length, "utf-8");
					for(ChatClient client:ChatMain.clients) {
						client.send(message);
					}
				}
			}catch(Exception e) {
				try {
					System.out.println("[�޽��� ���� ����]"
							+socket.getRemoteSocketAddress()+": "+Thread.currentThread().getName());
					ChatMain.clients.remove(ChatClient.this);
					socket.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	};
	ChatMain.threadPool.submit(thread);
}
//�ش� Ŭ���̾�Ʈ���� �޼����� �����ϴ� �޼ҵ��Դϴ�
public void send(String message) {
	Runnable thread=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				OutputStream out=socket.getOutputStream();
				byte[] buffer=message.getBytes("utf-8");
				out.write(buffer);
				out.flush();
			}catch(Exception e) {
				try{
					System.out.println("[�޽��� �۽� ����]"
							+socket.getRemoteSocketAddress()
							+":"+Thread.currentThread().getName());
					ChatMain.clients.remove(ChatClient.class);
					socket.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	};
	ChatMain.threadPool.submit(thread);
	
}
}
