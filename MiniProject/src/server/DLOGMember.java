package server;

import java.io.Serializable;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DLOGMember implements Serializable{
	
	private String imageString, text;
	private String nickname;
	private int like;
	
	public DLOGMember(String nickname, String imageString, int like, String text) {
		this.imageString = imageString;
		this.text = text;
		this.nickname = nickname;
		this.like = like;
	}

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}
	
}
