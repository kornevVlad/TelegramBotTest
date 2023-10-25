package org.example.utils;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MassageUtils {

    /**
     * класс который сетит сообщения из чатов
     */
    public SendMessage generateMassageWithText(Update update, String text) {
        var massage = update.getMessage();
        var sendMassage = new SendMessage();
        sendMassage.setChatId(massage.getChatId().toString());
        sendMassage.setText(text);
        return sendMassage;
    }
}