package org.example.entity;

import lombok.*;
import org.example.entity.enams.UserState;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //первичный ключ
    private Long telegramUserId; //ключ пользователя из телеграм

    @CreationTimestamp
    private LocalDateTime firstLoginDate; //дата сохранения в БД

    /**
     * входящие данные из телеграм чата
     */
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private UserState state; //состояние пользователя
}
