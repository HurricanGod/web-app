package cn.hurrican.model;

import java.util.ArrayList;
import java.util.List;

public class Riddle {
    private Integer id;

    /**
     * 来源平台
     */
    private Integer platformId;
    
    private Integer uid;
    
    /**
     * 活动Id
     */
    private Integer aid;

    /**
     * 谜语相关图片
     */
    private String imgUrl;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案json
     */
    private List<String> answerList;
    /**
     * 对应分值，跟 answerList一一对应，只有部分活动支持，比如双十一单身狗活动
     */
    private List<String> scoreList = new ArrayList<>();

    /**
     * 正确答案编号 0、1、2...
     */
    private String rightIndex;

    private Boolean deleted;
    
    
    @Override
    public String toString() {
        return "Riddle{" +
                "id=" + id +
                ", platformId=" + platformId +
                ", uid=" + uid +
                ", aid=" + aid +
                ", imgUrl='" + imgUrl + '\'' +
                ", question='" + question + '\'' +
                ", answerList=" + answerList +
                ", scoreList=" + scoreList +
                ", rightIndex='" + rightIndex + '\'' +
                ", deleted=" + deleted +
                '}';
    }
    
    public Boolean getDeleted() {
        return deleted;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public Integer getUid() {
        return uid;
    }


    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getPlatformId() {
        return platformId;
    }


    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }


    public Integer getAid() {
        return aid;
    }

    /**  设置活动ID **/
    public void setAid(Integer aid) {
        this.aid = aid;
    }


    public String getImgUrl() {
        return imgUrl;
    }


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public String getRightIndex() {
        return rightIndex;
    }


    public void setRightIndex(String rightIndex) {
        this.rightIndex = rightIndex;
    }

	public List<String> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<String> scoreList) {
		this.scoreList = scoreList;
	}
}
