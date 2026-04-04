package com.wedding.wedding_invitation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class WeddingInvitationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeddingInvitationApplication.class, args);
	}

}
