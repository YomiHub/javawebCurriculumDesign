package mybean.data;

public class FindPeople {
    String userName;   
    String signature;   
    String userLogImage;  
    String backnews="";
  /*  String passWord;  */ 

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
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

    /*public String getPassWord() { return passWord; }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }*/
}
