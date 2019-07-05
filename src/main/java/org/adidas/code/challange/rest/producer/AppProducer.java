package org.adidas.code.challange.rest.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "org.adidas.code.challange.rest.producer")
public class AppProducer {

    public static void main(String[] args) {
        SpringApplication.run(AppProducer.class, args);
    }
}