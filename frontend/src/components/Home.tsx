import React, { useState } from 'react';
import ProductCard from './ProductCard';

const Home: React.FC = () => {
    // Estado para los productos y para el filtro de búsqueda
    const [searchQuery, setSearchQuery] = useState('');
    const [productos, setProductos] = useState([
        { nombre: 'Manzanas', precio: 2.99 },
        { nombre: 'Leche', precio: 1.49 },
        { nombre: 'Arroz', precio: 0.99 },
        { nombre: 'Pan', precio: 1.20 },
        { nombre: 'Huevos', precio: 10.5 },
        { nombre: 'Jabón', precio: 3.80 }
    ]);

    
    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchQuery(e.target.value);
    };

    
    const filteredProducts = productos.filter(prod =>
        prod.nombre.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Nuestros Productos</h2>

            {/* Campo de búsqueda */}
            <div className="mb-4 text-center">
                <input
                    type="text"
                    className="form-control w-50 mx-auto"
                    placeholder="Buscar productos..."
                    value={searchQuery}
                    onChange={handleSearchChange}
                />
            </div>

            <div className="d-flex flex-wrap justify-content-center">
                {filteredProducts.map((prod, index) => (
                    <ProductCard key={index} nombre={prod.nombre} precio={prod.precio} />
                ))}
            </div>
        </div>
    );
}

export default Home;
