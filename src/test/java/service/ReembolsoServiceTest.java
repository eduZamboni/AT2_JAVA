package service;

import TP1.model.Paciente;
import TP1.model.PlanoSaude;
import TP1.service.ReembolsoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReembolsoServiceTest {

    private final ReembolsoService service = new ReembolsoService();

    // Stub para plano com 70% de cobertura
    static class Plano70Stub implements PlanoSaude {
        @Override
        public double getPercentualCobertura() {
            return 0.70;
        }
    }

    // Stub para plano com 0% de cobertura
    static class Plano0Stub implements PlanoSaude {
        @Override
        public double getPercentualCobertura() {
            return 0.0;
        }
    }

    // Stub para plano com 100% de cobertura
    static class Plano100Stub implements PlanoSaude {
        @Override
        public double getPercentualCobertura() {
            return 1.0;
        }
    }


    @Test
    @DisplayName("calculateReembolso: 200 com 70% de cobertura retorna 140")
    void calculateReembolso_with70PercentCoverage_returns140() {
        // Setup
        double valorConsulta = 200.0;
        PlanoSaude plano = new Plano70Stub();
        Paciente dummy = new Paciente();

        // Execution
        double reembolso = service.calculateReembolso(valorConsulta, plano, dummy);

        // Assertion
        assertEquals(140.0, reembolso, 1e-9,
                "Reembolso de 200 com 70% de cobertura deve ser 140");
    }

    @Test
    @DisplayName("calculateReembolso: casos limites com 0 e 100% de cobertura")
    void calculateReembolso_edgeCases() {
        Paciente dummy = new Paciente();

        assertEquals(0.0, service.calculateReembolso(0.0, new Plano70Stub(), dummy), 1e-9);

        assertEquals(0.0, service.calculateReembolso(200.0, new Plano0Stub(), dummy), 1e-9);

        assertEquals(200.0, service.calculateReembolso(200.0, new Plano100Stub(), dummy), 1e-9);
    }
}