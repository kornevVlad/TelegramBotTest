package org.example.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.example.RabbitQueue.*;

/**
 * Конфигурационный класс RabbitMq
 */
@Configuration
public class RabbitConfiguration {

    /**
     * Преобразование данных в Json и обратно в Java текст
     */
    @Bean
    public MessageConverter jsonMassageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * создание очередей
     */
    @Bean
    public Queue textMessageQueue() {
        return new Queue(TEXT_MASSAGE_UPDATE);
    }

    @Bean
    public Queue docMessageQueue() {
        return new Queue(DOC_MASSAGE_UPDATE);
    }

    @Bean
    public Queue photoMessageQueue() {
        return new Queue(PHOTO_MASSAGE_UPDATE);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(ANSWER_MASSAGE);
    }
}
