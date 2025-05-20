package service;

import TP1.model.Consulta;
import TP1.model.Paciente;
import TP1.model.PlanoSaude;
import TP1.service.AuditoriaSpy;
import TP1.service.AutorizadorReembolso;
import TP1.service.ReembolsoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReembolsoServiceTest {

    private final ReembolsoService service = new ReembolsoService(new AutorizadorAlwaysAllowStub());

    private Paciente dummy;
    private Consulta consultaComPlanoZero;

    @BeforeEach
    void setUp() {
        dummy = new Paciente();
        consultaComPlanoZero = new Consulta(dummy, 200.0, LocalDate.now());
    }

    private Consulta criarConsulta(double valor){
        return new Consulta(dummy, valor, LocalDate.now());
    }

    static class AutorizadorAlwaysAllowStub implements AutorizadorReembolso {
        @Override
        public boolean isAutorizado(Consulta consulta) {
            return true;
        }
    }

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
        PlanoSaude plano = new Plano70Stub();

        // Execution
        double reembolso = service.calculateReembolso(criarConsulta(200.0), plano, dummy);

        // Assertion
        assertEquals(140.0, reembolso, 1e-9,
                "Reembolso de 200 com 70% de cobertura deve ser 140");
    }

    @Test
    @DisplayName("calculateReembolso: casos limites com 0 e 100% de cobertura")
    void calculateReembolso_edgeCases() {

        assertEquals(140.0, service.calculateReembolso(criarConsulta(200.0), new Plano70Stub(), dummy), 1e-9);

        assertEquals(0.0, service.calculateReembolso(criarConsulta(200.0), new Plano0Stub(), dummy), 1e-9);

        assertEquals(200.0, service.calculateReembolso(criarConsulta(200.0), new Plano100Stub(), dummy), 1e-9);
    }

    @Test
    @DisplayName("Deve chamar o serviço de auditoria ao registrar consulta")
    void deveChamarAuditoriaAoRegistrarConsulta() {
        AuditoriaSpy auditoria = new AuditoriaSpy();

        auditoria.registrarConsulta(consultaComPlanoZero);

        assertTrue(auditoria.foiRegistrado(), "Auditoria deveria ter sido chamada");
    }

    @Test
    @DisplayName("Deve lançar exceção se consulta não for autorizada")
    void naoDevePermitirReembolsoSemAutorizacao() {
        // Arrange
        AutorizadorReembolso autorizadorMock = Mockito.mock(AutorizadorReembolso.class);
        ReembolsoService service = new ReembolsoService(autorizadorMock);

        Consulta consulta = criarConsulta(100.0);
        PlanoSaude plano = () -> 0.8; // lambda como stub

        // Simula não autorizado
        Mockito.when(autorizadorMock.isAutorizado(consulta)).thenReturn(false);

        // Act & Assert
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            service.calculateReembolso(consulta, plano, consulta.getPaciente());
        });

        assertEquals("Consulta não autorizada para reembolso.", ex.getMessage());

        }
    }