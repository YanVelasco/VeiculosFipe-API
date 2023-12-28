package com.example.avalicaocarros.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.avalicaocarros.model.DTODados;
import com.example.avalicaocarros.model.DTOModelos;
import com.example.avalicaocarros.model.Veiculo;
import com.example.avalicaocarros.service.ConsumoApi;
import com.example.avalicaocarros.service.ConverterDados;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados converterDados = new ConverterDados();

    public void exibeMenu() {
        var menu = """
                 ***Opções***
                    Carro
                    Moto
                    Caminhão

                 Digite uma das opções para consultar
                """;
        System.out.println(menu);
        var opcao = sc.nextLine();
        String endereco;

        if (opcao.toUpperCase().contains("CARR")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toUpperCase().contains("MOT")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumoApi.obterDados(endereco);
        System.out.println(json);
        var marcas = converterDados.obterLista(json, DTODados.class);
        marcas.stream().sorted(Comparator.comparing(DTODados::codigo)).forEach(System.out::println);
        System.out.println("\nInforme o código da marca para consulta: ");
        var codigoMarca = sc.nextLine();
        
        endereco += "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(endereco);
        var modeloLista = converterDados.obterDados(json, DTOModelos.class);
        System.err.println("Modelos:");
        modeloLista.modelos().stream().sorted(Comparator.comparing(DTODados::nome)).forEach(System.out::println);
        
        System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = sc.nextLine();
        
        List<DTODados> modelosFiltrados = modeloLista
        .modelos()
        .stream()
        .filter(m -> m
        .nome()
        .toUpperCase()
        .contains(nomeVeiculo.toUpperCase()))
        .collect(Collectors.toList());
        System.err.println("\n Modelo filtrados:");
        modelosFiltrados.forEach(System.out::println);
        
        System.out.println("Digite por favor o código do modelo buscado: ");
        var codigoModeloBuscado = sc.nextLine();

        endereco += "/" + codigoModeloBuscado + "/anos";
        json = consumoApi.obterDados(endereco);
        List<DTODados> anos = converterDados.obterLista(json, DTODados.class);
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = converterDados.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("Todos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}