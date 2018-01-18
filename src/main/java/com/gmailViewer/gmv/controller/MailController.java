package com.gmailViewer.gmv.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmailViewer.gmv.model.Mail;
import com.gmailViewer.gmv.model.MailBox;
import com.gmailViewer.gmv.repository.MailRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Controller
public class MailController {

    @Autowired
    MailRepository mailRepository;


    @RequestMapping(value = "/getMails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public List<Mail> readInbox(String jsonMailBoxes) {

        List<Mail> mails = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        List<MailBox> mailBoxes = null;
        try {
            mailBoxes = objectMapper.readValue(jsonMailBoxes, new TypeReference<List<MailBox>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (MailBox mailBox : mailBoxes) {


            Properties props = new Properties();

            try {

                InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
                props.load(input);
                Session session = Session.getDefaultInstance(props, null);
                Store store = session.getStore("imaps");
                store.connect("smtp.gmail.com", mailBox.getUserMail(), mailBox.getUserPassword());
                Folder inbox = store.getFolder("inbox");
                inbox.open(Folder.READ_ONLY);
                int messageCount = inbox.getMessageCount();
                Message[] messages = inbox.getMessages();
                System.out.println("------------------------------");
                String result = null;
                for (int i = messageCount - 1; i > messageCount - 10; i--) {
                    Mail mail = new Mail();
                    mail.setSubject(messages[i].getSubject());
                    mail.setSenderEmail(messages[i].getFrom()[0].toString());
                    mail.setReceiverEmail(messages[i].getAllRecipients()[0].toString());
                    Object contentObject = messages[i].getContent();
                    BodyPart clearTextPart = null;
                    BodyPart htmlTextPart = null;

                    if (contentObject instanceof Multipart) {
                        Multipart content = (Multipart) contentObject;
                        int count = content.getCount();
                        for (int j = 0; j < count; j++) {
                            BodyPart part = content.getBodyPart(j);
                            if (part.isMimeType("text/plain")) {
                                clearTextPart = part;
                                break;
                            } else if (part.isMimeType("text/html")) {
                                htmlTextPart = part;
                            }
                        }
                        if (clearTextPart != null) {
                            result = (String) clearTextPart.getContent();
                        } else if (htmlTextPart != null) {
                            String html = (String) htmlTextPart.getContent();
                            result = Jsoup.parse(html).text();
                        }
                    } else if (contentObject instanceof String) {
                        result = (String) contentObject;
                    } else {
                        System.out.println("Invalid message format");
                        result = null;
                    }
                    mail.setContent(result);
                    mail.setReceiptDate(messages[i].getSentDate().toString());
                    mail.setId(i);
                    mailRepository.save(mail);
                    mails.add(mail);
                }

                inbox.close(true);
                store.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return mails;
    }
}

