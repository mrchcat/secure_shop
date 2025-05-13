package com.github.mrchcat.intershop;

import com.github.mrchcat.client.PaymentClient;
import com.redis.testcontainers.RedisContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractTestContainerTest {

    public final static PostgreSQLContainer postgres=new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.4"));

    public final static RedisContainer redis=new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379);;

    static{
        postgres.start();
        redis.start();
    }

    @Autowired
    public  WebTestClient webTestClient;

    @MockitoBean
    public PaymentClient paymentClient;

}
