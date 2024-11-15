package br.com.alura.desafio_tabela_fipe.principal;

import br.com.alura.desafio_tabela_fipe.ConverteDados;
import br.com.alura.desafio_tabela_fipe.model.Dados;
import br.com.alura.desafio_tabela_fipe.model.Modelos;
import br.com.alura.desafio_tabela_fipe.model.Veiculo;
import br.com.alura.desafio_tabela_fipe.service.ConsumoApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        var menu = """
                **** OPÇÕES ****
                1. Carro
                2. Moto
                3. Caminhão
                
                Digite uma das opções para consultar: 
                """;

        System.out.println(menu);
        Scanner leitura = new Scanner(System.in);
        var opcao = leitura.nextLine().toLowerCase();
        String endereco = null;

        if (opcao.contains("carr") || opcao.equals("1")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.contains("mot")| opcao.equals("2")) {
            endereco = URL_BASE + "motos/marcas";
        } else if (opcao.contains("caminh")| opcao.equals("3")){
            endereco = URL_BASE + "caminhoes/marcas";
        } else{
            System.out.println("Opção inválida! Tente novamente.\n");
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modelosLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modelosLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores ");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++){
            var endercoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(endercoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

            System.out.println("\nTodos os veiculos filtrados por anos: ");
            veiculos.forEach(System.out::println);
    }
}
