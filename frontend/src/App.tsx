
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavigationBar from './components/NavigationBar';
import Home from './components/Home';
import Nosotros from './components/Nosotros';
import Contactanos from './components/Contactanos';
import Login from './components/Login';

const App = () => (
  <BrowserRouter>
    <NavigationBar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/nosotros" element={<Nosotros />} />
      <Route path="/contactanos" element={<Contactanos />} />
      <Route path="/login" element={<Login />} />
    </Routes>
  </BrowserRouter>
);

export default App;
