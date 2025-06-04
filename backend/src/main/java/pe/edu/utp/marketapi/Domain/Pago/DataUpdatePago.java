package pe.edu.utp.marketapi.Domain.Pago;

import jakarta.validation.constraints.NotNull;

public record DataUpdatePago(
        @NotNull Long idPago,
        Long idMetodoPago,
        Long idPedido,
        Double monto
) {
}
