package org.example.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProducerService {

    /**
     * отправка ответов с node в брокер
     */

    void producerAnswer(SendMessage sendMessage);
}
