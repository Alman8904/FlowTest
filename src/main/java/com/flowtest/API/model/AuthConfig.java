package com.flowtest.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthConfig {
    private AuthType authType;
    private String token;
    private String username;
    private String password;
    private Map<String, String> customHeaders;
}
