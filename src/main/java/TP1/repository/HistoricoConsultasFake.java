package TP1.repository;

import TP1.model.Paciente;
import TP1.model.Consulta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoConsultasFake implements HistoricoConsultas  {
    private final List<Consulta> consultas = new ArrayList<>();

    @Override
    public void registrarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    @Override
    public List<Consulta> obterConsultaDoPaciente(Paciente paciente) {
        return consultas.stream()
                .filter(c -> c.getPaciente().equals(paciente))
                .collect(Collectors.toList());
    }
}