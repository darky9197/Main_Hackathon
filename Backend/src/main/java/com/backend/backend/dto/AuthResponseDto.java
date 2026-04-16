package com.backend.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private UUID userId;
    private String name;
    private String role;
}
