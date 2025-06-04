package pe.edu.utp.marketapi.Domain.Pedido;

import jakarta.validation.constraints.NotNull;

public record DataRegisterPedido(
        @NotNull Long idUsuario
) {
}
