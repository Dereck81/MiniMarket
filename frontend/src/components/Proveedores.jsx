import { useState, useEffect } from 'react';
import {
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    IconButton, Button, Modal, Box, TextField, InputAdornment, Typography,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { ApiPath } from '../config/constants';
import ToggleOffIcon from "@mui/icons-material/ToggleOff";
import ToggleOnIcon from "@mui/icons-material/ToggleOn";
import Loading from "./Loading.jsx";

const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    p: 4,
    boxShadow: 24,
};

const Proveedores = () => {
    const { user, token } = useAuth();
    const [proveedores, setProveedores] = useState([]);
    const [search, setSearch] = useState('');
    const [selectedProveedor, setSelectedProveedor] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const [nombre, setNombre] = useState('');
    const [telefono, setTelefono] = useState('');
    const [correo, setCorreo] = useState('');
    const [direccion, setDireccion] = useState('');
    const [ruc, setRuc] = useState('');
    const [loading, setLoading] = useState(true);

    const fetchProveedores = async () => {
        setLoading(true);
        try {
            if (user?.rol.nombreRol === 'VENDEDOR' || user?.rol.nombreRol === 'ADMIN') {
                const response = await axios.get(`${ApiPath.apiBaseUrl}proveedores`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setProveedores(response.data);
            }
        } catch (error) {
            console.error('Error al obtener proveedores:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProveedores();
    }, [user, token]);

    const filteredProveedores = proveedores.filter((proveedor) =>
        proveedor.nombreProveedor.toLowerCase().includes(search.toLowerCase())
    );

    const handleOpenModal = (proveedor = null) => {
        setSelectedProveedor(proveedor);
        setNombre(proveedor ? proveedor.nombreProveedor : '');
        setTelefono(proveedor ? proveedor.telefono : '');
        setCorreo(proveedor ? proveedor.email : '');
        setDireccion(proveedor ? proveedor.direccion : '');
        setRuc(proveedor ? proveedor.ruc : '');
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    const handleSave = async () => {
        try {
            if (selectedProveedor) {
                if (user?.rol.nombreRol === 'ADMIN') {
                    await axios.put(
                        `${ApiPath.apiBaseUrl}proveedores`,
                        { id: selectedProveedor.idProveedor, nombreProveedor: nombre, telefono, email: correo, direccion, ruc },
                        {
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                        }
                    );
                }
            } else {
                await axios.post(
                    `${ApiPath.apiBaseUrl}proveedores`,
                    { nombreProveedor: nombre, telefono, email: correo, direccion, ruc },
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
            }
            fetchProveedores();
            handleCloseModal();
        } catch (error) {
            console.error('Error al guardar el proveedor:', error);
        }
    };

    const handleDelete = async (idProveedor) => {
        try {
            await axios.delete(`${ApiPath.apiBaseUrl}proveedores/${idProveedor}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            fetchProveedores();
        } catch (error) {
            console.error('Error al eliminar el proveedor:', error);
        }
    };

    const canAdd = user?.rol.nombreRol === 'ADMIN' || user?.rol.nombreRol === 'VENDEDOR';
    const canEdit = user?.rol.nombreRol === 'ADMIN';
    const canDelete = user?.rol.nombreRol === 'ADMIN';

    if (loading) {
        return <Loading message="Cargando Proveedores ..." />;
    }

    return (
        <Box sx={{ padding: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2 }}>
                <TextField
                    label="Buscar proveedores"
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
                {canAdd && (
                    <Button variant="contained" startIcon={<AddIcon />} onClick={() => handleOpenModal()}>
                    </Button>
                )}
            </Box>

            <TableContainer component={Paper}>
                <Table stickyHeader>
                    <TableHead>
                        <TableRow>
                            <TableCell>Nombre</TableCell>
                            <TableCell>Teléfono</TableCell>
                            <TableCell>Correo</TableCell>
                            <TableCell>Dirección</TableCell>
                            <TableCell>RUC</TableCell>
                            <TableCell>Estado</TableCell>
                            {canEdit || canDelete ? <TableCell>Acciones</TableCell> : null}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredProveedores.map((proveedor) => (
                            <TableRow key={proveedor.idProveedor}>
                                <TableCell>{proveedor.nombreProveedor}</TableCell>
                                <TableCell>{proveedor.telefono}</TableCell>
                                <TableCell>{proveedor.email}</TableCell>
                                <TableCell>{proveedor.direccion}</TableCell>
                                <TableCell>{proveedor.ruc}</TableCell>
                                <TableCell
                                    sx={{
                                        color: proveedor.estado ? 'green' : 'red',
                                        textAlign: 'center',
                                        fontWeight: 'bold',
                                    }}
                                >
                                    {proveedor.estado ? 'Activo' : 'Inactivo'}
                                </TableCell>
                                <TableCell>
                                    {canEdit && (
                                        <IconButton onClick={() => handleOpenModal(proveedor)}>
                                            <EditIcon />
                                        </IconButton>
                                    )}
                                    {canDelete && (
                                        <IconButton
                                            onClick={() => handleDelete(proveedor.idProveedor)}
                                            color={proveedor.estado ? 'error' : 'success'}
                                        >
                                            {proveedor.estado ? <ToggleOffIcon /> : <ToggleOnIcon />}
                                        </IconButton>
                                    )}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Modal open={openModal} onClose={handleCloseModal}>
                <Box sx={modalStyle}>
                    <Typography variant="h6">{selectedProveedor ? 'Editar Proveedor' : 'Agregar Proveedor'}</Typography>
                    <TextField
                        fullWidth
                        label="Nombre"
                        variant="outlined"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <TextField
                        fullWidth
                        label="Teléfono"
                        variant="outlined"
                        value={telefono}
                        onChange={(e) => setTelefono(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <TextField
                        fullWidth
                        label="Correo"
                        variant="outlined"
                        value={correo}
                        onChange={(e) => setCorreo(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <TextField
                        fullWidth
                        label="Dirección"
                        variant="outlined"
                        value={direccion}
                        onChange={(e) => setDireccion(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <TextField
                        fullWidth
                        label="RUC"
                        variant="outlined"
                        value={ruc}
                        onChange={(e) => setRuc(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <Button variant="contained" onClick={handleSave}>
                        {selectedProveedor ? 'Guardar Cambios' : 'Agregar'}
                    </Button>
                </Box>
            </Modal>
        </Box>
    );
};

export default Proveedores;