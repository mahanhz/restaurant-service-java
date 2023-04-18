package com.example.restaurant.orderservice.steps;

import com.example.restaurant.orderservice.config.WorldConfig;
import com.example.restaurant.orderservice.model.World;
import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import jakarta.annotation.PostConstruct;


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(value = {WorldConfig.class})
@ActiveProfiles(profiles = {"acceptancetest"})
class SpringContextConfig extends DatabaseTest implements En {

    private static final String HOST_URL_FORMAT = "http://localhost:%s";

    @LocalServerPort
    private int serverPort;

    @Autowired
    private World world;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void postConstruct() {
        world.baseUrl = String.format(HOST_URL_FORMAT, serverPort);
    }
}
