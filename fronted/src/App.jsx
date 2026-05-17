import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { CartProvider } from './context/CartContext';
import Navbar from './components/Navbar';

// Páginas Públicas
import Home from './pages/Home';
import Cart from './pages/Cart';

// Páginas de Administración
import AdminLayout from './pages/admin/AdminLayout';
import Categorias from './pages/admin/Categorias';
import Productos from './pages/admin/Productos';
import Usuarios from './pages/admin/Usuarios';
import Pedidos from './pages/admin/Pedidos';

function App() {
  return (
    <CartProvider>
      <BrowserRouter>
        <Navbar />
        <Routes>
          {/* Rutas Públicas */}
          <Route path="/" element={<Home />} />
          <Route path="/cart" element={<Cart />} />
          
          {/* Rutas de Administración */}
          <Route path="/admin" element={<AdminLayout />}>
            <Route index element={<Navigate to="productos" replace />} />
            <Route path="categorias" element={<Categorias />} />
            <Route path="productos" element={<Productos />} />
            <Route path="usuarios" element={<Usuarios />} />
            <Route path="pedidos" element={<Pedidos />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </CartProvider>
  );
}

export default App;
