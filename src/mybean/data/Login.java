package mybean.data;

public class Login {
	String logname = "", backnews = "", changePassBacknews = "", changeRegisterInfoBack = "";
	String addInfoBacknews = "";
	String userLogImage;
	String signature;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAddInfoBacknews() {
		return addInfoBacknews;
	}

	public void setAddInfoBacknews(String addInfoBacknews) {
		this.addInfoBacknews = addInfoBacknews;
	}

	public String getChangeRegisterInfoBack() {
		return changeRegisterInfoBack;
	}

	public void setChangeRegisterInfoBack(String changeRegisterInfoBack) {
		this.changeRegisterInfoBack = changeRegisterInfoBack;
	}

	public String getChangePassBacknews() {
		return changePassBacknews;
	}

	public void setChangePassBacknews(String changePassBacknews) {
		this.changePassBacknews = changePassBacknews;
	}

	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public String getUserLogImage() {
		return userLogImage;
	}

	public void setUserLogImage(String userLogImage) {
		this.userLogImage = userLogImage;
	}

	public String getBacknews() {
		return backnews;
	}

	public void setBacknews(String backnews) {
		this.backnews = backnews;
	}
}
