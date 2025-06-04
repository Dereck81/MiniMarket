import { useState, useEffect } from 'react';
import {
    Box,
    Button,
    TextField,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    InputAdornment,
    Avatar,
    Typography,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
    Modal,
    Alert,
    IconButton
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import EditIcon from '@mui/icons-material/Edit';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { ApiFiles, ApiPath } from '../config/constants';
import ToggleOffIcon from "@mui/icons-material/ToggleOff";
import ToggleOnIcon from "@mui/icons-material/ToggleOn";
import AddIcon from "@mui/icons-material/Add";
import RegisterForm from "./RegisterForm.jsx";
import Loading from "./Loading.jsx";

const Usuarios = () => {
    const { token } = useAuth();
    const [usuarios, setUsuarios] = useState([]);
    const [roles, setRoles] = useState([]);
    const [search, setSearch] = useState('');
    const [selectedRole, setSelectedRole] = useState('');
    const [openRoleModal, setOpenRoleModal] = useState(false);
    const [openRegisterModal, setOpenRegisterModal] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(true);
    const [nombre, setNombre] = useState('');
    const [email, setEmail] = useState('');
    const [dni, setDni] = useState('');
    const [telefono, setTelefono] = useState('');
    const [rol, setRol] = useState('');
    const [userId, setUserId] = useState(null);

    useEffect(() => {
        fetchUsuarios();
        fetchRoles();
    }, [token]);

    const fetchUsuarios = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${ApiPath.apiBaseUrl}usuarios`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setUsuarios(response.data);
        } catch (error) {
            console.error('Error al obtener usuarios:', error);
        } finally {
            setLoading(false);
        }
    };

    const fetchRoles = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${ApiPath.apiBaseUrl}roles`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setRoles(response.data);
        } catch (error) {
            console.error('Error al obtener roles:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenRoleModal = (usuario) => {
        setUserId(usuario.id);
        setNombre(usuario.nombre);
        setEmail(usuario.email);
        setDni(usuario.dni);
        setTelefono(usuario.telefono);
        setRol(usuario.rol.id);
        setOpenRoleModal(true);
    };

    const handleCloseRoleModal = () => {
        setOpenRoleModal(false);
    };

    const handleChangeRole = async () => {
        try {
            await axios.put(
                `${ApiPath.apiBaseUrl}usuarios/rol`,
                { idUsuario: userId, idRol: rol },
                { headers: { Authorization: `Bearer ${token}` } }
            );
            fetchUsuarios();
            handleCloseRoleModal();
        } catch (error) {
            console.error('Error al cambiar rol:', error);
            setErrorMessage("No se pudo cambiar el rol. Intente nuevamente.");
        }
    };

    const handleOpenRegisterModal = () => {
        setOpenRegisterModal(true);
    };

    const handleCloseRegisterModal = () => {
        setOpenRegisterModal(false);
    };

    const handleRegister = async ({ nombre, apellidos, email, dni, telefono, password, imagen, rolId }) => {
        setErrorMessage('');

        try {
            let imageName = 'default.png';

            if (imagen) {
                const formData = new FormData();
                formData.append('image', imagen);

                const imageResponse = await axios.post(`${ApiPath.apiBaseUrl}images`, formData, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                });
                imageName = imageResponse.data;
            }

            await axios.post(`${ApiPath.apiBaseUrl}usuarios`, {
                nombre,
                apellidos,
                email,
                dni,
                telefono,
                contrasena: password,
                rolId,
                imagen: imageName,
            });

            fetchUsuarios();
            handleCloseRegisterModal();
        } catch (error) {
            setErrorMessage('Error en el registro.');
            console.error(error);
        }
    };

    const filteredUsuarios = usuarios.filter(
        (usuario) =>
            (usuario.nombre.toLowerCase().includes(search.toLowerCase()) ||
                usuario.dni.toLowerCase().includes(search.toLowerCase())) &&
            (selectedRole === '' || usuario.rol.nombreRol === selectedRole)
    );

    const handleToggleStatus = async (usuarioId) => {
        try {
            await axios.delete(`${ApiPath.apiBaseUrl}usuarios/${usuarioId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            fetchUsuarios();
        } catch (error) {
            console.error('Error al cambiar el estado del método de pago:', error);
        }
    }

    if (loading) {
        return <Loading message="Cargando Usuarios ..." />;
    }

    return (
        <Box sx={{ padding: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2 }}>
                <TextField
                    label="Buscar por nombre o DNI"
                    variant="outlined"
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <SearchIcon />
                            </InputAdornment>
                        ),
                    }}
                />
                <FormControl variant="outlined" sx={{ minWidth: 150 }}>
                    <InputLabel>Filtrar por rol</InputLabel>
                    <Select
                        value={selectedRole}
                        onChange={(e) => setSelectedRole(e.target.value)}
                        label="Filtrar por rol"
                        variant={'filled'}
                    >
                        <MenuItem value="">Todos</MenuItem>
                        {roles.map((role) => (
                            <MenuItem key={role.id} value={role.nombreRol}>
                                {role.nombreRol}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <Button variant="contained" startIcon={<AddIcon />} onClick={handleOpenRegisterModal}></Button>
            </Box>

            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Imagen</TableCell>
                            <TableCell>Nombre</TableCell>
                            <TableCell>DNI</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Rol</TableCell>
                            <TableCell>Estado</TableCell>
                            <TableCell>Acciones</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredUsuarios.map((usuario) => (
                            <TableRow key={usuario.id}>
                                <TableCell>
                                    <Avatar alt={usuario.nombre} src={`${ApiFiles.apiBaseUrl}${usuario.imagen}`} />
                                </TableCell>
                                <TableCell>{usuario.nombre}</TableCell>
                                <TableCell>{usuario.dni}</TableCell>
                                <TableCell>{usuario.email || 'No disponible'}</TableCell>
                                <TableCell>{usuario.rol.nombreRol}</TableCell>
                                <TableCell
                                    sx={{
                                        color: usuario.estado ? 'green' : 'red',
                                        textAlign: 'center',
                                        fontWeight: 'bold',
                                    }}
                                >
                                    {usuario.estado ? 'Activo' : 'Inactivo'}
                                </TableCell>
                                <TableCell>
                                    <IconButton
                                        onClick={() => handleOpenRoleModal(usuario)}
                                    >
                                        <EditIcon />
                                    </IconButton>
                                    <IconButton
                                        onClick={() => handleToggleStatus(usuario.id)}
                                        color={usuario.estado ? 'error' : 'success'}
                                    >
                                        {usuario.estado ? <ToggleOffIcon /> : <ToggleOnIcon />}
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Modal open={openRoleModal} onClose={handleCloseRoleModal}>
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        p: 4,
                        borderRadius: 2,
                    }}
                >
                    <Typography variant="h6" gutterBottom>
                        Cambiar Rol de {nombre}
                    </Typography>
                    {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
                    <form>
                        <TextField
                            fullWidth
                            label="Nombre"
                            variant="outlined"
                            value={nombre}
                            disabled
                            sx={{ mb: 2 }}
                        />
                        <TextField
                            fullWidth
                            label="Email"
                            variant="outlined"
                            value={email}
                            disabled
                            sx={{ mb: 2 }}
                        />
                        <TextField
                            fullWidth
                            label="DNI"
                            variant="outlined"
                            value={dni}
                            disabled
                            sx={{ mb: 2 }}
                        />
                        <TextField
                            fullWidth
                            label="Teléfono"
                            variant="outlined"
                            value={telefono}
                            disabled
                            sx={{ mb: 2 }}
                        />

                        <FormControl sx={{ width: '100%' }}>
                            <InputLabel>Rol</InputLabel>
                            <Select
                                value={rol}
                                onChange={(e) => setRol(e.target.value)}
                                label="Rol"
                                variant={'filled'}
                            >
                                {roles.map((role) => (
                                    <MenuItem key={role.id} value={role.id}>
                                        {role.nombreRol}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>

                        <Button
                            variant="contained"
                            fullWidth
                            sx={{ mt: 2 }}
                            onClick={handleChangeRole}
                        >
                            Cambiar Rol
                        </Button>
                    </form>
                </Box>
            </Modal>
            <Modal open={openRegisterModal} onClose={handleCloseRegisterModal}>
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        p: 4,
                        borderRadius: 2,
                    }}
                >
                    <Typography variant="h6" gutterBottom>
                        Registrar Nuevo Usuario
                    </Typography>
                    {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
                    <RegisterForm
                        onSubmit={handleRegister}
                        loading={false}
                        errorMessage={errorMessage}
                        roles={roles}
                    />
                </Box>
            </Modal>
        </Box>
    );
};

export default Usuarios;