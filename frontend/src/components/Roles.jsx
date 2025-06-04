import React, { useState, useEffect } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    IconButton,
    Button,
    Modal,
    Box,
    TextField,
    InputAdornment,
    Typography,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { ApiPath } from '../config/constants';
import { useNavigate } from 'react-router-dom';
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

const Roles = () => {
    const { user, token } = useAuth();
    const navigate = useNavigate();
    const [roles, setRoles] = useState([]);
    const [search, setSearch] = useState('');
    const [selectedRole, setSelectedRole] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const [nombre, setNombre] = useState('');
    const [loading, setLoading] = useState(true);

    const fetchRoles = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${ApiPath.apiBaseUrl}roles`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setRoles(response.data);
        } catch (error) {
            if (error.response?.status === 403) {
                navigate('/');
            }
            console.error('Error al obtener roles:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRoles();
    }, []);

    const filteredRoles = roles.filter((role) =>
        role.nombreRol.toLowerCase().includes(search.toLowerCase())
    );

    const handleOpenModal = (role = null) => {
        setSelectedRole(role);
        setNombre(role ? role.nombreRol : '');
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    const handleSave = async () => {
        try {
            if (selectedRole) {
                await axios.put(
                    `${ApiPath.apiBaseUrl}roles`,
                    {
                        id: selectedRole.id,
                        nombreRol: nombre,
                    },
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
            } else {
                await axios.post(
                    `${ApiPath.apiBaseUrl}roles`,
                    { nombreRol: nombre },
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
            }
            fetchRoles();
            handleCloseModal();
        } catch (error) {
            console.error('Error al guardar el rol:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`${ApiPath.apiBaseUrl}roles/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            fetchRoles();
        } catch (error) {
            console.error('Error al eliminar el rol:', error);
        }
    };

    const canAdd = user?.rol.nombreRol === 'ADMIN';
    const canEdit = user?.rol.nombreRol === 'ADMIN';
    const canDelete = user?.rol.nombreRol === 'ADMIN';

    if (loading) {
        return <Loading message="Cargando Roles ..." />;
    }

    return (
        <Box sx={{ padding: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2 }}>
                <TextField
                    label="Buscar roles"
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
                            <TableCell>Estado</TableCell>
                            {canEdit || canDelete ? <TableCell>Acciones</TableCell> : null}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredRoles.map((role) => (
                            <TableRow key={role.id}>
                                <TableCell>{role.nombreRol}</TableCell>
                                <TableCell
                                    sx={{
                                        color: role.estado ? 'green' : 'red',
                                        textAlign: 'center',
                                        fontWeight: 'bold',
                                    }}
                                >
                                    {role.estado ? 'Activo' : 'Inactivo'}
                                </TableCell>
                                <TableCell>
                                    {canEdit && (
                                        <IconButton onClick={() => handleOpenModal(role)}>
                                            <EditIcon />
                                        </IconButton>
                                    )}
                                    {canDelete && (
                                        <IconButton
                                            onClick={() => handleDelete(role.id)}
                                            color={role.estado ? 'error' : 'success'}
                                        >
                                            {role.estado ? <ToggleOffIcon /> : <ToggleOnIcon />}
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
                    <Typography variant="h6">{selectedRole ? 'Editar Rol' : 'Agregar Rol'}</Typography>
                    <TextField
                        fullWidth
                        label="Nombre"
                        variant="outlined"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <Button variant="contained" onClick={handleSave}>
                        {selectedRole ? 'Guardar Cambios' : 'Agregar'}
                    </Button>
                </Box>
            </Modal>
        </Box>
    );
};

export default Roles;