package com.om.domain;
// Generated 2015-11-19 11:30:47 by Hibernate Tools 4.0.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * DataSource generated by hbm2java
 */
@Entity
@Table(name="data_source"
    ,catalog="networkpublicopinionmap"
)
public class DataSource  implements java.io.Serializable {


     private String dataSourceId;
     private Writer writer;
     private String contentTopic;
     private String contentKeywords;
     private String dataType;
     private String dataUrl;
     private String title;
     private String content;
     private Date publishTime;
     private String website;
     private Integer visitNum;
     private Integer replyNum;
     private Date grabTime;
     private String websiteSection;
     private String state;
     private String titleFenci;
     private String contentFenci;
     private String calculateState;
     private Double emotionValue;
     private String emotionState;
     private String ssyClassifyResult;
     private String svmClassifyResult; //SVM分类结果
	 private String clusterResult;
     private String clusterResult2;//聚类结果DBScan
     private String wordWeight;//词的权重
     private boolean keypoint;
     private double hotNum; //专题下的帖子热度
     private Set<DataEvent> dataEvents = new HashSet<DataEvent>(0);
     private Set<Comment> comments = new HashSet<Comment>(0);

    public DataSource() {
    }

	
    public DataSource(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    public DataSource(String dataSourceId, Writer writer, String contentTopic, String contentKeywords, String dataType, String dataUrl, String title, String content, Date publishTime, String website, Integer visitNum, Integer replyNum, Date grabTime, String websiteSection, String state, String titleFenci, String contentFenci, String calculateState, Double emotionValue, String emotionState, Set<DataEvent> dataEvents, Set<Comment> comments) {
       this.dataSourceId = dataSourceId;
       this.writer = writer;
       this.contentTopic = contentTopic;
       this.contentKeywords = contentKeywords;
       this.dataType = dataType;
       this.dataUrl = dataUrl;
       this.title = title;
       this.content = content;
       this.publishTime = publishTime;
       this.website = website;
       this.visitNum = visitNum;
       this.replyNum = replyNum;
       this.grabTime = grabTime;
       this.websiteSection = websiteSection;
       this.state = state;
       this.titleFenci = titleFenci;
       this.contentFenci = contentFenci;
       this.calculateState = calculateState;
       this.emotionValue = emotionValue;
       this.emotionState = emotionState;
       this.dataEvents = dataEvents;
       this.comments = comments;
    }
   
     @Id 

    
    @Column(name="data_source_id", unique=true, nullable=false, length=32)
    public String getDataSourceId() {
        return this.dataSourceId;
    }
    
    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="writer_id")
    public Writer getWriter() {
        return this.writer;
    }
    
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    
    @Column(name="content_topic", length=32)
    public String getContentTopic() {
        return this.contentTopic;
    }
    
    public void setContentTopic(String contentTopic) {
        this.contentTopic = contentTopic;
    }

    
    @Column(name="content_keywords", length=65535)
    public String getContentKeywords() {
        return this.contentKeywords;
    }
    
    public void setContentKeywords(String contentKeywords) {
        this.contentKeywords = contentKeywords;
    }

    
    @Column(name="data_type", length=50)
    public String getDataType() {
        return this.dataType;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    
    @Column(name="data_url", length=400)
    public String getDataUrl() {
        return this.dataUrl;
    }
    
    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    
    @Column(name="title", length=400)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="content", length=65535)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="publish_time", length=10)
    public Date getPublishTime() {
        return this.publishTime;
    }
    
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    
    @Column(name="website", length=20)
    public String getWebsite() {
        return this.website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }

    
    @Column(name="visit_num")
    public Integer getVisitNum() {
        return this.visitNum;
    }
    
    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    
    @Column(name="reply_num")
    public Integer getReplyNum() {
        return this.replyNum;
    }
    
    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="grab_time", length=19)
    public Date getGrabTime() {
        return this.grabTime;
    }
    
    public void setGrabTime(Date grabTime) {
        this.grabTime = grabTime;
    }

    
    @Column(name="website_section", length=50)
    public String getWebsiteSection() {
        return this.websiteSection;
    }
    
    public void setWebsiteSection(String websiteSection) {
        this.websiteSection = websiteSection;
    }

    
    @Column(name="state", length=20)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    
    @Column(name="title_fenci", length=65535)
    public String getTitleFenci() {
        return this.titleFenci;
    }
    
    public void setTitleFenci(String titleFenci) {
        this.titleFenci = titleFenci;
    }

    
    @Column(name="content_fenci", length=65535)
    public String getContentFenci() {
        return this.contentFenci;
    }
    
    public void setContentFenci(String contentFenci) {
        this.contentFenci = contentFenci;
    }

    
    @Column(name="calculate_state", length=20)
    public String getCalculateState() {
        return this.calculateState;
    }
    
    public void setCalculateState(String calculateState) {
        this.calculateState = calculateState;
    }

    
    @Column(name="emotion_value", precision=22, scale=0)
    public Double getEmotionValue() {
        return this.emotionValue;
    }
    
    public void setEmotionValue(Double emotionValue) {
        this.emotionValue = emotionValue;
    }

    
    @Column(name="emotion_state", length=32)
    public String getEmotionState() {
        return this.emotionState;
    }
    
    public void setEmotionState(String emotionState) {
        this.emotionState = emotionState;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="dataSource")
    public Set<DataEvent> getDataEvents() {
        return this.dataEvents;
    }
    
    public void setDataEvents(Set<DataEvent> dataEvents) {
        this.dataEvents = dataEvents;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="dataSource")
    public Set<Comment> getComments() {
        return this.comments;
    }
    
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

	public String getSsyClassifyResult() {
		return ssyClassifyResult;
	}


	public void setSsyClassifyResult(String ssyClassifyResult) {
		this.ssyClassifyResult = ssyClassifyResult;
	}
	
	 public String getSvmClassifyResult() {
			return svmClassifyResult;
		}


		public void setSvmClassifyResult(String svmClassifyResult) {
			this.svmClassifyResult = svmClassifyResult;
		}

	public String getClusterResult() {
		return clusterResult;
	}


	public void setClusterResult(String clusterResult) {
		this.clusterResult = clusterResult;
	}


	public double getHotNum() {
		return hotNum;
	}


	public void setHotNum(double hotNum) {
		this.hotNum = hotNum;
	}


	public String getClusterResult2() {
		return clusterResult2;
	}


	public void setClusterResult2(String clusterResult2) {
		this.clusterResult2 = clusterResult2;
	}


	public String getWordWeight() {
		return wordWeight;
	}


	public void setWordWeight(String wordWeight) {
		this.wordWeight = wordWeight;
	}


	public boolean isKeypoint() {
		return keypoint;
	}


	public void setKeypoint(boolean keypoint) {
		this.keypoint = keypoint;
	}






}


