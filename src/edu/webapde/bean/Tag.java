package edu.webapde.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="tag")
public class Tag {
	@Id
	@Column(name="tagid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private int photoid;
	@Column
	private String tagname;
	
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
	
	public String getTagname() {
		return tagname;
	}
	
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", photoid=" + photoid + ", tagname=" + tagname + "]";
	}
}
