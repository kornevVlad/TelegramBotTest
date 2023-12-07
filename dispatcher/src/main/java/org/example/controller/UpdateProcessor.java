package org.example.controller;

import lombok.extern.log4j.Log4j;
import org.example.service.UpdateProducer;
import org.example.utils.MassageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.RabbitQueue.PHOTO_MASSAGE_UPDATE;
import static org.example.RabbitQueue.TEXT_MASSAGE_UPDATE;
import static org.example.RabbitQueue.DOC_MASSAGE_UPDATE;

@Component
@Log4j
public class UpdateProcessor {

    private TelegramBot telegramBot;
    private final MassageUtils massageUtils;

    private final UpdateProducer updateProducer;

    public UpdateProcessor(MassageUtils massageUtils, UpdateProducer updateProducer) {
        this.massageUtils = massageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Massage is null");
            return;
        }

        if (update.hasMessage()) {
            distributeMassageByType(update);
        } else {
            log.error("Unsupported type");
        }
    }

    /**
     * обработка входящих сообщений с бота
     */
    public void distributeMassageByType(Update update) {
        var massage = update.getMessage();

        if (massage.hasText()) {
            processTextMassage(update);
        } else if (massage.hasDocument()) {
            processDocumentMassage(update);
        } else if (massage.hasPhoto()) {
            processPhotoMassage(update);
        } else {
            setUnsupportedMassageTypeView(update);
        }
    }

    /**
     * ответ при неподдерживаемом входящем сообщении
     */
    private void setUnsupportedMassageTypeView(Update update) {
        var sendMassage = massageUtils.generateMassageWithText(update,
                "неподдерживаемый тип входящего сообщения");
        setView(sendMassage); //ответ в телеграмм бот
    }

    /**
     * генерация ответа в бот
     */
    public void setView(SendMessage sendMassage) {
        telegramBot.sendAnswerMessage(sendMassage);
    }

    private void processPhotoMassage(Update update) {
        updateProducer.produce(PHOTO_MASSAGE_UPDATE, update);
        setFileIsReceivedView(update);
    }

    private void processDocumentMassage(Update update) {
        updateProducer.produce(DOC_MASSAGE_UPDATE, update);
        setFileIsReceivedView(update);
    }

    private void processTextMassage(Update update) {
        updateProducer.produce(TEXT_MASSAGE_UPDATE, update);
       // setFileIsReceivedView(update);
    }

    /**
     * контент получен и ведется его обработка
     */
    private void setFileIsReceivedView(Update update) {
        var sendMassage = massageUtils.generateMassageWithText(update,
                "Контент получен, ведется обработка. Обработка....");
        setView(sendMassage); //ответ в телеграмм бот
    }
}
