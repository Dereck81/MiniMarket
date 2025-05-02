import { useState } from 'react';
import Sidebar from './AdminComponent/Sidebar';
import Dashboard from './AdminPages/Dashboard';
import Categorias from './AdminPages/Categoria';
import Proveedores from './AdminPages/Proveedores';
import MetodosPago from './AdminPages/MetodosPago';
import Productos from './AdminPages/Productos';
import Pedidos from './AdminPages/Pedidos';
import Inventario from './AdminPages/Inventario';
import Usuarios from './AdminPages/Usuarios';
import Roles from './AdminPages/Roles';
import Cuenta from './AdminPages/Cuenta';
import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => {
    const [currentPage, setCurrentPage] = useState<string>('pedidos');
  
    const renderPage = () => {
      switch (currentPage) {
        case 'dashboard':
          return <Dashboard />;
        case 'categorias':
          return <Categorias />;
        case 'proveedores':
          return <Proveedores />;
        case 'metodos-pago':
          return <MetodosPago />;
        case 'productos':
          return <Productos />;
        case 'pedidos':
          return <Pedidos />;
        case 'inventario':
          return <Inventario />;
        case 'usuarios':
          return <Usuarios />;
        case 'roles':
          return <Roles />;
        case 'cuenta':
          return <Cuenta />;
        default:
          return <Dashboard />;
      }
    };
  
    return (
      <div className="d-flex" style={{ minHeight: '100vh' }}>
        <div style={{ width: '250px', position: 'fixed', top: 0, bottom: 0, left: 0, zIndex: 1000, height: '100vh' }}>
          <Sidebar currentPage={currentPage} setCurrentPage={setCurrentPage} />
        </div>
        <div style={{ marginLeft: '250px', width: 'calc(100% - 250px)', padding: '1rem', overflowY: 'auto' }}>
          {renderPage()}
        </div>
      </div>
    );
  };
  
  export default App;
  