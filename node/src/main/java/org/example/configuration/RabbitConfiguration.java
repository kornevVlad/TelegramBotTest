package org.example.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
