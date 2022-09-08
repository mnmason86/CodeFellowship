package com.mnmason6.codefellowship.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private LocalDateTime timeStamp;

    @ManyToOne
    private SiteUser siteUser;

    protected Post(){
    }

    public Post(String body, LocalDateTime timeStamp, SiteUser siteUser) {
        this.body = body;
        this.timeStamp = timeStamp;
        this.siteUser = siteUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }
}
