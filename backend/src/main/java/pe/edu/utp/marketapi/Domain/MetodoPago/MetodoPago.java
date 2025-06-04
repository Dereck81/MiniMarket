package pe.edu.utp.marketapi.Domain.MetodoPago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "metodos_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;

    @Column(name = "nombre_metodo", length = 15)
    private String nombreMetodo;

    private Boolean estado = true;

    public MetodoPago(@NotNull DataRegisterMetodoPago data) {
        this.nombreMetodo = data.nombreMetodo();
        this.estado = true;
    }

    public void updateData(@NotNull DataUpdateMetodoPago data) {
        if (data.nombreMetodo() != null) this.nombreMetodo = data.nombreMetodo();
    }
}
