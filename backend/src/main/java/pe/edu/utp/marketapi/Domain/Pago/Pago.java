package pe.edu.utp.marketapi.Domain.Pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPago;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "monto_pagado")
    private double montoPagado;

    @Column(name = "fecha_pago")
    private Timestamp fechaPago;

    public Pago(Long idPedido, Long idModoPago, Double monto){
        this.pedido = Pedido.builder().idPedido(idPedido).build();
        this.metodoPago = MetodoPago.builder().idMetodoPago(idModoPago).build();
        this.montoPagado = monto;
        this.fechaPago = new Timestamp(System.currentTimeMillis());
    }

    public Pago(DataRegisterPago dataRegisterPago){
        this.idPago = null;
        this.pedido = Pedido.builder().idPedido(dataRegisterPago.idPedido()).build();
        this.metodoPago = MetodoPago.builder().idMetodoPago(dataRegisterPago.idMetodoPago()).build();
        this.montoPagado = dataRegisterPago.monto();
        this.fechaPago = new Timestamp(System.currentTimeMillis());
    }

    public void updatePago(DataUpdatePago dataUpdatePago){
        if (dataUpdatePago.idPago() != null) this.idPago = dataUpdatePago.idPago();
        if (dataUpdatePago.idPedido() != null) this.pedido = Pedido.builder().idPedido(dataUpdatePago.idPedido()).build();
        if (dataUpdatePago.idMetodoPago() != null) this.metodoPago = MetodoPago.builder().idMetodoPago(dataUpdatePago.idMetodoPago()).build();
        if (dataUpdatePago.monto() != null) this.montoPagado = dataUpdatePago.monto();
        this.fechaPago = new Timestamp(System.currentTimeMillis());
    }
}
