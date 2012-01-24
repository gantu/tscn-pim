package kg.cloud.tuscon.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Sektor implements Serializable {
	private int id;
	private String sektorName;

	public Sektor() {
	}

	public String getSektorName() {
		return sektorName;
	}

	public void setSektorName(String sektorName) {
		this.sektorName = sektorName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString(){
		return sektorName;
	}
}
