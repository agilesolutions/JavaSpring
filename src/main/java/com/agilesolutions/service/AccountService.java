package com.agilesolutions.service;

import com.agilesolutions.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

    private final StreamBridge stream;

    private static final String PROCESSOR_BINDING_NAME = "accountProcessor-in-0";

    public void publishAccount(AccountDto account) {


        Message<AccountDto> message = MessageBuilder.withPayload(account).build();

        stream.send(PROCESSOR_BINDING_NAME, message);

        log.info("Published Account: {}", account);
    }


}
