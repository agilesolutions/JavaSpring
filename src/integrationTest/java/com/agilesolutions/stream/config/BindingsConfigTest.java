package com.agilesolutions.stream.config;

import com.agilesolutions.dto.AccountDto;
import com.agilesolutions.jpa.model.AccountEntity;
import com.agilesolutions.jpa.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Function with multiple consumers based on header routing
 */
class BindingsConfigTest {


    @Test
    @ExtendWith(OutputCaptureExtension.class)
    public void whenNonSavingAccount_StoreAccountInDatabase(CapturedOutput capturedOutput) {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(BindingsConfiguration.class))
                .web(WebApplicationType.NONE)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(false)
                .run("--spring.cloud.function.definition=accountProcessor;accountConsumer;fraudConsumer")) {

            InputDestination input = context.getBean(InputDestination.class);
            OutputDestination output = context.getBean(OutputDestination.class);

            Message<AccountDto> inputMessage = MessageBuilder.withPayload(
                    AccountDto.builder().accountNumber("12345").accountType("Savings").branchAddress("123 Main St").build()).build();

            input.send(inputMessage);

            assertThat(capturedOutput.toString(), containsString("SAVING ACCOUNT DETAILS"));
        }
    }

    @Test
    @ExtendWith(OutputCaptureExtension.class)
    public void whenNonCompliantAccount_FlagFraudDetection(CapturedOutput capturedOutput) {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(BindingsConfiguration.class))
                .web(WebApplicationType.NONE)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(false)
                .run("--spring.cloud.function.definition=accountProcessor;accountConsumer;fraudConsumer")) {

            InputDestination input = context.getBean(InputDestination.class);
            OutputDestination output = context.getBean(OutputDestination.class);

            Message<AccountDto> inputMessage = MessageBuilder.withPayload(
                    AccountDto.builder().accountNumber("12345").accountType("External").branchAddress("123 Main St").build()).build();

            input.send(inputMessage);

            assertThat(capturedOutput.toString(), containsString("FRAUD DETECTED"));
        }
    }




    @EnableAutoConfiguration(
            exclude = {
                    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                    org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                    org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
                    org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class,
                    org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
                    org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class
            }
    )
    public static class BindingsConfiguration {

        @Bean
        public AccountRepository accountRepository() {return Mockito.mock(AccountRepository.class);}

        @Bean
        public Function<AccountDto, Message<AccountDto>> accountProcessor() {
            return account -> {
                boolean isInternal = "Savings".equals(account.accountType());
                String destination = isInternal ? "accountConsumer-in-0" : "fraudConsumer-in-0";

                return org.springframework.integration.support.MessageBuilder.withPayload(account)
                        .setHeader("spring.cloud.stream.sendto.destination", destination)
                        .build();

            };
        }


        // Consumer: DTO -> JPA Entity
        @Bean
        public Consumer<AccountDto> accountConsumer(AccountRepository accountRepository) {
            return dto -> {
                AccountEntity entity = new AccountEntity();
                entity.setAccountNumber(dto.accountNumber());
                entity.setAccountType(dto.accountType());
                entity.setBranchAddress(dto.branchAddress());
                accountRepository.save(entity);
                System.out.println("SAVING ACCOUNT DETAILS: %s".formatted(dto));
            };
        }

        @Bean
        public Consumer<AccountDto> fraudConsumer() {
            return dto -> {
                System.out.println("FRAUD DETECTED: %s".formatted(dto));
            };
        }


    }

}