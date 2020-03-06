package server;

import java.io.Serializable;

public class Member implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userid, userpw, username, petname;

	public Member(String id, String pw, String name, String petname) {
		userid = id;
		userpw = pw;
		username = name;
		this.petname = petname;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPetname() {
		return petname;
	}

	public void setPetname(String petname) {
		this.petname = petname;
	}
}