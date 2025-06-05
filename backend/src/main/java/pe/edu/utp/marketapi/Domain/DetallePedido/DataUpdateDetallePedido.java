package pe.edu.utp.marketapi.Domain.DetallePedido;

public record DataUpdateDetallePedido(
        Long idDetalle,
        Long idPedido,
        Long idInventario,
        Integer cantidad
) {
}
