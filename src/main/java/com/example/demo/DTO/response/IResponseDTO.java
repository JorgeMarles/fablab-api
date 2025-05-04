package com.example.demo.DTO.response;

public interface IResponseDTO<T> {
    public void parseFromEntity(T entity);
}
