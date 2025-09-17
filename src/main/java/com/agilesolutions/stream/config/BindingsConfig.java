package com.agilesolutions.stream.config;

import com.agilesolutions.dto.AccountDto;
import com.agilesolutions.jpa.model.AccountEntity;
import com.agilesolutions.jpa.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Slf4j
public class BindingsConfig {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public Function<AccountDto, Message<AccountDto>> accountProcessor() {
        return account -> {
            boolean isInternal = "Savings".equals(account.accountType());
            String destination = isInternal ? "accountConsumer-in-0" : "fraudConsumer-in-0";

            return MessageBuilder.withPayload(account)
                    .setHeader("spring.cloud.stream.sendto.destination", destination)
                    .build();

        };
    }


    // Consumer: DTO -> JPA Entity
    @Bean
    public Consumer<AccountDto> accountConsumer() {
        return dto -> {
            AccountEntity entity = new AccountEntity();
            entity.setAccountNumber(dto.accountNumber());
            entity.setAccountType(dto.accountType());
            entity.setBranchAddress(dto.branchAddress());
            accountRepository.save(entity);
        };
    }

    @Bean
    public Consumer<AccountDto> fraudConsumer() {
        return dto -> {
            log.info("FRAUD DETECTED: {}", dto);
        };
    }


}
