package com.example.demo.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.demo.entities.TipoDato;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Observable {
    TipoDato tipo() default TipoDato.STRING;
}
