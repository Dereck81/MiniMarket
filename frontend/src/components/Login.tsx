import React from 'react';


const Login: React.FC = () => {
    return (
        <div className="d-flex justify-content-center align-items-center vh-100" style={{ background: '#f0f2f5' }}>
            <div className="card p-4 shadow" style={{ width: '22rem', borderRadius: '1rem' }}>
                <h2 className="text-center mb-4" style={{ color: '#0d6efd' }}>
                    Bienvenido
                </h2>
                <form>
                    <div className="mb-3">
                        <label className="form-label">Correo Electrónico</label>
                        <input type="email" className="form-control" placeholder="Ingrese su correo" required />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Contraseña</label>
                        <input type="password" className="form-control" placeholder="Ingrese su contraseña" required />
                    </div>
                    <button type="submit" className="btn btn-primary w-100 mt-3">
                        Ingresar
                    </button>
                    <div className="text-center mt-3">
                        <small>¿No tienes cuenta? <a href="#" className="text-decoration-none">Regístrate</a></small>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;
