package mybean.data;

public class FindComment {
 	Integer commentId ;    //ID
 	String commentUser;   //评论者
 	Integer commentInfo;   //评论的文章ID
    String commentDetail;   //内容详情
 
    String backnews="";
    
    public FindComment(Integer commentId,String commentUser,Integer commentInfo, String commentDetail ) {
    	this.commentId = commentId;
     	this.commentUser = commentUser;   //评论者
        this.commentInfo = commentInfo;   //评论的文章ID
        this.commentDetail = commentDetail;   //内容详情
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
