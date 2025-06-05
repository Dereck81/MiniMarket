create table categorias
(
    id_categoria     int auto_increment
        primary key,
    nombre_categoria varchar(50) null,
    descripcion      text        null
);

create table metodos_pago
(
    id_metodo_pago int auto_increment
        primary key,
    nombre_metodo  varchar(15) null
);

create table productos
(
    id_producto  int auto_increment
        primary key,
    nombre       varchar(30)  not null,
    descripcion  text         not null,
    imagen       varchar(100) null,
    id_categoria int          not null,
    constraint productos_ibfk_1
        foreign key (id_categoria) references categorias (id_categoria)
);

create index id_categoria
    on productos (id_categoria);

create table proveedores
(
    id_proveedor     int auto_increment
        primary key,
    nombre_proveedor varchar(15) null,
    telefono         char(9)     null,
    email            varchar(25) null,
    direccion        varchar(30) null,
    ruc              varchar(30) null
);

create table inventarios
(
    id_inventario    int auto_increment
        primary key,
    id_producto      int            null,
    id_proveedor     int            null,
    stock            int            null,
    precio_venta     decimal(10, 2) null,
    fecha_movimiento timestamp      null,
    constraint inventarios_ibfk_1
        foreign key (id_producto) references productos (id_producto),
    constraint inventarios_ibfk_2
        foreign key (id_proveedor) references proveedores (id_proveedor)
);

create index id_producto
    on inventarios (id_producto);

create index id_proveedor
    on inventarios (id_proveedor);

create table usuarios
(
    id_usuario       int auto_increment
        primary key,
    nombre_usuario   varchar(15)                                                       not null,
    email            varchar(25)                                                       not null,
    contraseña       varchar(100)                                                      not null,
    rol              enum ('ADMIN', 'CLIENTE', 'VENDEDOR') default 'CLIENTE'           not null,
    dni              char(8)                                                           null,
    fecha_registro   timestamp                             default current_timestamp() null,
    fecha_actualizar timestamp                             default current_timestamp() null,
    telefono         varchar(9)                                                        null,
    imagen           varchar(100)                                                      null
);

create table pedidos
(
    id_pedido    int auto_increment
        primary key,
    id_usuario   int            null,
    fecha_pedido timestamp      null,
    total        decimal(10, 2) null,
    constraint pedidos_ibfk_1
        foreign key (id_usuario) references usuarios (id_usuario)
);

create table comprobante_venta
(
    id_comprobante int auto_increment
        primary key,
    id_pedido      int            null,
    fecha_emision  timestamp      null,
    total          decimal(10, 2) null,
    constraint comprobante_venta_ibfk_1
        foreign key (id_pedido) references pedidos (id_pedido)
            on delete cascade
);

create table detalle_pedido
(
    id_detalle      int auto_increment
        primary key,
    id_pedido       int            null,
    id_inventario   int            null,
    cantidad        int            null,
    precio_unitario decimal(10, 2) null,
    constraint detalle_pedido_ibfk_1
        foreign key (id_pedido) references pedidos (id_pedido)
            on delete cascade,
    constraint detalle_pedido_ibfk_2
        foreign key (id_inventario) references inventarios (id_inventario)
);

create table pagos
(
    id_pago        int auto_increment
        primary key,
    id_pedido      int            null,
    id_metodo_pago int            null,
    monto_pagado   decimal(10, 2) null,
    fecha_pago     timestamp      null,
    constraint pagos_ibfk_1
        foreign key (id_pedido) references pedidos (id_pedido)
            on delete cascade,
    constraint pagos_ibfk_2
        foreign key (id_metodo_pago) references metodos_pago (id_metodo_pago)
);

create index id_metodo_pago
    on pagos (id_metodo_pago);

create index id_usuario
    on pedidos (id_usuario);

create index usuarios___fk___rol
    on usuarios (rol);




-- Update db

create table roles
(
    id_rol   int auto_increment primary key,
    nombre_rol varchar(15) not null unique
);

-- Insertar roles
insert into roles (nombre_rol) values ('ADMIN'), ('CLIENTE'), ('VENDEDOR');



alter table usuarios
    drop column rol,
    add column id_rol int not null;


alter table usuarios
    add constraint usuarios_ibfk_rol_table
        foreign key (id_rol) references roles (id_rol);