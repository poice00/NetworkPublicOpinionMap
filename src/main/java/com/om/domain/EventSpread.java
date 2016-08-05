package com.om.domain;
// Generated 2015-11-19 11:30:47 by Hibernate Tools 4.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EventSpread generated by hbm2java
 */
@Entity
@Table(name="event_spread"
    ,catalog="networkpublicopinionmap"
)
public class EventSpread  implements java.io.Serializable {


     private String eventSpreadId;
     private Event event;
     private Double replyNum;
     private Double browseRate;
     private Double developPeek;
     private Double inflectionPoint;
     private Date spreadDate;
     private Double positiveNum;
     private Double normalNum;
     private Double negtiveNum;

    public EventSpread() {
    }

	
    public EventSpread(String eventSpreadId) {
        this.eventSpreadId = eventSpreadId;
    }
    public EventSpread(String eventSpreadId, Event event, Double replyNum, Double browseRate, Double developPeek, Double inflectionPoint, Date spreadDate, Double positiveNum, Double normalNum, Double negtiveNum) {
       this.eventSpreadId = eventSpreadId;
       this.event = event;
       this.replyNum = replyNum;
       this.browseRate = browseRate;
       this.developPeek = developPeek;
       this.inflectionPoint = inflectionPoint;
       this.spreadDate = spreadDate;
       this.positiveNum = positiveNum;
       this.normalNum = normalNum;
       this.negtiveNum = negtiveNum;
    }
   
     @Id 

    
    @Column(name="event_spread_id", unique=true, nullable=false, length=32)
    public String getEventSpreadId() {
        return this.eventSpreadId;
    }
    
    public void setEventSpreadId(String eventSpreadId) {
        this.eventSpreadId = eventSpreadId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event_id")
    public Event getEvent() {
        return this.event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }

    
    @Column(name="reply_num", precision=22, scale=0)
    public Double getReplyNum() {
        return this.replyNum;
    }
    
    public void setReplyNum(Double replyNum) {
        this.replyNum = replyNum;
    }

    
    @Column(name="browse_rate", precision=22, scale=0)
    public Double getBrowseRate() {
        return this.browseRate;
    }
    
    public void setBrowseRate(Double browseRate) {
        this.browseRate = browseRate;
    }

    
    @Column(name="develop_peek", precision=22, scale=0)
    public Double getDevelopPeek() {
        return this.developPeek;
    }
    
    public void setDevelopPeek(Double developPeek) {
        this.developPeek = developPeek;
    }

    
    @Column(name="inflection_point", precision=22, scale=0)
    public Double getInflectionPoint() {
        return this.inflectionPoint;
    }
    
    public void setInflectionPoint(Double inflectionPoint) {
        this.inflectionPoint = inflectionPoint;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="spread_date", length=19)
    public Date getSpreadDate() {
        return this.spreadDate;
    }
    
    public void setSpreadDate(Date spreadDate) {
        this.spreadDate = spreadDate;
    }

    
    @Column(name="positive_num", precision=22, scale=0)
    public Double getPositiveNum() {
        return this.positiveNum;
    }
    
    public void setPositiveNum(Double positiveNum) {
        this.positiveNum = positiveNum;
    }

    
    @Column(name="normal_num", precision=22, scale=0)
    public Double getNormalNum() {
        return this.normalNum;
    }
    
    public void setNormalNum(Double normalNum) {
        this.normalNum = normalNum;
    }

    
    @Column(name="negtive_num", precision=22, scale=0)
    public Double getNegtiveNum() {
        return this.negtiveNum;
    }
    
    public void setNegtiveNum(Double negtiveNum) {
        this.negtiveNum = negtiveNum;
    }




}


