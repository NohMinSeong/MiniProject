package server;

import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import client.DLOGMember;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

class ClientThread implements Runnable{
	Socket so;
	BufferedReader br;
	PrintWriter pwr;
	Model md;
	DLOGModel dm;
	
	
	ClientThread(Socket so, Model mo, DLOGModel dm, PrintWriter pwr) {
		this.so=so;
		this.md = mo;
		this.dm = dm;
		this.pwr = pwr;
		try {
			br = new BufferedReader(new InputStreamReader(so.getInputStream()));
			
		} catch (Exception e) {
			System.out.println("예외 발생 4");
			e.printStackTrace();
		}
	}
	
	public void idCheck(String id) {
		for(Member mem : md.list) {
			if(mem.getUserid().equals(id)) {
				pwr.println("f");
				pwr.flush();
				return;
			}
		}
		pwr.println("s");
		pwr.flush();
	}
	
	public void join(String info) {
		String[] join = info.split(",");
		md.list.add(new Member(join[0],join[1],join[2],join[3]));
	}
	
	public void login(String login) {
		String[] loginCheck = login.split(",");
		for(Member mem : md.list) {
			if(mem.getUserid().equals(loginCheck[0])) {
				if(mem.getUserpw().equals(loginCheck[1])) {
					pwr.println("logins★" + mem.getUserid()+"," + mem.getUserpw()+"," + mem.getUsername() + "," +mem.getPetname());
					pwr.flush();
					md.sendAll();
					
					return;
				}
			}
		}
		pwr.println("loginf"); 
		pwr.flush();
		
	}
	
	public void sns(String vbox) {
	
		String[] snsvbox = vbox.split(",");
		dm.myadd(snsvbox[0], snsvbox[1], snsvbox[2], snsvbox[3]);
		pwr.println("sns★" + snsvbox[0] +"," + snsvbox[1] +"," 
					+snsvbox[2]+","+snsvbox[3]);
		pwr.flush();
	}
	
	public void update(String vbox) {
		String[] snsvbox = vbox.split(",");
		int count = Integer.parseInt(snsvbox[2]);
		dm.myupdate(snsvbox[0], snsvbox[1], count, snsvbox[3], snsvbox[4]);
	}

	@Override
	public void run() {
		try {
			while(true) {
				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				String first = br.readLine();
				System.out.println("서버 :" + first);
				if(first == null) break;
				String[] secon = first.split("★");
				if (secon[0].equals("id")) idCheck(secon[1]);
				if (secon[0].equals("회원가입")) join(secon[1]);
				if (secon[0].equals("로그인")) login(secon[1]);
				if (secon[0].equals("sns")) {sns(secon[1]);}
				if(secon[0].equals("update")) {update(secon[1]);}
			}
			boolean delete = md.plist.remove(pwr);
			md.con.renewMsg();
		}
		catch(SocketException e) {
			md.plist.remove(pwr);
			md.con.renewMsg();
		
		}catch(IOException e) {
			System.out.println("IOException : " + e);
		}
		catch(NullPointerException e) {}
		catch(Exception e) {System.out.println("쓰레드 연결오류 : ");e.printStackTrace();}
		
	}
}
	
public class Model implements Runnable{

	ArrayList<Member> list = new ArrayList<Member>();
	ArrayList<PrintWriter> plist = new ArrayList<PrintWriter>();
	ServerSocket ss;
	Socket so; 
	Controller con;
	DLOGModel dm;
	ChatServer chats;
	
	public void sendAll() {
		for(PrintWriter pw : plist) {
			dm.load();
			for(int i=0;i <dm.fal.size(); i++) {
				pw.println("sns★" + dm.fal.get(i).getNickname() + "," + 
						dm.fal.get(i).getImageString()+","+
						dm.fal.get(i).getLike()+","+
						dm.fal.get(i).getText());
				pw.flush();
				
			}
			pw.println("완료");
			pw.flush();
		}
	}
	
	@Override
	public void run() {
		dm = new DLOGModel();
		PrintWriter pw = null;
		list.add(new Member("1","1","1","시바"));
		list.add(new Member("2","2","2","백구"));
		try {
			ss = new ServerSocket(7777);
			
			chats = new ChatServer();
			chats.start();
			
			while(true) {
				System.out.println("접속 대기중...");
				so = ss.accept();
				pw = new PrintWriter(so.getOutputStream());
				plist.add(pw);
				new Thread(new ClientThread(so, this, dm, pw)).start();
				con.renewMsg(plist.size());		
			}
		} catch (IOException e) {
			System.out.println("입출력 예외(1) : " + e);
			plist.remove(pw);
		}
	}
}
