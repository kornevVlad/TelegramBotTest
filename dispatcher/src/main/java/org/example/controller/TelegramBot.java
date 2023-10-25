package org.example.controller;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}") //подключение зависимости смотреть application.properties
    private String botName;

    @Value("${bot.token}") //подключение зависимости смотреть application.properties
    private String botToken;

    private UpdateController updateController;

    public TelegramBot(UpdateController updateController) {
        this.updateController = updateController;
    }

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
    }

    /**
     * подключение ручного логгера, изменяем на бибилиотеку Lombok
     * private static final Logger log = Logger.getLogger(TelegramBot.class);
     */

    /**
     * Возращаем userName для авторизации в Telegram
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возращаем token для авторизации в Telegram
     */
    @Override
    public String getBotToken() {
       return botToken;
    }

    /**
     * Информация о полученном сообщении из Telegram
     * И ответное сообщение на входящее
     */
    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
        /**
        var originalMessage = update.getMessage();
        log.debug(originalMessage.getText());

        var response = new SendMessage();
        response.setChatId(originalMessage.getChatId().toString());
        response.setText("Hello, I'm Telegram Bot");
        sendAnswerMessage(response);
         */
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}
