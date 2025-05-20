package TP1.repository;

import TP1.model.Paciente;
import TP1.model.Consulta;

import java.util.List;

public interface HistoricoConsultas {
    void registrarConsulta(Consulta consulta);
    List<Consulta> obterConsultaDoPaciente(Paciente paciente);
}
