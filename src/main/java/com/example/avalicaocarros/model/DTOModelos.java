package com.example.avalicaocarros.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DTOModelos(List<DTODados> modelos) {   
}