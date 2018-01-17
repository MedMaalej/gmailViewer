package com.gmailViewer.gmv.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class MailBox extends BaseEntity {
    @JsonProperty("userMail")
    private String userMail;
    @JsonProperty("userPassword")

    private String userPassword;

    public MailBox() {
    }

    public MailBox(String userMail, String userPassword) {
        this.userMail = userMail;
        this.userPassword = userPassword;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
