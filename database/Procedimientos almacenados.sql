USE db_market;

DELIMITER //


CREATE PROCEDURE sp_crear_categoria(
    IN p_nombre_categoria VARCHAR(255),
    IN p_descripcion TEXT
)
BEGIN
    INSERT INTO categorias (nombre_categoria, descripcion, estado)
    VALUES (p_nombre_categoria, p_descripcion, TRUE);
    
    SELECT LAST_INSERT_ID() as id_categoria, 'Categoría creada exitosamente' as mensaje;
END //


CREATE PROCEDURE sp_actualizar_categoria(
    IN p_id_categoria BIGINT,
    IN p_nombre_categoria VARCHAR(255),
    IN p_descripcion TEXT,
    IN p_estado BOOLEAN
)
BEGIN
    UPDATE categorias 
    SET nombre_categoria = p_nombre_categoria,
        descripcion = p_descripcion,
        estado = p_estado
    WHERE id_categoria = p_id_categoria;
    
    SELECT 'Categoría actualizada exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_listar_categorias_activas()
BEGIN
    SELECT id_categoria, nombre_categoria, descripcion, estado
    FROM categorias
    WHERE estado = TRUE
    ORDER BY nombre_categoria;
END //


CREATE PROCEDURE sp_crear_proveedor(
    IN p_nombre_proveedor VARCHAR(30),
    IN p_ruc VARCHAR(30),
    IN p_direccion VARCHAR(30),
    IN p_telefono CHAR(9),
    IN p_email VARCHAR(255)
)
BEGIN
    INSERT INTO proveedores (nombre_proveedor, ruc, direccion, telefono, email, estado)
    VALUES (p_nombre_proveedor, p_ruc, p_direccion, p_telefono, p_email, TRUE);
    
    SELECT LAST_INSERT_ID() as id_proveedor, 'Proveedor creado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_listar_proveedores_activos()
BEGIN
    SELECT id_proveedor, nombre_proveedor, ruc, direccion, telefono, email
    FROM proveedores
    WHERE estado = TRUE
    ORDER BY nombre_proveedor;
END //

CREATE PROCEDURE sp_crear_producto(
    IN p_nombre VARCHAR(30),
    IN p_descripcion TEXT,
    IN p_imagen VARCHAR(255),
    IN p_id_categoria BIGINT
)
BEGIN
    INSERT INTO productos (nombre, descripcion, imagen, id_categoria, estado)
    VALUES (p_nombre, p_descripcion, p_imagen, p_id_categoria, TRUE);
    
    SELECT LAST_INSERT_ID() as id_producto, 'Producto creado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_actualizar_producto(
    IN p_id_producto BIGINT,
    IN p_nombre VARCHAR(30),
    IN p_descripcion TEXT,
    IN p_imagen VARCHAR(255),
    IN p_id_categoria BIGINT,
    IN p_estado BOOLEAN
)
BEGIN
    UPDATE productos 
    SET nombre = p_nombre,
        descripcion = p_descripcion,
        imagen = p_imagen,
        id_categoria = p_id_categoria,
        estado = p_estado
    WHERE id_producto = p_id_producto;
    
    SELECT 'Producto actualizado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_listar_productos_disponibles()
BEGIN
    SELECT 
        p.id_producto,
        p.nombre,
        p.descripcion,
        p.imagen,
        c.nombre_categoria,
        i.precio_venta,
        i.stock,
        pr.nombre_proveedor
    FROM productos p
    INNER JOIN categorias c ON p.id_categoria = c.id_categoria
    INNER JOIN inventarios i ON p.id_producto = i.id_producto
    INNER JOIN proveedores pr ON i.id_proveedor = pr.id_proveedor
    WHERE p.estado = TRUE AND i.stock > 0
    ORDER BY p.nombre;
END //

CREATE PROCEDURE sp_buscar_productos_por_categoria(
    IN p_id_categoria BIGINT
)
BEGIN
    SELECT 
        p.id_producto,
        p.nombre,
        p.descripcion,
        p.imagen,
        c.nombre_categoria,
        i.precio_venta,
        i.stock
    FROM productos p
    INNER JOIN categorias c ON p.id_categoria = c.id_categoria
    INNER JOIN inventarios i ON p.id_producto = i.id_producto
    WHERE p.id_categoria = p_id_categoria 
    AND p.estado = TRUE 
    AND i.stock > 0
    ORDER BY p.nombre;
END //

CREATE PROCEDURE sp_agregar_stock(
    IN p_id_producto BIGINT,
    IN p_id_proveedor BIGINT,
    IN p_stock INT,
    IN p_precio_venta DOUBLE
)
BEGIN
    DECLARE v_existe INT DEFAULT 0;
    
    SELECT COUNT(*) INTO v_existe
    FROM inventarios
    WHERE id_producto = p_id_producto AND id_proveedor = p_id_proveedor;
    
    IF v_existe > 0 THEN
        UPDATE inventarios
        SET stock = stock + p_stock,
            precio_venta = p_precio_venta,
            fecha_movimiento = NOW()
        WHERE id_producto = p_id_producto AND id_proveedor = p_id_proveedor;
        
        SELECT 'Stock actualizado exitosamente' as mensaje;
    ELSE
        INSERT INTO inventarios (id_producto, id_proveedor, stock, precio_venta, fecha_movimiento)
        VALUES (p_id_producto, p_id_proveedor, p_stock, p_precio_venta, NOW());
        
        SELECT 'Nuevo inventario creado exitosamente' as mensaje;
    END IF;
END //

CREATE PROCEDURE sp_consultar_stock()
BEGIN
    SELECT 
        p.nombre as producto,
        pr.nombre_proveedor,
        i.stock,
        i.precio_venta,
        i.fecha_movimiento as ultima_actualizacion,
        c.nombre_categoria
    FROM inventarios i
    INNER JOIN productos p ON i.id_producto = p.id_producto
    INNER JOIN proveedores pr ON i.id_proveedor = pr.id_proveedor
    INNER JOIN categorias c ON p.id_categoria = c.id_categoria
    WHERE p.estado = TRUE
    ORDER BY p.nombre;
END //

CREATE PROCEDURE sp_productos_stock_bajo()
BEGIN
    SELECT 
        p.nombre as producto,
        i.stock,
        pr.nombre_proveedor,
        c.nombre_categoria
    FROM inventarios i
    INNER JOIN productos p ON i.id_producto = p.id_producto
    INNER JOIN proveedores pr ON i.id_proveedor = pr.id_proveedor
    INNER JOIN categorias c ON p.id_categoria = c.id_categoria
    WHERE i.stock < 10 AND p.estado = TRUE
    ORDER BY i.stock ASC;
END //

CREATE PROCEDURE sp_registrar_usuario(
    IN p_dni CHAR(8),
    IN p_nombres VARCHAR(40),
    IN p_apellidos VARCHAR(255),
    IN p_pass VARCHAR(255),
    IN p_telefono CHAR(9),
    IN p_email VARCHAR(255),
    IN p_imagen VARCHAR(255),
    IN p_id_rol BIGINT
)
BEGIN
    INSERT INTO usuarios (dni, nombres, apellidos, pass, telefono, email, imagen, id_rol, estado, fecha_registro, fecha_actualizar)
    VALUES (p_dni, p_nombres, p_apellidos, p_pass, p_telefono, p_email, p_imagen, p_id_rol, TRUE, NOW(), NOW());
    
    SELECT LAST_INSERT_ID() as id_usuario, 'Usuario registrado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_autenticar_usuario(
    IN p_email VARCHAR(255),
    IN p_pass VARCHAR(255)
)
BEGIN
    SELECT 
        u.id_usuario,
        u.dni,
        u.nombres,
        u.apellidos,
        u.email,
        u.telefono,
        u.imagen,
        r.nombre_rol,
        u.estado
    FROM usuarios u
    INNER JOIN roles r ON u.id_rol = r.id_rol
    WHERE u.email = p_email 
    AND u.pass = p_pass 
    AND u.estado = TRUE;
END //

CREATE PROCEDURE sp_crear_pedido(
    IN p_id_usuario BIGINT,
    IN p_total DOUBLE
)
BEGIN
    INSERT INTO pedidos (id_usuario, total, fecha_pedido, estado)
    VALUES (p_id_usuario, p_total, NOW(), TRUE);
    
    SELECT LAST_INSERT_ID() as id_pedido, 'Pedido creado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_agregar_detalle_pedido(
    IN p_id_pedido BIGINT,
    IN p_id_inventario BIGINT,
    IN p_cantidad INT,
    IN p_precio_unitario DOUBLE
)
BEGIN
    DECLARE v_stock_disponible INT DEFAULT 0;
    
    SELECT stock INTO v_stock_disponible
    FROM inventarios
    WHERE id_inventario = p_id_inventario;
    
    IF v_stock_disponible >= p_cantidad THEN
        INSERT INTO detalle_pedido (id_pedido, id_inventario, cantidad, precio_unitario)
        VALUES (p_id_pedido, p_id_inventario, p_cantidad, p_precio_unitario);

        UPDATE inventarios
        SET stock = stock - p_cantidad
        WHERE id_inventario = p_id_inventario;
        
        SELECT 'Producto agregado al pedido exitosamente' as mensaje;
    ELSE
        SELECT 'Stock insuficiente' as mensaje, v_stock_disponible as stock_disponible;
    END IF;
END //

CREATE PROCEDURE sp_visualizar_pedidos()
BEGIN
    SELECT 
        pe.id_pedido,
        u.nombres,
        u.apellidos,
        u.email,
        pe.fecha_pedido,
        pe.total,
        pe.estado,
        COUNT(dp.id_detalle) as total_productos
    FROM pedidos pe
    INNER JOIN usuarios u ON pe.id_usuario = u.id_usuario
    LEFT JOIN detalle_pedido dp ON pe.id_pedido = dp.id_pedido
    GROUP BY pe.id_pedido, u.nombres, u.apellidos, u.email, pe.fecha_pedido, pe.total, pe.estado
    ORDER BY pe.fecha_pedido DESC;
END //

CREATE PROCEDURE sp_detalle_pedido(
    IN p_id_pedido BIGINT
)
BEGIN
    SELECT 
        pe.id_pedido,
        u.nombres,
        u.apellidos,
        u.email,
        u.telefono,
        pe.fecha_pedido,
        pe.total,
        pe.estado,
        p.nombre as producto,
        dp.cantidad,
        dp.precio_unitario,
        (dp.cantidad * dp.precio_unitario) as subtotal
    FROM pedidos pe
    INNER JOIN usuarios u ON pe.id_usuario = u.id_usuario
    INNER JOIN detalle_pedido dp ON pe.id_pedido = dp.id_pedido
    INNER JOIN inventarios i ON dp.id_inventario = i.id_inventario
    INNER JOIN productos p ON i.id_producto = p.id_producto
    WHERE pe.id_pedido = p_id_pedido;
END //

CREATE PROCEDURE sp_actualizar_estado_pedido(
    IN p_id_pedido BIGINT,
    IN p_estado BOOLEAN
)
BEGIN
    UPDATE pedidos
    SET estado = p_estado
    WHERE id_pedido = p_id_pedido;
    
    SELECT 'Estado del pedido actualizado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_registrar_pago(
    IN p_id_pedido BIGINT,
    IN p_id_metodo_pago BIGINT,
    IN p_monto_pago DOUBLE
)
BEGIN
    INSERT INTO pagos (id_pedido, id_metodo_pago, monto_pago, fecha_pago)
    VALUES (p_id_pedido, p_id_metodo_pago, p_monto_pago, NOW());
    
    SELECT LAST_INSERT_ID() as id_pago, 'Pago registrado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_reporte_ventas_periodo(
    IN p_fecha_inicio DATE,
    IN p_fecha_fin DATE
)
BEGIN
    SELECT 
        DATE(pe.fecha_pedido) as fecha,
        COUNT(pe.id_pedido) as total_pedidos,
        SUM(pe.total) as total_ventas,
        AVG(pe.total) as promedio_venta
    FROM pedidos pe
    WHERE DATE(pe.fecha_pedido) BETWEEN p_fecha_inicio AND p_fecha_fin
    AND pe.estado = TRUE
    GROUP BY DATE(pe.fecha_pedido)
    ORDER BY fecha DESC;
END //

CREATE PROCEDURE sp_productos_mas_vendidos(
    IN p_limite INT
)
BEGIN
    SELECT 
        p.nombre as producto,
        c.nombre_categoria,
        SUM(dp.cantidad) as total_vendido,
        SUM(dp.cantidad * dp.precio_unitario) as total_ingresos
    FROM detalle_pedido dp
    INNER JOIN inventarios i ON dp.id_inventario = i.id_inventario
    INNER JOIN productos p ON i.id_producto = p.id_producto
    INNER JOIN categorias c ON p.id_categoria = c.id_categoria
    INNER JOIN pedidos pe ON dp.id_pedido = pe.id_pedido
    WHERE pe.estado = TRUE
    GROUP BY p.id_producto, p.nombre, c.nombre_categoria
    ORDER BY total_vendido DESC
    LIMIT p_limite;
END //

CREATE PROCEDURE sp_generar_comprobante(
    IN p_id_pedido BIGINT
)
BEGIN
    DECLARE v_total DOUBLE DEFAULT 0;

    SELECT total INTO v_total
    FROM pedidos
    WHERE id_pedido = p_id_pedido;
    
    INSERT INTO comprobante_venta (id_pedido, total, fecha_emision)
    VALUES (p_id_pedido, v_total, NOW());
    
    SELECT LAST_INSERT_ID() as id_comprobante, 'Comprobante generado exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_historial_usuario(
    IN p_id_usuario BIGINT
)
BEGIN
    SELECT 
        pe.id_pedido,
        pe.fecha_pedido,
        pe.total,
        pe.estado,
        COUNT(dp.id_detalle) as total_productos
    FROM pedidos pe
    LEFT JOIN detalle_pedido dp ON pe.id_pedido = dp.id_pedido
    WHERE pe.id_usuario = p_id_usuario
    GROUP BY pe.id_pedido, pe.fecha_pedido, pe.total, pe.estado
    ORDER BY pe.fecha_pedido DESC;
END //

DELIMITER ;