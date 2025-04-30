import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavigationBar from './components/NavigationBar';
import Home from './components/Home';
import Nosotros from './components/Nosotros';
import Contactanos from './components/Contactanos';
import Login from './components/Login';
import './App.css';

const App = () => (
  <BrowserRouter>
    <NavigationBar />
    <div className="main-content">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/nosotros" element={<Nosotros />} />
        <Route path="/contactanos" element={<Contactanos />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </div>
  </BrowserRouter>
);

export default App;
