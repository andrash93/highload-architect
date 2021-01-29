package ru.otus.architect.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.architect.generator.service.GeneratorService;

@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String args[]) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        GeneratorService generatorService = context.getBean(GeneratorService.class);
        int N = 100000;
        int butchSize = 50;
        for (int i = 0; i < N; i++) {
       //     log.info("{} butch start", i);
            generatorService.generateStubAccounts(butchSize);
       //     log.info("{} butch complete", i);

        }
        log.info("all record created");
    }
}
