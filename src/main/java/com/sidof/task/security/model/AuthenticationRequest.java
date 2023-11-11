package com.sidof.task.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author sidof
 * @Since 10/4/23
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
