package TP1.service;

import TP1.model.Consulta;

public interface AutorizadorReembolso {
    boolean isAutorizado(Consulta consulta);
}
