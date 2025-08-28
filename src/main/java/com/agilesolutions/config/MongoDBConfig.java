package com.agilesolutions.config;

import com.agilesolutions.actuator.CustomMongoHealthIndicator;
import com.agilesolutions.mongo.repository.MongoDBShareRepository;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.observability.ContextProviderFactory;
import org.springframework.data.mongodb.observability.MongoObservationCommandListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses={MongoDBShareRepository.class})
public class MongoDBConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory(MongoServer mongoServer) {
        String connectionString = mongoServer.getConnectionString();
        return new SimpleMongoClientDatabaseFactory(connectionString + "/test");
    }

    @Bean(destroyMethod = "shutdown")
    public MongoServer mongoServer() {
        MongoServer mongoServer = new MongoServer(new MemoryBackend());
        mongoServer.bind();
        return mongoServer;
    }

    // Registering MongoDB Micrometer customizer setup
    @Bean
    MongoClientSettingsBuilderCustomizer mongoMetricsSynchronousContextProvider(ObservationRegistry registry) {
        return (clientSettingsBuilder) -> {
            clientSettingsBuilder.contextProvider(ContextProviderFactory.create(registry))
                    .addCommandListener(new MongoObservationCommandListener(registry));
        };
    }

    @Bean
    public CustomMongoHealthIndicator mongoHealthIndicator(MongoTemplate mongoTemplate) {
        return new CustomMongoHealthIndicator(mongoTemplate);
    }
}
