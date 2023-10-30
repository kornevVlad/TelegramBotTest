package org.example.service.impl;

import org.example.dao.AppUserDao;
import org.example.dao.RawDataDao;
import org.example.entity.AppUser;
import org.example.entity.RawData;
import org.example.service.MainService;
import org.example.service.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.example.entity.enams.UserState.BASIC_STATE;

@Service
public class MainServiceImpl implements MainService {

    private final RawDataDao rawDataDao;

    private final ProducerService producerService;

    private final AppUserDao appUserDao;

    public MainServiceImpl(RawDataDao rawDataDao, ProducerService producerService, AppUserDao appUserDao) {
        this.rawDataDao = rawDataDao;
        this.producerService = producerService;
        this.appUserDao = appUserDao;
    }

    @Override
    public void processTextMassage(Update update) {
        saveRawData(update);

        var massage = update.getMessage();
        var sendMassage = new SendMessage();

        var textMassage = update.getMessage();
        var telegramUser = textMassage.getFrom();
        var appUser = findOrSaveAppUser(telegramUser);

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

    /**
     * проверка пользователя на ID
     */
    private AppUser findOrSaveAppUser(User telegramUser) {
        AppUser persistansAppUser = appUserDao.findAppUserByTelegramUserId(telegramUser.getId());
        if(persistansAppUser == null) {
            //пользователь не найден, предстоящее сохранение
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    //TODO изменить значение
                    .isActive(true)
                    .state(BASIC_STATE)
                    .build();
            return appUserDao.save(transientAppUser);
        }
        return persistansAppUser;
    }
}
