package org.example.service.impl;

import org.example.dao.RawDataDao;
import org.example.entity.RawData;
import org.example.service.MainService;
import org.example.service.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MainServiceImpl implements MainService {

    private final RawDataDao rawDataDao;

    private final ProducerService producerService;

    public MainServiceImpl(RawDataDao rawDataDao, ProducerService producerService) {
        this.rawDataDao = rawDataDao;
        this.producerService = producerService;
    }

    @Override
    public void processTextMassage(Update update) {
        saveRawData(update);

        var massage = update.getMessage();
        var sendMassage = new SendMessage();
        sendMassage.setChatId(massage.getChatId().toString());
        sendMassage.setText("Hello from NODE");
        producerService.producerAnswer(sendMassage);
    }

    /**
     * сохранение update через билдер
     */
    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDao.save(rawData);
    }
}
