import { useState } from 'react';
import {
    TextField,
    Button,
    CircularProgress,
    Typography,
    Avatar,
    IconButton,
    InputAdornment,
    Box,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
} from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import PropTypes from 'prop-types';

const RegisterForm = ({ onSubmit, loading, errorMessage, role, roles }) => {
    const [nombre, setNombre] = useState('');
    const [apellidos, setApellidos] = useState('');
    const [email, setEmail] = useState('');
    const [dni, setDni] = useState('');
    const [telefono, setTelefono] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [image, setImage] = useState(null);
    const [preview, setPreview] = useState(null);
    const [selectedRole, setSelectedRole] = useState(role || '');
    const [errors, setErrors] = useState({
        dniError: false,
        telefonoError: false,
        passwordError: false,
        confirmPasswordError: false,
    });

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setImage(file);

        const reader = new FileReader();
        reader.onloadend = () => {
            setPreview(reader.result);
        };
        reader.readAsDataURL(file);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const newErrors = {
            dniError: dni.length !== 8,
            telefonoError: telefono.length !== 9,
            passwordError: password !== confirmPassword,
            confirmPasswordError: password !== confirmPassword,
        };
        setErrors(newErrors);

        if (Object.values(newErrors).includes(true)) {
            return;
        }

        onSubmit({
            nombre,
            apellidos,
            email,
            dni,
            telefono,
            password,
            imagen: image,
            rolId: selectedRole,
        });
    };

    return (
        <form onSubmit={handleSubmit} style={{ maxHeight: '500px', overflowY: 'auto' }}>
            {errorMessage && <Typography color="error" sx={{ mb: 2 }}>{errorMessage}</Typography>}

            <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: 2 }}>
                <Button variant="outlined" component="label">
                    Subir Imagen
                    <input type="file" hidden onChange={handleImageChange} />
                </Button>
                {preview && (
                    <Avatar
                        alt="Previsualización"
                        src={preview}
                        sx={{ width: 80, height: 80, marginLeft: 2 }}
                    />
                )}
            </Box>

            <TextField
                fullWidth
                label="Nombre"
                variant="outlined"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
                sx={{ marginBottom: 2 }}
                required
            />

            <TextField
                fullWidth
                label="Apellidos"
                variant="outlined"
                value={apellidos}
                onChange={(e) => setApellidos(e.target.value)}
                sx={{ marginBottom: 2 }}
                required
            />

            <TextField
                fullWidth
                label="Correo Electrónico"
                variant="outlined"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                sx={{ marginBottom: 2 }}
                required
                type="email"
            />

            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2 }}>
                <TextField
                    label="DNI"
                    variant="outlined"
                    value={dni}
                    onChange={(e) => {
                        const value = e.target.value.replace(/\D/g, '').slice(0, 8); // Limita a 8 dígitos
                        setDni(value);
                    }}
                    error={errors.dniError}
                    helperText={errors.dniError ? 'El DNI debe tener exactamente 8 dígitos' : ''}
                    sx={{ marginRight: 1, width: '48%' }}
                    required
                />
                <TextField
                    label="Teléfono"
                    variant="outlined"
                    value={telefono}
                    onChange={(e) => {
                        const value = e.target.value.replace(/\D/g, '').slice(0, 9); // Limita a 9 dígitos
                        setTelefono(value);
                    }}
                    error={errors.telefonoError}
                    helperText={errors.telefonoError ? 'El teléfono debe tener exactamente 9 dígitos' : ''}
                    sx={{ marginLeft: 1, width: '48%' }}
                    required
                />
            </Box>

            <TextField
                fullWidth
                label="Contraseña"
                type={showPassword ? 'text' : 'password'}
                variant="outlined"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                sx={{ marginBottom: 2 }}
                required
                error={errors.passwordError}
                helperText={errors.passwordError ? 'Las contraseñas no coinciden' : ''}
                InputProps={{
                    endAdornment: (
                        <InputAdornment position="end">
                            <IconButton onClick={() => setShowPassword(!showPassword)}>
                                {showPassword ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                        </InputAdornment>
                    ),
                }}
            />

            <TextField
                fullWidth
                label="Confirmar Contraseña"
                type={showPassword ? 'text' : 'password'}
                variant="outlined"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                sx={{ marginBottom: 2 }}
                required
                error={errors.confirmPasswordError}
                helperText={errors.confirmPasswordError ? 'Las contraseñas no coinciden' : ''}
            />

            {roles && (
                <FormControl fullWidth sx={{ marginBottom: 2 }}>
                    <InputLabel required>Rol</InputLabel>
                    <Select
                        value={selectedRole}
                        onChange={(e) => setSelectedRole(e.target.value)}
                        required
                        variant={'filled'}
                    >
                        {roles
                            .filter(rol => rol.estado)
                            .map((roleOption) => (
                            <MenuItem key={roleOption.id} value={roleOption.id}>
                                {roleOption.nombreRol}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            )}

            <Button variant="contained" type="submit" fullWidth disabled={loading}>
                {loading ? <CircularProgress size={24} color="inherit" /> : 'Registrarse'}
            </Button>
        </form>
    );
};

RegisterForm.propTypes = {
    onSubmit: PropTypes.func.isRequired,
    loading: PropTypes.bool.isRequired,
    errorMessage: PropTypes.string,
    role: PropTypes.string,
    roles: PropTypes.array,
};

export default RegisterForm;