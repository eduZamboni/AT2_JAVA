package TP1.service;

import TP1.model.Paciente;

public class ReembolsoService {
    public double calculateReembolso(double valorConsulta, double cobertura, Paciente paciente) {
        return valorConsulta * cobertura;
    }
}