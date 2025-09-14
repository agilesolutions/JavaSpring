package com.agilesolutions.streams.config;

import com.agilesolutions.dto.AccountDto;
import com.agilesolutions.jpa.model.AccountEntity;
import com.agilesolutions.jpa.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class SteamsConfig {

    @Bean
    public Supplier<AccountDto> accountSupplier() {
        return () -> {
            return AccountDto.builder().accountNumber(UUID.randomUUID().toString()).accountType("EXTERNAL")
                    .branchAddress("123 Main Street, Zurich").build();
        };
    }

    @Bean
    public Function<AccountDto, Message<AccountDto>> accountProcessor() {
        return account -> {
            boolean isExternal = "EXTERNAL".equals(account.accountType());
            String destination = isExternal ? "accountConsumer-in-0" : "fraudConsumer-in-0";

            return MessageBuilder.withPayload(account)
                    .setHeader("spring.cloud.stream.sendto.destination", destination)
                    .build();

        };
    }


    // Consumer: DTO -> JPA Entity
    @Bean
    public Consumer<AccountDto> accountConsumer(AccountRepository repo) {
        return dto -> {
            AccountEntity entity = new AccountEntity();
            entity.setAccountNumber(dto.accountNumber());
            entity.setAccountType(dto.accountType());
            entity.setBranchAddress(dto.branchAddress());
            repo.save(entity);
        };
    }

    @Bean
    public Consumer<AccountDto> fraudConsumer(AccountRepository repo) {
        return dto -> {
            log.info("FRAUD DETECTED: {}", dto);
        };
    }


}
