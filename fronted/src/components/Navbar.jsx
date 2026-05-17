import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { ShoppingBag, Settings } from 'lucide-react';

export default function Navbar() {
  const { totalItems } = useCart();
  const location = useLocation();

  const isAdmin = location.pathname.startsWith('/admin');

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-brand">Nova Tienda</Link>
      <div className="navbar-links">
        {isAdmin ? (
          <Link to="/" className="nav-link">Volver a la Tienda</Link>
        ) : (
          <>
            <Link to="/" className={`nav-link ${location.pathname === '/' ? 'active' : ''}`}>Catálogo</Link>
            <Link to="/cart" className={`nav-link ${location.pathname === '/cart' ? 'active' : ''}`}>
              <ShoppingBag size={20} style={{ verticalAlign: 'middle', marginRight: '4px' }} />
              Carrito
              {totalItems > 0 && <span className="cart-badge">{totalItems}</span>}
            </Link>
            <Link to="/admin/productos" className="nav-link">
              <Settings size={20} style={{ verticalAlign: 'middle' }} />
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}
