package mybean.data;

public class FindInfo {
    Integer infoId;    //����ID
    String infoTitle;   //���ݱ���
    String infoDescribe;   //���ݼ���
    String infoDetail;   //��������
    Integer type;    //���ͣ�0��ʾ�ռǣ�1��ʾȤ��
    Integer support;   //������
    String infoAuthor;  //����
	String infoPic;  //����ͼƬ
    String backnews="";
    public String getBacknews() {
		return backnews;
	}

	public void setBacknews(String backnews) {
		this.backnews = backnews;
	}

	public FindInfo(Integer infoId, String infoTitle, String infoDescribe,String infoDetail,Integer type, Integer support,String infoAuthor,String infoPic) {
		this.infoId = infoId;
		this.infoTitle = infoTitle;
		this.infoDescribe = infoDescribe;
		this.infoDetail = infoDetail;
		this.type = type;
		this.support = support;
		this.infoAuthor = infoAuthor;
		this.infoPic = infoPic;
	}

    public Integer getInfoId() {
        return infoId;
    }
    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }


    public String getInfoTitle() {
        return infoTitle;
    }
    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoDescribe() {
        return infoDescribe;
    }
    public void setInfoDescribe(String infoDescribe) {
        this.infoDescribe = infoDescribe;
    }

    public String getInfoDetail() { return infoDetail; }
    public void setInfoDetail(String infoDetail) {
        this.infoDetail = infoDetail;
    }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public Integer getSupport() { return support; }
    public void setSupport(Integer support) { this.support = support; }

    public String getInfoAuthor() {
        return infoAuthor;
    }
    
    public void setInfoAuthor(String infoAuthor) { this.infoAuthor = infoAuthor; }
    
    public String getInfoPic() {
		return infoPic;
	}

	public void setInfoPic(String infoPic) {
		this.infoPic = infoPic;
	}
}

