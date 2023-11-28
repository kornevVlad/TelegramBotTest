package org.example.service.impl;

import org.example.CryptoTool;
import org.example.dao.AppUserDao;
import org.example.service.UserActivationService;
import org.springframework.stereotype.Service;

/**
 *проверка пользователя по ID для активации учетной записи
 */
@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final AppUserDao appUserDao;

    private final CryptoTool cryptoTool;

    public UserActivationServiceImpl(AppUserDao appUserDao, CryptoTool cryptoTool) {
        this.appUserDao = appUserDao;
        this.cryptoTool = cryptoTool;
    }

    /**
     * проверка пользователя по id
     */
    @Override
    public boolean activation(String cryptoUserId) {
        var userId = cryptoTool.idOf(cryptoUserId);
        var optional = appUserDao.findById(userId);
        if (optional.isPresent()) {
            var user = optional.get();
            user.setIsActive(true);
            appUserDao.save(user);
            return true;
        }
        return false;
    }
}
