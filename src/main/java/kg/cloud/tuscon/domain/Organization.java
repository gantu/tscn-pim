package kg.cloud.tuscon.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Organization implements Serializable{
	private int id;
	private String orgName;
	public Organization(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String toString(){
		return orgName;
	}
}
