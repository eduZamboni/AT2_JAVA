package Service;

import TP1.Service.ReembolsoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReembolsoServiceTest {

    private final ReembolsoService service = new ReembolsoService();

    @Test
    @DisplayName("calculateReembolso: 200 com 70% de cobertura retorna 140")
    void calculateReembolso_with70PercentCoverage_returns140() {
        // Setup
        double valorConsulta = 200.0;
        double percentualCobertura = 0.70;

        // Execution
        double reembolso = service.calculateReembolso(valorConsulta, percentualCobertura);

        // Assertion
        assertEquals(140.0, reembolso, 1e-9,
                "Reembolso de 200 com 70% de cobertura deve ser 140");
    }

    @Test
    @DisplayName("calculateReembolso: casos limites com 0 e 100% de cobertura")
    void calculateReembolso_edgeCases() {
        assertEquals(0.0, service.calculateReembolso(0.0, 0.70), 1e-9);

        assertEquals(0.0, service.calculateReembolso(200.0, 0.0), 1e-9);

        assertEquals(200.0, service.calculateReembolso(200.0, 1.0), 1e-9);
    }
}