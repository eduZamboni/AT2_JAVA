package TP1.service;

import TP1.model.Paciente;
import TP1.model.PlanoSaude;

public class ReembolsoService {
    public double calculateReembolso(double valorConsulta, PlanoSaude plano, Paciente paciente) {
        return valorConsulta * plano.getPercentualCobertura();
    }
}