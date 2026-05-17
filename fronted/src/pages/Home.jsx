import React, { useEffect, useState } from 'react';
import { api } from '../services/api';
import { useCart } from '../context/CartContext';

export default function Home() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const { addToCart } = useCart();

  useEffect(() => {
    api.productos.getAll()
      .then(setProductos)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="container"><p>Cargando productos...</p></div>;

  return (
    <div className="container">
      <div className="page-header">
        <div>
          <h2>Nueva Colección</h2>
          <p>Descubre lo último en tendencia</p>
        </div>
      </div>

      <div className="products-grid">
        {productos.filter(p => p.active).map(producto => (
          <div key={producto.id} className="product-card">
            <div className="product-image">
              🛍️
            </div>
            <div className="product-info">
              <h3>{producto.nombre}</h3>
              <div className="price" style={{ marginTop: 'auto' }}>${parseFloat(producto.precio).toFixed(2)}</div>
              <button 
                className="btn" 
                style={{ width: '100%' }}
                onClick={() => addToCart(producto)}
              >
                Añadir al Carrito
              </button>
            </div>
          </div>
        ))}
        {productos.filter(p => p.active).length === 0 && (
          <p>No hay productos disponibles por el momento.</p>
        )}
      </div>
    </div>
  );
}
