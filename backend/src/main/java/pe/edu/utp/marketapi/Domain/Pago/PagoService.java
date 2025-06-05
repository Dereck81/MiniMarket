package pe.edu.utp.marketapi.Domain.Pago;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPago;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPagoRepository;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPagoService;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;
import pe.edu.utp.marketapi.Domain.Pedido.PedidoRepository;
import pe.edu.utp.marketapi.Domain.Pedido.PedidoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private MetodoPagoService metodoPagoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Transactional
    public boolean save(@NotNull DataRegisterPago pago){
        metodoPagoService.existsById(pago.idMetodoPago());
        pedidoService.existsById(pago.idPedido());

        Pago newPago = new Pago(pago);
        pagoRepository.save(newPago);
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataUpdatePago pago){
        if (pagoRepository.existsById(pago.idPago())){

            if (pago.idMetodoPago() != null) metodoPagoService.existsById(pago.idMetodoPago());
            if (pago.idPedido() != null) pedidoService.existsById(pago.idPedido());

            Pago updatePago = pagoRepository.getById(pago.idPago());
            updatePago.updatePago(pago);
            pagoRepository.save(updatePago);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delete(@NotNull Long id){
        if (pagoRepository.existsById(id)){
            pagoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public DataListPago findById(@NotNull Long id){
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago not found"));
        return new DataListPago(pago);
    }

    public DataListPago findByPedidoId(@NotNull Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido not found"));
        Pago pago = pagoRepository.findByPedido(pedido)
                .orElseThrow(() -> new RuntimeException("Pago not found"));
        return new DataListPago(pago);
    }

    public List<EstadisticaMetodoPago> getEstadisticasPagos() {
        List<EstadisticaMetodoPago> response = new ArrayList<>();
        List<MetodoPago> metodosPago = metodoPagoRepository.findAll();
        for (MetodoPago pago : metodosPago) {
            int cantidadPagos = pagoRepository.countByMetodoPago(pago.getIdMetodoPago());
            response.add(new EstadisticaMetodoPago(pago.getNombreMetodo(), cantidadPagos));
        }
        return response;
    }

    public List<VentasMes> getVentasMes() {
        List<VentasMes> response = new ArrayList<>();
        List<String> mesesString = List.of("ENE", "FEB", "MAR", "ABR", "MAY", "JUN",
                "JUL", "AGO", "SEP", "OCT", "NOV", "DIC");
        List<Integer> mesesNumber = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        int i= 0;
        for (Integer mes : mesesNumber) {
            Double totalVentas = pagoRepository.sumTotalVentasByMes(mes);
            response.add(new VentasMes(mesesString.get(i), totalVentas));
            i++;
        }
        return response;
    }
}
