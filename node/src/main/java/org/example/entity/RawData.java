package org.example.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.*;

/**
 * входящие update
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "raw_date")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class RawData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //первичный ключ

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Update event; //объект приходящий их телеграмм
}