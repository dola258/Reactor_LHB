package com.cos.lhbreactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // cron작동 (정기적 실행)
@SpringBootApplication
public class LhbReactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LhbReactorApplication.class, args);
	}

}
