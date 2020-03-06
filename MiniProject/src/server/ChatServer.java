package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

class ChatserverThread extends Thread {
	PrintWriter pw;
	BufferedReader br;
	String msg = "";
	ChatServer chatsv;
	
	public ChatserverThread(PrintWriter pw, BufferedReader br, ChatServer chatsv) {
		this.pw = pw;
		this.br = br;
		this.chatsv = chatsv;
	}
	public void run() {
		try {
			while(true) {
				msg = br.readLine().trim();
				System.out.println("서버에서 받은 버퍼 : " + msg);
				chatsv.sendmsg(msg);
				if(msg == null) break;
			}
			
		} catch(SocketException e) {
			chatsv.plist.remove(pw);
		}catch(IOException e) {
			System.out.println("IOException : " + e);
		}
		catch(NullPointerException e) {}
		catch(Exception e) {System.out.println("쓰레드 연결오류 : ");e.printStackTrace();}
		
	}
}
public class ChatServer extends Thread{
	ServerSocket ss = null;
	PrintWriter pw = null;
	BufferedReader br = null;
	Socket so = null;
	ArrayList<PrintWriter> plist = new ArrayList<PrintWriter>();
	
	public void sendmsg(String msg) {
		for(PrintWriter pw : plist) {
			pw.println(msg);
			pw.flush();
		}
	}
	public void run() {
		try {
			ss = new ServerSocket(7979);
			while(true) {
				System.out.println("채팅 서버 접속 대기중...");
				so = ss.accept();
				System.out.println("채팅접속");
				pw = new PrintWriter(so.getOutputStream());
				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				plist.add(pw);
				
				String msg = br.readLine().trim();
				System.out.println("서버가 받은 msg:" + msg);
				sendmsg(msg);
				
				new ChatserverThread(pw, br, this).start();
			}
		} catch (IOException e) {
			System.out.println("채팅 서버 소켓 오류 : " +e);
			e.printStackTrace();
			plist.remove(pw);
		}
	}
}
