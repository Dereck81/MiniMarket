package pe.edu.utp.marketapi.Domain.MetodoPago;

public record DataListMetodoPago(
        Long idMetodoPago,
        String nombreMetodo,
        Boolean estado
) {
    public DataListMetodoPago(MetodoPago metodoPago){
        this(
                metodoPago.getIdMetodoPago(),
                metodoPago.getNombreMetodo(),
                metodoPago.getEstado()
        );
    }
}
