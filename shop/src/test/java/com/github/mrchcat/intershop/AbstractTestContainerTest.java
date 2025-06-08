package com.github.mrchcat.intershop;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractTestContainerTest {
//
//    public final static PostgreSQLContainer postgres =
//            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.4"));
//
//    public final static RedisContainer redis =
//            new RedisContainer(DockerImageName.parse("redis:8.0.0"));
//
//    static {
//        postgres.start();
//        redis.start();
//    }
//
//    @DynamicPropertySource
//    static void redisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", redis::getHost);
//        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
//
//        registry.add("spring.r2dbc.url", AbstractTestContainerTest::getPostgresUrl);
//        registry.add("spring.r2dbc.username", postgres::getUsername);
//        registry.add("spring.r2dbc.password", postgres::getPassword);
//    }
//
//    static String getPostgresUrl() {
//        return String.format("r2dbc:postgresql://%s:%s/%s",
//                postgres.getHost(),
//                postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
//                postgres.getDatabaseName()
//        );
//    }
//
//    @Autowired
//    public WebTestClient webTestClient;
//
//    @MockitoBean
//    public PaymentClient paymentClient;

}
