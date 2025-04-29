import React from 'react';

interface ProductCardProps {
  nombre: string;
  precio: number;
}

const ProductCard: React.FC<ProductCardProps> = ({ nombre, precio }) => {
  return (
    <div className="card m-2" style={{ width: '18rem' }}>
      <img src="https://via.placeholder.com/150" className="card-img-top" alt={nombre} />
      <div className="card-body">
        <h5 className="card-title">{nombre}</h5>
        <p className="card-text">Precio: ${precio}</p>
        <button className="btn btn-primary">Agregar al carrito</button>
      </div>
    </div>
  );
};

export default ProductCard;
