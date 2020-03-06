package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import client.DLOGMember;

public class DLOGModel {
	ArrayList<DLOGMember> fal = new ArrayList<DLOGMember>();
	
	void save() {
		File f = new File("sns.txt");
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
		fos = new FileOutputStream(f);
		oos = new ObjectOutputStream(fos);
		oos.writeObject(fal);
		oos.flush();
		}catch(Exception e) { System.out.println(e); 
		}finally {
			try{oos.close();}catch(Exception e) {System.out.println(e);}
			try{fos.close();}catch(Exception e) {System.out.println(e);}
		}
	}
	
	void load() {
		File f = new File("sns.txt");
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			
			fal = (ArrayList<DLOGMember>)ois.readObject();
			

		} catch (Exception e) {
		} finally {
			try {ois.close(); } catch (Exception e) { System.out.println(e); System.out.println("≥Œ¿Ã∞°");} 
			try {fis.close(); } catch (Exception e) { System.out.println(e); } 			
		}
	}
	
	public void myadd(String nickname, String imageString, String like, String text) {
		int likecount = Integer.parseInt(like);
		fal.add(new DLOGMember(nickname, imageString, likecount, text));
		save();
	}
	
	public void myupdate(String nickname, String imageString, int like, String text, String index) {
		int i = Integer.parseInt(index);
		fal.set(i, new DLOGMember(nickname, imageString, like, text));
		save();
	}
}
