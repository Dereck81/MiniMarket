import React, { useState } from 'react';

const Contactanos: React.FC = () => {
    const [mensajeEnviado, setMensajeEnviado] = useState(false);

    const manejarEnvio = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setMensajeEnviado(true);

        // Opcional: aquí podrías también limpiar el formulario
        e.currentTarget.reset();
        
        // Opcional: ocultar el mensaje después de unos segundos
        setTimeout(() => setMensajeEnviado(false), 4000);
    }

    return (
        <div className="container mt-5">
            <div className="card shadow p-4">
                <h2 className="text-center text-primary mb-4">¡Contáctanos!</h2>

                {mensajeEnviado && (
                    <div className="alert alert-success text-center" role="alert">
                        ¡Tu mensaje fue enviado exitosamente!
                    </div>
                )}

                <form onSubmit={manejarEnvio}>
                    <div className="row mb-3">
                        <div className="col-md-6">
                            <label className="form-label">Nombre</label>
                            <input type="text" className="form-control rounded-pill" placeholder="Tu nombre" required />
                        </div>
                        <div className="col-md-6">
                            <label className="form-label">Correo Electrónico</label>
                            <input type="email" className="form-control rounded-pill" placeholder="correo@ejemplo.com" required />
                        </div>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Mensaje</label>
                        <textarea className="form-control rounded-4" rows={4} placeholder="Escribe tu mensaje aquí..." required></textarea>
                    </div>
                    <div className="d-grid">
                        <button type="submit" className="btn btn-primary btn-lg rounded-pill">
                            Enviar Mensaje
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Contactanos;
