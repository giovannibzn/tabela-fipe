package br.com.alura.desafio_tabela_fipe.model;

public record Dados (String codigo, String nome){
    @Override
    public String toString() {
        return String.format("Código: %-5s | %-20s", codigo, nome);
    }
}
