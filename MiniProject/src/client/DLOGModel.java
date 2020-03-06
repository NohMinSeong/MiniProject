package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DLOGModel implements Runnable{
	ObservableList<DLOGMember> ol = FXCollections.observableArrayList();
	ArrayList<DLOGMember> fal = new ArrayList<DLOGMember>();
	
	int mcount, count = 0;
	String filename;
	Socket so;
	BufferedReader br;
	PrintWriter pwr;
	DLOGMember dmem;
	DLOGController dc;
	
	public void myadd(String nickname, String imageString, int like, String text) {
		fal.add(new DLOGMember(nickname, imageString, like, text));
		for (int i = 0; i < fal.size(); i++) {
			ol.add(new DLOGMember(fal.get(i).getNickname(), fal.get(i).getImageString(), fal.get(i).getLike(), fal.get(i).getText()));
		}
	}
	
	public void myupdate(String nickname, String imageString, int like, String text, int index) {
		fal.set(index, new DLOGMember(nickname, imageString, like, text));
		ol.set(index, new DLOGMember(nickname, imageString, like, text));
		
		pwr.println("update★"+fal.get(index).getNickname()+","+
					fal.get(index).getImageString()+","+
					fal.get(index).getLike()+","+
					fal.get(index).getText()+","+index);
		pwr.flush();
	}
	

	@Override
	public void run() {
		try {
			pwr = new PrintWriter(so.getOutputStream());
			
			br = new BufferedReader(new InputStreamReader(so.getInputStream()));
			while(true) {
				String result = br.readLine().trim();
				if(result.equals("완료")) {
					break;
				}
//				System.out.println("클라가 받는 버퍼:" +result);
				
				String[] re = result.split("★");
				if(re[0].equals("sns")) {
					System.out.println("실행");
					String[] initfal = re[1].split(",");
					count = Integer.parseInt(initfal[2]);
					
					ol.clear();
					myadd(initfal[0], initfal[1],count, initfal[3]);
				}
			}
			
		} catch (Exception e) {
			System.out.println("클라 쓰레드 예외 발생");
			e.printStackTrace();
		}
	}
	
	
}
