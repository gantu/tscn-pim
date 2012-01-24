package kg.cloud.tuscon.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Membership implements Serializable{
	
	private int id;
	private String unityName;
	
	public Membership(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnityName() {
		return unityName;
	}

	@Override
	public String toString() {
		return unityName;
	}

	public void setUnityName(String unityName) {
		this.unityName = unityName;
	}
	

}
