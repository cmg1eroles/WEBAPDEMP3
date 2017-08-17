package edu.webapde.bean;

import javax.persistence.*;

@Entity(name="photo")
public class Photo {
	@Id
	@Column(name="photoid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private int userid;
	@Column
	private String title;
	@Column(name="description")
	private String desc;
	@Column
	private boolean privacy;
	@Column
	private String filename;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean isPrivacy() {
		return privacy;
	}
	
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		return "Photo [id=" + id + ", userid=" + userid + ", title=" + title + ", desc=" + desc + ", privacy=" + privacy
				+ "]";
	}
}
