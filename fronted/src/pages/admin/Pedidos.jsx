import React, { useState, useEffect } from 'react';
import { api } from '../../services/api';

export default function Pedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.pedidos.getAll()
      .then(setPedidos)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Cargando pedidos...</p>;

  return (
    <div>
      <div className="page-header">
        <h2>Listado de Pedidos</h2>
      </div>

      <table className="data-table">
        <thead>
          <tr>
            <th>ID Pedido</th>
            <th>Fecha</th>
            <th>Usuario</th>
            <th>Total</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {pedidos.map(p => (
            <tr key={p.id}>
              <td>#{p.id}</td>
              <td>{new Date(p.fecha).toLocaleDateString()} {new Date(p.fecha).toLocaleTimeString()}</td>
              <td>{p.usuarioNombre || `Usuario ID: ${p.usuarioId}`}</td>
              <td><strong>${parseFloat(p.total).toFixed(2)}</strong></td>
              <td>
                <span className="status-badge" style={{ background: p.estado === 'COMPLETADO' ? 'var(--success-color)' : 'var(--primary-color)' }}>
                  {p.estado}
                </span>
              </td>
            </tr>
          ))}
          {pedidos.length === 0 && (
            <tr><td colSpan="5" style={{ textAlign: 'center' }}>No hay pedidos registrados</td></tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
