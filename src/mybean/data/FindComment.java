package mybean.data;

public class FindComment {
 	Integer commentId ;    //ID
 	String commentUser;   //������
 	Integer commentInfo;   //���۵�����ID
    String commentDetail;   //��������
 
    String backnews="";
    
    public FindComment(Integer commentId,String commentUser,Integer commentInfo, String commentDetail ) {
    	this.commentId = commentId;
     	this.commentUser = commentUser;   //������
        this.commentInfo = commentInfo;   //���۵�����ID
        this.commentDetail = commentDetail;   //��������
    }
    public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}

	public Integer getCommentInfo() {
		return commentInfo;
	}

	public void setCommentInfo(Integer commentInfo) {
		this.commentInfo = commentInfo;
	}

	public String getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}

	public String getBacknews() {
		return backnews;
	}

	public void setBacknews(String backnews) {
		this.backnews = backnews;
	}

	
	   
}
