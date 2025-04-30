import { useState } from 'react';
import Sidebar from './components/sidebar';
import Dashboard from './pages/Dashboard';
import Categorias from './pages/Categoria';
import Proveedores from './pages/proveedores';
import MetodosPago from './pages/MetodosPago';
import Productos from './pages/productos';
import Pedidos from './pages/Pedidos';
import Inventario from './pages/Inventario';
import Usuarios from './pages/Usuarios';
import Roles from './pages/Roles';
import Cuenta from './pages/Cuenta';
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
    <div className="flex h-screen bg-gray-100">
      <Sidebar currentPage={currentPage} setCurrentPage={setCurrentPage} />
      <div className="flex-1 overflow-auto">
        {renderPage()}
      </div>
    </div>
  );
};

export default App;
