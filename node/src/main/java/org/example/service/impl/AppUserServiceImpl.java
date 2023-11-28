package org.example.service.impl;


import lombok.extern.log4j.Log4j;
import org.example.CryptoTool;
import org.example.dao.AppUserDao;
import org.example.dto.MailParams;
import org.example.entity.AppUser;

import org.example.service.AppUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.example.entity.enams.UserState.BASIC_STATE;
import static org.example.entity.enams.UserState.WAIT_FOR_EMAIL_STATE;

@Service
@Log4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDao appUserDao;

    private final CryptoTool cryptoTool;

    @Value("${service.mail.url}")
    private String mailServiceUrl;

    public AppUserServiceImpl(AppUserDao appUserDao, CryptoTool cryptoTool) {
        this.appUserDao = appUserDao;
        this.cryptoTool = cryptoTool;
    }


    /**
     * проверка пользователя на регистрацию(true/false)
     */
    @Override
    public String registerUser(AppUser appUser) {
        if (appUser.getIsActive()) {
            return "Вы уже зарегистрированы";
        } else if (appUser.getEmail() != null) {
            return "Вам на почту уже было отправлено письмо, перейдите по ссылке для подтверждения регистрации";
        }
        appUser.setState(WAIT_FOR_EMAIL_STATE);
        appUserDao.save(appUser);
        return "Введите ваш email";
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            return "Введите корректный email, для отмены нажмите /cancel";
        }
        var optional = appUserDao.findByEmail(email);
        if (optional.isEmpty()) {
            appUser.setEmail(email);
            appUser.setState(BASIC_STATE);
            appUser = appUserDao.save(appUser);

            var cryptoUserId = cryptoTool.hashOf(appUser.getId());
            var response = sendRequestToMailService(cryptoUserId, email);
            if (response.getStatusCode() != HttpStatus.OK) {
                var msg = String.format("Отправка письма на %s почту не удалась", email);
                log.error(msg);
                appUser.setEmail(null);
                appUserDao.save(appUser);
                return msg;
            }
            return "Вам на почту отправлено письмо, перейдите по ссылке для подтверждения регистрации";
        } else {
            return "Введенная почта уже используется. Введите другую почту или нажмите /cancel";
        }
    }

    private ResponseEntity<String> sendRequestToMailService(String cryptoUserId, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        var request = new HttpEntity<>(mailParams, headers);
        return restTemplate.exchange(mailServiceUrl,
                HttpMethod.POST,
                request,
                String.class);
    }
}
