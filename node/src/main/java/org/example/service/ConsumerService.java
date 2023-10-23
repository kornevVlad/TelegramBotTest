package org.example.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {

    /**
     * считывание сообщений из брокера
     */

    void consumerTextMessageUpdates(Update update);

    void consumerDocMessageUpdates(Update update);

    void consumerPhotoMessageUpdates(Update update);
}
