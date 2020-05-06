package org.jeecg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"org.jeecg","org.hero"})
@MapperScan({"org.hero.*.mapper"})
public class JeecgApplication {

    public static void main(String[] args) {
    	System.setProperty("spring.devtools.restart.enabled", "true");
    	SpringApplication.run(JeecgApplication.class, args);
    }
}