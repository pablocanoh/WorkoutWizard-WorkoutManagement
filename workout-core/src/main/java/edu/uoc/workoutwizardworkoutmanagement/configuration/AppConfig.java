package edu.uoc.workoutwizardworkoutmanagement.configuration;

import com.example.routineclient.RoutineClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uoc.edu.commons.JwtTokenUtil;

@Configuration
public class AppConfig {

    @Bean
    public RoutineClient routineClient(@Value("${routine-service.url}") String routineServiceUrl) {
        return new RoutineClient(routineServiceUrl);
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
}
