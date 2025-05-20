package TP1.service;

import TP1.model.Consulta;

public class AuditoriaSpy implements Auditoria{
    private boolean consultaRegistrada = false;

    @Override
    public void registrarConsulta(Consulta consulta) {
        consultaRegistrada = true;
    }

    public boolean foiRegistrado() {
        return consultaRegistrada;
    }
}