package pe.edu.utp.marketapi.Domain.Pago;

import jakarta.validation.constraints.NotNull;

public record DataRegisterPago(
        @NotNull Long idMetodoPago,
        Long idPedido,
        Double monto
) {
}
