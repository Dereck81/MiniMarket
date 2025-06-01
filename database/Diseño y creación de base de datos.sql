CREATE DATABASE IF NOT EXISTS db_market;

USE db_market;

CREATE TABLE categorias (
	id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	descripcion TEXT DEFAULT NULL,
	estado BOOLEAN NOT NULL,
	nombre_categoria VARCHAR(255) UNIQUE NOT NULL

);


CREATE TABLE metodos_pagos (
	id_metodo_pago BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	estado BOOLEAN NOT NULL,
	nombre_metodo VARCHAR(15) UNIQUE NOT NULL

);


CREATE TABLE proveedores (
	id_proveedor BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	direccion VARCHAR(30) NOT NULL,
	email VARCHAR(255) UNIQUE DEFAULT NULL,
	estado BOOLEAN NOT NULL,
	nombre_proveedor VARCHAR(30) NOT NULL,
	ruc VARCHAR(30) UNIQUE NOT NULL,
	telefono CHAR(9) DEFAULT NULL,
	CONSTRAINT chk_proveedores_telefono CHECK (telefono IS NULL OR telefono REGEXP '^[0-9]{9}$'),
	CONSTRAINT chk_proveedores_email CHECK (email IS NULL OR email REGEXP '^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}$'),
	CONSTRAINT chk_proveedores_telefono_email CHECK (NOT (email IS NULL AND telefono IS NULL)),
	CONSTRAINT chk_proveedores_ruc CHECK (ruc REGEXP '^[0-9]+$')

);


CREATE TABLE roles (
	id_rol BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	estado BOOLEAN NOT NULL,
	nombre_rol VARCHAR(255) UNIQUE NOT NULL

);


CREATE TABLE productos (
	id_producto BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	descripcion TEXT NOT NULL,
	estado BOOLEAN NOT NULL,
	imagen VARCHAR(255) NOT NULL,
	nombre VARCHAR(30) NOT NULL,
	id_categoria BIGINT NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)

);


CREATE TABLE usuarios (
	id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	dni CHAR(8) UNIQUE NOT NULL,
	nombres VARCHAR(40) NOT NULL,
	apellidos VARCHAR(255) NOT NULL,
	pass VARCHAR(255) NOT NULL,
	telefono CHAR(9) DEFAULT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	estado BOOLEAN NOT NULL,
	fecha_actualizar DATETIME(6) NOT NULL,
	fecha_registro DATETIME(6) NOT NULL,
	imagen VARCHAR(255) NOT NULL,
	id_rol BIGINT NOT NULL,
	FOREIGN KEY (id_rol) REFERENCES roles(id_rol),
	CONSTRAINT chk_usuario_dni CHECK (dni REGEXP '^[0-9]{8}$'),
	CONSTRAINT chk_usuario_telefono CHECK (telefono IS NULL OR telefono REGEXP '^[0-9]{9}$'),
	CONSTRAINT chk_usuario_email CHECK (email REGEXP '^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}$')
    
);


CREATE TABLE inventarios (
	id_inventario BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	fecha_movimiento DATETIME(6) NOT NULL,
	precio_venta DOUBLE NOT NULL,
	stock INT NOT NULL,
	id_producto BIGINT NOT NULL,
	id_proveedor BIGINT NOT NULL,
	FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
	FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor),
	CONSTRAINT chk_precio_venta CHECK (precio_venta > 0)

);


CREATE TABLE pedidos (
	id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	estado BOOLEAN NOT NULL,
	fecha_pedido DATETIME(6) NOT NULL,
	total DOUBLE NOT NULL,
	id_usuario BIGINT NOT NULL,
	FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
	CONSTRAINT chk_pedidos_total CHECK (total > 0)

);


CREATE TABLE comprobante_venta (
	id_comprobante BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	fecha_emision DATETIME(6) NOT NULL,
	total DOUBLE NOT NULL,
	id_pedido BIGINT NOT NULL,
	FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
	CONSTRAINT chk_comprobante_venta_total CHECK (total > 0)

);


CREATE TABLE detalle_pedido (
	id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	cantidad INT NOT NULL,
	precio_unitario DOUBLE NOT NULL,
	id_inventario BIGINT NOT NULL,
	id_pedido BIGINT NOT NULL,
	FOREIGN KEY (id_inventario) REFERENCES inventarios(id_inventario),
	FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
	CONSTRAINT chk_cantidad CHECK (cantidad > 0),
	CONSTRAINT chk_precio_unitario CHECK (precio_unitario > 0)

);


CREATE TABLE pagos (
	id_pago BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	fecha_pago DATETIME(6) NOT NULL,
	monto_pago DOUBLE NOT NULL,
	id_metodo_pago BIGINT NOT NULL,
	id_pedido BIGINT NOT NULL,
	FOREIGN KEY (id_metodo_pago) REFERENCES metodos_pagos(id_metodo_pago),
	FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
	CONSTRAINT chk_monto_pago CHECK (monto_pago > 0)

);


