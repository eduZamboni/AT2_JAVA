package TP1.model;

import java.time.LocalDate;

public class Consulta {
    private final Paciente paciente;
    private final double valor;
    private final LocalDate data;

    public Consulta(Paciente paciente, double valor, LocalDate data) {
        this.paciente = paciente;
        this.valor = valor;
        this.data = data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }
}