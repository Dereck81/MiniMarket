import { Menu, Truck, CreditCard, Package, ShoppingBag, Users, Shield, User, LogOut, Layers } from 'lucide-react';
import SidebarLink from './sidebarLink';

interface SidebarProps {
  currentPage: string;
  setCurrentPage: React.Dispatch<React.SetStateAction<string>>;
}

const Sidebar: React.FC<SidebarProps> = ({ currentPage, setCurrentPage }) => {
  const isActive = (page: string) => currentPage === page ? 'active-link' : '';

  return (
    <div className="bg-success text-white d-flex flex-column p-3" style={{ width: '250px', minHeight: '100vh' }}>
      <div className="mb-4 text-center fw-bold">
        <div className="rounded-circle bg-light text-dark p-2 mx-auto mb-2">Administrador</div>
      </div>

      <SidebarLink onClick={() => setCurrentPage('dashboard')} icon={<Menu size={20} />} text="Dashboard" className={isActive('dashboard')} />
      <SidebarLink onClick={() => setCurrentPage('categorias')} icon={<Layers size={20} />} text="Categorías" className={isActive('categorias')} />
      <SidebarLink onClick={() => setCurrentPage('proveedores')} icon={<Truck size={20} />} text="Proveedores" className={isActive('proveedores')} />
      <SidebarLink onClick={() => setCurrentPage('metodos-pago')} icon={<CreditCard size={20} />} text="Métodos de Pago" className={isActive('metodos-pago')} />
      <SidebarLink onClick={() => setCurrentPage('productos')} icon={<Package size={20} />} text="Productos" className={isActive('productos')} />
      <SidebarLink onClick={() => setCurrentPage('pedidos')} icon={<ShoppingBag size={20} />} text="Pedidos" className={isActive('pedidos')} />
      <SidebarLink onClick={() => setCurrentPage('inventario')} icon={<Layers size={20} />} text="Inventario" className={isActive('inventario')} />
      <SidebarLink onClick={() => setCurrentPage('usuarios')} icon={<Users size={20} />} text="Usuarios" className={isActive('usuarios')} />
      <SidebarLink onClick={() => setCurrentPage('roles')} icon={<Shield size={20} />} text="Roles" className={isActive('roles')} />
      <SidebarLink onClick={() => setCurrentPage('cuenta')} icon={<User size={20} />} text="Cuenta" className={isActive('cuenta')} />

      <div className="mt-auto">
        <SidebarLink onClick={() => console.log('Cerrar sesión')} icon={<LogOut size={20} />} text="Cerrar Sesión" className="" />
      </div>
    </div>
  );
};

export default Sidebar;
