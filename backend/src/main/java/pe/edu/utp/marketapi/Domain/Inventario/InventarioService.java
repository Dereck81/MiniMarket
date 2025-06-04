package pe.edu.utp.marketapi.Domain.Inventario;

import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.Categoria.Categoria;
import pe.edu.utp.marketapi.Domain.Categoria.CategoriaRepository;
import pe.edu.utp.marketapi.Domain.Producto.Producto;
import pe.edu.utp.marketapi.Domain.Producto.ProductoService;
import pe.edu.utp.marketapi.Domain.Proveedor.ProveedorService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public boolean save(@NotNull DataRegisterInventario inventario){
        productoService.existById(inventario.idProducto());
        proveedorService.existById(inventario.idProveedor());

        inventarioRepository.save(new Inventario(inventario));
        return true;
    }

    @Transactional
    public boolean update(DataUpdateInventario dataUpdatePedido) {
        Inventario inventario = inventarioRepository.findById(dataUpdatePedido.idInventario())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        inventario.update(dataUpdatePedido);
        inventarioRepository.save(inventario);
        return true;
    }


    @Transactional
    public boolean delete(@NotNull Long id){
        inventarioRepository.deleteById(id);
        return true;
    }

    public List<DataListInventario> getAll(){
        return inventarioRepository.findAll().stream()
                .filter(inventario -> inventario.getStock() > 0)
                .map(DataListInventario::new)
                .toList();
    }

    public DataListInventario findById(@NotNull Long id){
        return new DataListInventario(inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado")));
    }

    public List<DataListInventario> findByNombre(@NotNull String nombre){
        List<Inventario> inventarios = inventarioRepository.findByNombre(nombre);
        return inventarios.stream()
                .filter(inventario -> inventario.getStock() > 0)
                .map(DataListInventario::new)
                .toList();
    }

    public List<DataListInventario> findByCategoria(Long idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        List<Inventario> inventarios = inventarioRepository.findByCategory(categoria);
        return inventarios.stream()
                .filter(inventario -> inventario.getStock() > 0)
                .map(DataListInventario::new)
                .toList();
    }

    public List<LowSockProductos> getStockProductos() {
        List<LowSockProductos> lowSockProductos = new ArrayList<>();
        List<Inventario> inventarios = inventarioRepository.findAll();
        for (Inventario inventario : inventarios) {
            if (inventario.getStock() < 10) {
                Producto producto = inventario.getProducto();
                lowSockProductos.add(
                        new LowSockProductos(producto.getIdProducto(),
                                producto.getNombre(),
                                inventario.getStock(),
                                inventario.getProducto().getCategoria().getNombreCategoria(),
                                inventario.getProveedor().getNombreProveedor()
                        )
                );
            }
        }
        return lowSockProductos;
    }

    public byte[] generateInventarioReport() {
        try{
            List<DataListInventario> inventarios = getAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Inventario");

            String[] headers = {"ID Inventario", "Producto", "Proveedor", "Stock", "Precio Venta", "Fecha Movimiento"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            int rowIdx = 1;
            for (DataListInventario inventario : inventarios) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(inventario.idInventario());
                row.createCell(1).setCellValue(inventario.producto() != null ? inventario.producto().nombre() : "N/A");
                row.createCell(2).setCellValue(inventario.proveedor() != null ? inventario.proveedor().nombreProveedor() : "N/A");
                row.createCell(3).setCellValue(inventario.stock());
                row.createCell(4).setCellValue(inventario.precioVenta());
                row.createCell(5).setCellValue(inventario.fechaMovimiento());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);
                return bos.toByteArray();
            } finally {
                workbook.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
