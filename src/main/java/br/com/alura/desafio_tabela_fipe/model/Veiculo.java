package br.com.alura.desafio_tabela_fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(
        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") Integer ano,
        @JsonAlias("Combustivel") String combustivel
) {
    @Override
    public String toString() {
        String primeiroNomeModelo = modelo.split(" ")[0];

        return "Veículo " + marca + ", " + primeiroNomeModelo + ", ano " + ano +
                ", a " + combustivel +
                ", no valor de R$ " + valor;

    }
}
