package com.esgi.tp_spring.dto.requests;

public interface IRequestDTO<T> {
    T ToEntity();
}
