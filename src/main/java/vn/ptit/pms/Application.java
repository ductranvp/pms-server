package vn.ptit.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

// Set the default timezone for application to UTC
@SpringBootApplication
@EntityScan(basePackageClasses = {
        Application.class,
        Jsr310JpaConverters.class
})
@EnableScheduling
public class Application {

//    @PostConstruct
//    void init() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
