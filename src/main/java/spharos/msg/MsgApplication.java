package spharos.msg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(
    servers = {
//        @Server(url = "https://sssg.shop", description = "Default Server URL"),
        @Server(url = "http://localhost:8282", description = "Local Test Server URL")
    }
)
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
    }

}
