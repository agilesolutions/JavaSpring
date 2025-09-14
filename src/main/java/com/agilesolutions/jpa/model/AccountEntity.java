package com.agilesolutions.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    private Long id;  // surrogate PK in H2

    private String accountNumber;
    private String accountType;
    private String branchAddress;
}