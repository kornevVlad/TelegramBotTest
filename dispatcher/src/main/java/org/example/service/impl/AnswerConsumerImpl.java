package org.example.service.impl;

import lombok.extern.log4j.Log4j;
import org.example.controller.UpdateController;
import org.example.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.example.RabbitQueue.ANSWER_MASSAGE;

@Service
@Log4j
public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_MASSAGE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
