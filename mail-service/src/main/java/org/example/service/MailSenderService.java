package org.example.service;

import org.example.dto.MailParams;

public interface MailSenderService {

    void send(MailParams mailParams);
}
