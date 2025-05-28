package com.example.demo.DTO.response;

import com.example.demo.entities.Usuario;

public interface IPersonalizableResponseDTO<T> extends IResponseDTO<T> {
    /**
     * Parses the entity to a personalized response DTO by user.
     *
     * @param entity the entity to parse
     */
    void personalizeFromEntity(T entity, Usuario user);
    
}
