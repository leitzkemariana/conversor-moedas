package com.mari.conversormoedas;

import lombok.Getter;
import lombok.Setter;

public class ConversorModel {
    @Getter @Setter
    private String valorInicial;

    @Getter @Setter
    private String moedaInicial;

    @Getter @Setter
    private String moedaFinal;
}
