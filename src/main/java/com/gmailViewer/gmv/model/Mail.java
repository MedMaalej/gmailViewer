package com.gmailViewer.gmv.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Mail extends  BaseEntity {
   private String subject;
   private String senderEmail;
   private String receiverEmail;
   @Lob
   private String content;
   private String receiptDate;


    public Mail() {
    }

    public Mail(String subject, String senderEmail, String receiverEmail, String content, String receiptDate) {
        this.subject = subject;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.receiptDate = receiptDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }
}
