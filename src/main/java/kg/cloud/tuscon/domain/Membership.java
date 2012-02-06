package kg.cloud.tuscon.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Membership implements Serializable{
	
	private String id;
	private String unityName;
	
	public Membership(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnityName() {
		return unityName;
	}

	@Override
	public String toString() {
		return id;
	}

	public void setUnityName(String unityName) {
		this.unityName = unityName;
	}
	

}
