package ru.otus.architect.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/success")
    @Timed(value = "social.network.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public String test() {
        log.info("invoke test");
        return "test success response";
    }
}
