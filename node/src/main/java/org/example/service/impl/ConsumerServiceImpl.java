package org.example.service.impl;

import lombok.extern.log4j.Log4j;
import org.example.service.ConsumerService;
import org.example.service.ProducerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.RabbitQueue.*;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {

    /**
     * RabbitListener слушает очередь порта
     */

    private final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues = TEXT_MASSAGE_UPDATE)
    public void consumerTextMessageUpdates(Update update) {
        log.debug("NODE: text" + update.toString());

        var massage = update.getMessage();
        var sendMassage = new SendMessage();
        sendMassage.setChatId(massage.getChatId().toString());
        sendMassage.setText("Hello from NODE");
        producerService.producerAnswer(sendMassage);
    }

    @Override
    @RabbitListener(queues = DOC_MASSAGE_UPDATE)
    public void consumerDocMessageUpdates(Update update) {
        log.debug("NODE: doc" + update.toString());
    }

    @Override
    @RabbitListener(queues = PHOTO_MASSAGE_UPDATE)
    public void consumerPhotoMessageUpdates(Update update) {
        log.debug("NODE: photo" + update.toString());
    }
}
