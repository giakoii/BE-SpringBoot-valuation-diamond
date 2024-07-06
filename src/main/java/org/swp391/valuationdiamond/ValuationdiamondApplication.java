package org.swp391.valuationdiamond;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.swp391.valuationdiamond.service.EvaluationRequestServiceImp;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ValuationdiamondApplication {

    public static void main(String[] args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        SpringApplication.run(ValuationdiamondApplication.class, args);
        for (int i = 0; i <= 5; i++) {
            System.out.println("Success!");
        }
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(EvaluationRequestServiceImp evaluationRequestServiceImp) {
//        return args -> {
//            System.out.println(evaluationRequestServiceImp.getEvaluationRequest("ER0507202444").getRequestDate());
//        };
//    }
}
