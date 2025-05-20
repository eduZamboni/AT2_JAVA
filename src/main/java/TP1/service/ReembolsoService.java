package TP1.service;

import TP1.model.Consulta;
import TP1.model.Paciente;
import TP1.model.PlanoSaude;

public class ReembolsoService {

    private final AutorizadorReembolso autorizador;

    public ReembolsoService(AutorizadorReembolso autorizador) {
        this.autorizador = autorizador;
    }

    public double calculateReembolso(Consulta consulta, PlanoSaude plano, Paciente paciente) {
        if (!autorizador.isAutorizado(consulta)) {
            throw new IllegalStateException("Consulta não autorizada para reembolso.");
        }
        return consulta.getValor() * plano.getPercentualCobertura();
    }
}