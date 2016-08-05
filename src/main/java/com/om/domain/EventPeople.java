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
 * EventPeople generated by hbm2java
 */
@Entity
@Table(name="event_people"
    ,catalog="networkpublicopinionmap"
)
public class EventPeople  implements java.io.Serializable {


     private String eventPeopleId;
     private Event event;
     private Double negativeReplyNum;
     private Double totalReplyNum;
     private Double neturalReplyNum;
     private Double browseNum;
     private Date peopleDate;

    public EventPeople() {
    }

	
    public EventPeople(String eventPeopleId) {
        this.eventPeopleId = eventPeopleId;
    }
    public EventPeople(String eventPeopleId, Event event, Double negativeReplyNum, Double totalReplyNum, Double neturalReplyNum, Double browseNum, Date peopleDate) {
       this.eventPeopleId = eventPeopleId;
       this.event = event;
       this.negativeReplyNum = negativeReplyNum;
       this.totalReplyNum = totalReplyNum;
       this.neturalReplyNum = neturalReplyNum;
       this.browseNum = browseNum;
       this.peopleDate = peopleDate;
    }
   
     @Id 

    
    @Column(name="event_people_id", unique=true, nullable=false, length=32)
    public String getEventPeopleId() {
        return this.eventPeopleId;
    }
    
    public void setEventPeopleId(String eventPeopleId) {
        this.eventPeopleId = eventPeopleId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event_id")
    public Event getEvent() {
        return this.event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }

    
    @Column(name="negative_reply_num", precision=22, scale=0)
    public Double getNegativeReplyNum() {
        return this.negativeReplyNum;
    }
    
    public void setNegativeReplyNum(Double negativeReplyNum) {
        this.negativeReplyNum = negativeReplyNum;
    }

    
    @Column(name="total_reply_num", precision=22, scale=0)
    public Double getTotalReplyNum() {
        return this.totalReplyNum;
    }
    
    public void setTotalReplyNum(Double totalReplyNum) {
        this.totalReplyNum = totalReplyNum;
    }

    
    @Column(name="netural_reply_num", precision=22, scale=0)
    public Double getNeturalReplyNum() {
        return this.neturalReplyNum;
    }
    
    public void setNeturalReplyNum(Double neturalReplyNum) {
        this.neturalReplyNum = neturalReplyNum;
    }

    
    @Column(name="browse_num", precision=22, scale=0)
    public Double getBrowseNum() {
        return this.browseNum;
    }
    
    public void setBrowseNum(Double browseNum) {
        this.browseNum = browseNum;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="people_date", length=19)
    public Date getPeopleDate() {
        return this.peopleDate;
    }
    
    public void setPeopleDate(Date peopleDate) {
        this.peopleDate = peopleDate;
    }




}


