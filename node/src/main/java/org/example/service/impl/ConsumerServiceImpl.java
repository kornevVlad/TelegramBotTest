package org.example.service.impl;

import lombok.extern.log4j.Log4j;
import org.example.service.ConsumerService;
import org.example.service.MainService;
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

   /* private final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }
    */

    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = TEXT_MASSAGE_UPDATE)
    public void consumerTextMessageUpdates(Update update) {
        log.debug("NODE: text" + update.toString());
        mainService.processTextMassage(update);
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
