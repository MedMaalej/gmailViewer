package com.gmailViewer.gmv.controller;

import com.gmailViewer.gmv.model.MailBox;
import com.gmailViewer.gmv.repository.MailBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Controller
public class MailBoxController {

    @Autowired
    MailBoxRepository mailBoxRepository;

    @RequestMapping(value = "/addMailbox", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MailBox addMailbox(String userName, String userPassword) {
        MailBox mailBox = new MailBox(userName, userPassword);
        mailBoxRepository.save(mailBox);
        return mailBox;
    }
}
