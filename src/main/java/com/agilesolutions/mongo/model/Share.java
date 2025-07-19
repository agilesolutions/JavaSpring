package com.agilesolutions.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("Share")
@Builder
@Data
@AllArgsConstructor
public class Share {

    @Id
    private Long id;
    private String company;
    private int quantity;
    private LocalDate updated;

}