package club.thewhitewall.nightjar;

import club.thewhitewall.nightjar.infra.properties.NightjarProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NightjarProperties.class)
public class Nightjar {

    public static void main(final String... args) {
        SpringApplication.run(Nightjar.class, args);
    }

}
