package com.example.demo.DTO.response;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String nombre;
    private List<String> roles = new LinkedList<>();
    private Boolean hasPersonalData = false;
}
