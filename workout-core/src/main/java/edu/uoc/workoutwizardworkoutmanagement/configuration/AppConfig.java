package edu.uoc.workoutwizardworkoutmanagement.configuration;

import com.example.routineclient.RoutineClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uoc.edu.commons.JwtTokenUtil;

@Configuration
public class AppConfig {

    @Bean
    public RoutineClient routineClient() {
        return new RoutineClient("http://localhost:8081");
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
}
