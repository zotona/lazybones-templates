package ${ group }

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
public class Application {
    public static final int DEFAULT_PADDING = 50

    public static void main(String[] args) {
        println 'Starting'.center(DEFAULT_PADDING, '=')
        SpringApplication.run Application, args
        println 'Started'.center(DEFAULT_PADDING, '=')
    }
}