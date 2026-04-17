package com.flowtest.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "auth_configs")
public class AuthConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String configId;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String token;
    private String username;
    private String password;

    @Column(columnDefinition = "TEXT")
    private String customHeaders;
}
