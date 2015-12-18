package com.yuzhi.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tiansj on 15/3/27.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class BmsApplication {
    public static ConfigurableApplicationContext run(Object source, String[] args) {
        return run(new Object[] { source }, args);
    }

    public static ConfigurableApplicationContext run(Object[] sources, String[] args) {
        List<Object> sourcesExt = new ArrayList<>(Arrays.asList(sources));
        SpringApplication app = new SpringApplication(sourcesExt.toArray());
        return app.run(args);
    }

    public static void main(String[] args) throws InterruptedException {
        run(BmsApplication.class, args);
    }
}
