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
//한명의 클라이 언트와 통신하도록 해주는 클라이언트 클래스 입니다
public class Chat {

	//room정보를 추가 하기 위해서 vactor를 추가
	RootController root;
	BufferedReader in;
	PrintWriter out;
	String id="익명";
	String room="방없음";
Socket socket;
public Chat(Socket socket) {
	this.socket=socket;
	receive();
}
public void run() {
	try {
		System.out.println(socket + " 접속됨");
		in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		out = 
			new PrintWriter(socket.getOutputStream(),true);
		//처음 클라이언트에 보내는 메세지
		out.println("사용하실 아이디와 방번호를 입력하세요."); 
		String data = null;
		boolean done = false;
		while(!done){
			String line = in.readLine();
			if(line==null) done = true;
			else routine(line);
		}
	} catch (Exception e) {
		root.removeClientThread(this);
		System.err.println(socket+"["+id+"] 끊어짐");
		e.printStackTrace();
	}
}
public void routine(String line) {
	//CHATALL:오늘은 불타는 금요일
	int idx = line.indexOf(':');
	//int len;
	String cmd = line.substring(0,idx);//CHATALL
	String data = line.substring(idx+1);//오늘은 불타는 금요일
	
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
				"["+id+"]님이 "+room+"번방에 입장하였습니다.", room);
	}else if(cmd.equals(ChatProtocol2.CHAT)){
		//CHAT:bbb;배고프나?
		idx = data.indexOf(';');
		cmd = data.substring(0,idx);//bbb
		data = data.substring(idx+1);//배고프나?
		Chat ct = root.findThread(cmd/*bbb*/);
		if(ct!=null){
			ct.sendMessage(ChatProtocol2.CHAT+":"+
					"["+id/*aaa*/+"]"+data);
		}else{
			sendMessage(ChatProtocol2.CHAT+":"+
				"["+cmd+"] 사용자가 아닙니다.");
		}
	}else if(cmd.equals(ChatProtocol2.CHATROOM)){
		sendRoomClient(ChatProtocol2.CHATROOM+":"+
				"["+id+"]"+data, room);
	}else if(cmd.equals(ChatProtocol2.MESSAGE)){
		//(C->S)MESSAGE:bbb;잠시만.... (aaa->bbb 쪽지)
		//(S->C)MESSAGE:aaa;잠시만....
		idx = data.indexOf(';');
		cmd = data.substring(0,idx);//bbb
		data = data.substring(idx+1);//잠시만....
		Chat ct = root.findThread(cmd/*bbb*/);
		if(ct!=null){
			ct.sendMessage(ChatProtocol2.MESSAGE+":"+
					id/*aaa*/+";"+data);
		}else{
			sendMessage(ChatProtocol2.CHAT+":"+
					"["+cmd+"] 사용자가 아닙니다.");
		}
	}
}

public void sendMessage(String msg){
	out.println(msg);
}
//반복적으로 클라이언트로 부터 메세지를 받는 메소드입니다
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
					System.out.println("[메시지 수신 성공]"
							+socket.getRemoteSocketAddress()
							+":"+Thread.currentThread().getName());
					String message=new String(buffer,0,length, "utf-8");
					for(Chat client:RootController.clients) {
						client.sendAllClient(message);
					}
				}
			}catch(Exception e) {
				try {
					System.out.println("[메시지 수신 오류]"
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
//전체 클라이언트들에게 메세지를 전송하는 메소드입니다
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
					System.out.println("[메시지 송신 오류]"
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
					System.out.println("[메시지 송신 오류]"
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
