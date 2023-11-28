package org.example.controller;

import lombok.extern.log4j.Log4j;
import org.example.service.UserActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * обработка запроса из ссылки письма подтверждения пользователя
 */
@RequestMapping("/user")
@RestController
@Log4j
public class ActivationController {

    private final UserActivationService userActivationService;


    public ActivationController(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activation")
    public ResponseEntity<?> activation(@RequestParam("id") String id) {
        var res = userActivationService.activation(id);
        log.info(res);
        if (res) {
            return ResponseEntity.ok().body("Активация успешно завершена");
        }
        log.info(ResponseEntity.internalServerError().build());
        return ResponseEntity.internalServerError().build();
    }
}
