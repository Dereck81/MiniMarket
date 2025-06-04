package pe.edu.utp.marketapi.Domain.Pedido;

import jakarta.validation.constraints.NotNull;

public record DataUpdatePedido(
        @NotNull(message = "Id Pedido no puede ser nulo") Long idPedido,
        Long idUsuario,
        Double total
) {
}
