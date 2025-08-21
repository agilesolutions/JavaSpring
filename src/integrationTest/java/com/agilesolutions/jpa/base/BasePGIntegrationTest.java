package com.agilesolutions.jpa.base;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BasePGIntegrationTest {
    static int containerPort = 5432;
    static int localPort = 5432;

    @ServiceConnection
    protected static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres")
                    .withTag("latest"))
            .withReuse(true)
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))

            ));

    static {
        dbContainer.setCommand("postgres","-c","max_connections=2000");
        dbContainer.start();
    }

}