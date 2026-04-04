package com.wedding.wedding_invitation.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration      // Spring에 설정 등록
@EnableJpaAuditing  // 시간 자동 기록
public class JpaAuditConfig {
    
}
