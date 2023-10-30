package org.example.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainService {
    void processTextMassage(Update update);

    void processDocMassage(Update update);

    void processPhotoMassage(Update update);
}
