package com.exam.stud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    // You can leave this empty for now.
    // Later, this is where you will add the "AuditorAware" bean 
    // to tell Spring WHO is logged in (for @CreatedBy).
}