import React from 'react';
import { Link } from 'react-router-dom';

const NavigationBar: React.FC = () => {
    return (
        <nav className="navbar navbar-expand-lg bg-primary navbar-dark">
            <div className="container">
                <Link className="navbar-brand fw-bold" to="/">MiniMarket</Link>

                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse justify-content-center" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link" to="/">Inicio</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/nosotros">Nosotros</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/contactanos">Contáctanos</Link>
                        </li>
                    </ul>
                </div>

                <div className="d-flex ms-auto">
                    <Link className="btn btn-light" to="/login">
                        Iniciar Sesión
                    </Link>
                </div>
            </div>
        </nav>
    );
}

export default NavigationBar;
