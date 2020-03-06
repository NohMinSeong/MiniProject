package client;

import java.io.Serializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DLOGMember implements Serializable{
	
	private static final long serialVersionUID = 1L;
	transient private VBox box;
	private String imageString, text;
	private String nickname;
	private int like;
	
	public DLOGMember(String nickname, String imageString, int like, String text) {
		this.imageString = imageString;
		this.text = text;
		this.nickname = nickname;
		this.like = like;
		
		VBox vb = new VBox();
		ImageView iv = new ImageView(imageString);
		iv.setFitHeight(100);
		iv.setFitWidth(200);
		
		vb.getChildren().add(new Text(nickname));
		VBox inner = new VBox(iv);
		inner.setAlignment(Pos.CENTER);
		vb.getChildren().add(inner);
		
		vb.getChildren().add(new Text("¡¡æ∆ø‰ : " + this.like ));
		vb.getChildren().add(new Text(text));
		vb.getChildren().add(new Text());
		
		this.box = vb;
		vb.setAlignment(Pos.CENTER_LEFT);
	}

	public VBox getBox() {
		return box;
	}

	public void setBox(VBox box) {
		this.box = box;
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
		return (int) like;
	}

	public void setLike(int like) {
		this.like = like;
	}

}