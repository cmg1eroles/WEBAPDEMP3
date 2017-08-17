package edu.webapde.bean;

import javax.persistence.*;

@Entity(name="shared")
public class Shared {
	@Id
	@Column(name="sharedid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private int photoid;
	@Column
	private int userid;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPhotoid() {
		return photoid;
	}
	
	public void setPhotoid(int photoid) {
		this.photoid = photoid;
	}
	
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Shared [photoid=" + photoid + ", userid=" + userid + "]";
	}
}
