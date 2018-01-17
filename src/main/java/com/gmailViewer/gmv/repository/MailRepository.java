package com.gmailViewer.gmv.repository;

import com.gmailViewer.gmv.model.Mail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends CrudRepository<Mail,Long> {

}
