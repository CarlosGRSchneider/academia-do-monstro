package com.example.treinos.academiadomonstro.commons.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrupoMuscularValidator implements ConstraintValidator<GrupoMuscularCheck, CharSequence> {

    private List<String> valoresDoEnum;

    @Override
    public void initialize(GrupoMuscularCheck params) {
        valoresDoEnum = Stream.of(params.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value != null && valoresDoEnum.contains(value.toString().toUpperCase());
    }
}
