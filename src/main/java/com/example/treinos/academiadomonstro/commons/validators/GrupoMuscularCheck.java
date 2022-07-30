package com.example.treinos.academiadomonstro.commons.validators;

import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {GrupoMuscularValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GrupoMuscularCheck {
    String message() default "Meu monstro, o grupo muscular informado não existe.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

}


//    String message() default "Meu monstro, o grupo muscular informado não existe. Grupos possiveis = {anyOf}";
//
//        Class<?>[] groups() default {};
//
//        Class<? extends Payload>[] payload() default {};
//
//        GrupoMuscular[] anyOf();
//