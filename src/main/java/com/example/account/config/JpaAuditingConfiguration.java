package com.example.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 자동으로 bean 등록
@EnableJpaAuditing // jpaAuditing 켜진 상태로 spring 시작
public class JpaAuditingConfiguration {
}
