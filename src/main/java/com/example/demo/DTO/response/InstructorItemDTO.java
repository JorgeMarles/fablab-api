package com.example.demo.DTO.response;

import com.example.demo.entities.Instructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorItemDTO implements IResponseDTO<Instructor> {

    private Long id;
    private String nombre;
    private String correo;
    @Override
    public void parseFromEntity(Instructor entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }


    
}
