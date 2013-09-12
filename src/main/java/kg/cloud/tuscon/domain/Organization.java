package kg.cloud.tuscon.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Organization implements Serializable{
	private String id;
	private String orgName;
	
	public Organization(){}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String toString(){
		return id;
	}
}
