package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.bind.util.ValidationEventCollector;

import javafx.scene.control.TextArea;
//�Ѹ��� Ŭ���� ��Ʈ�� ����ϵ��� ���ִ� Ŭ���̾�Ʈ Ŭ���� �Դϴ�
public class Chat {

	//room������ �߰� �ϱ� ���ؼ� vactor�� �߰�
	RootController root;
	BufferedReader in;
	PrintWriter out;
	String id="�͸�";
	String room="�����";
Socket socket;
public Chat(Socket socket) {
	this.socket=socket;
	receive();
}
public void run() {
	try {
		System.out.println(socket + " ���ӵ�");
		in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		out = 
			new PrintWriter(socket.getOutputStream(),true);
		//ó�� Ŭ���̾�Ʈ�� ������ �޼���
		out.println("����Ͻ� ���̵�� ���ȣ�� �Է��ϼ���."); 
		String data = null;
		boolean done = false;
		while(!done){
			String line = in.readLine();
			if(line==null) done = true;
			else routine(line);
		}
	} catch (Exception e) {
		root.removeClientThread(this);
		System.err.println(socket+"["+id+"] ������");
		e.printStackTrace();
	}
}
public void routine(String line) {
	//CHATALL:������ ��Ÿ�� �ݿ���
	int idx = line.indexOf(':');
	//int len;
	String cmd = line.substring(0,idx);//CHATALL
	String data = line.substring(idx+1);//������ ��Ÿ�� �ݿ���
	
	if(cmd.equals(ChatProtocol2.ID)){
		//ID:aaa
		if(data!=null&&data.length()>0){
			int idx1 = data.indexOf('/');
			id = data.substring(0,idx1);
			room = data.substring(idx1+1);
			//id=data;
		}
		sendRoomClient(ChatProtocol2.CHATLIST+":"+
				root.getIds(room), room);//CHATLIST:aaa,bbb,ccc
		sendRoomClient(ChatProtocol2.CHATROOM+":"+
				"["+id+"]���� "+room+"���濡 �����Ͽ����ϴ�.", room);
	}else if(cmd.equals(ChatProtocol2.CHAT)){
		//CHAT:bbb;�������?
		idx = data.indexOf(';');
		cmd = data.substring(0,idx);//bbb
		data = data.substring(idx+1);//�������?
		Chat ct = root.findThread(cmd/*bbb*/);
		if(ct!=null){
			ct.sendMessage(ChatProtocol2.CHAT+":"+
					"["+id/*aaa*/+"]"+data);
		}else{
			sendMessage(ChatProtocol2.CHAT+":"+
				"["+cmd+"] ����ڰ� �ƴմϴ�.");
		}
	}else if(cmd.equals(ChatProtocol2.CHATROOM)){
		sendRoomClient(ChatProtocol2.CHATROOM+":"+
				"["+id+"]"+data, room);
	}else if(cmd.equals(ChatProtocol2.MESSAGE)){
		//(C->S)MESSAGE:bbb;��ø�.... (aaa->bbb ����)
		//(S->C)MESSAGE:aaa;��ø�....
		idx = data.indexOf(';');
		cmd = data.substring(0,idx);//bbb
		data = data.substring(idx+1);//��ø�....
		Chat ct = root.findThread(cmd/*bbb*/);
		if(ct!=null){
			ct.sendMessage(ChatProtocol2.MESSAGE+":"+
					id/*aaa*/+";"+data);
		}else{
			sendMessage(ChatProtocol2.CHAT+":"+
					"["+cmd+"] ����ڰ� �ƴմϴ�.");
		}
	}
}

public void sendMessage(String msg){
	out.println(msg);
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
					for(Chat client:RootController.clients) {
						client.sendAllClient(message);
					}
				}
			}catch(Exception e) {
				try {
					System.out.println("[�޽��� ���� ����]"
							+socket.getRemoteSocketAddress()+": "+Thread.currentThread().getName());
					RootController.clients.remove(Chat.this);
					socket.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	};
	RootController.threadPool.submit(thread);
}
//��ü Ŭ���̾�Ʈ�鿡�� �޼����� �����ϴ� �޼ҵ��Դϴ�
public void sendAllClient(String message) {
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
					RootController.clients.remove(Chat.class);
					socket.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	};
	RootController.threadPool.submit(thread);
	
}
public void sendRoomClient(String message,String room) {
	Runnable thread=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				OutputStream out=socket.getOutputStream();
				byte[] msg=message.getBytes("utf-8");
				byte[] rb=room.getBytes("utf-8");
				byte[] buffer=new byte[msg.length+rb.length];

				System.arraycopy(msg, 0, buffer, 0, msg.length);
				System.arraycopy(rb, 0, buffer, msg.length, rb.length);

				out.write(buffer);
				out.flush();
			}catch(Exception e) {
				try{
					System.out.println("[�޽��� �۽� ����]"
							+socket.getRemoteSocketAddress()
							+":"+Thread.currentThread().getName());
					RootController.clients.remove(Chat.class);
					socket.close();
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	};
	RootController.threadPool.submit(thread);
	
}


}
