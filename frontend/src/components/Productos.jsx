import { useState, useEffect } from 'react';
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
    Tooltip,
    Typography,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { ApiFiles, ApiPath } from '../config/constants';
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

const Productos = () => {
    const { user, token } = useAuth();
    const [productos, setProductos] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [search, setSearch] = useState('');
    const [selectedProducto, setSelectedProducto] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const [loading, setLoading] = useState(true);
    const [nombre, setNombre] = useState('');
    const [categoria, setCategoria] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [imagen, setImagen] = useState(null);
    const [preview, setPreview] = useState(null);

    const fetchProductos = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${ApiPath.apiBaseUrl}productos`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setProductos(response.data);
        } catch (error) {
            console.error('Error al obtener productos:', error);
        } finally {
            setLoading(false);
        }
    };

    const fetchCategorias = async () => {
        setLoading(true);
        try {
            const response = await axios.get(`${ApiPath.apiBaseUrl}categorias`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setCategorias(response.data);
        } catch (error) {
            console.error('Error al obtener categorías:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProductos();
        fetchCategorias();
    }, [token]);

    const filteredProductos = productos.filter((item) =>
        item.nombre.toLowerCase().includes(search.toLowerCase())
    );

    const handleOpenModal = (producto = null) => {
        setSelectedProducto(producto);
        setNombre(producto ? producto.nombre : '');
        setCategoria(producto && producto.categoria ? producto.categoria.idCategoria : '');
        setDescripcion(producto ? producto.descripcion : '');
        setImagen(null);
        setPreview(producto ? `${ApiFiles.apiBaseUrl}${producto.imagen}` : null);
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
        setPreview(null);
        setImagen(null);
        setCategoria('');
    };

    const handleSave = async () => {
        try {
            let imageUrl = null;

            if (imagen) {
                const formData = new FormData();
                formData.append('image', imagen);
                const imageResponse = await axios.post(`${ApiPath.apiBaseUrl}images`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        Authorization: `Bearer ${token}`,
                    },
                });
                imageUrl = imageResponse.data;
            }

            if (selectedProducto && selectedProducto.idProducto) {
                await axios.put(
                    `${ApiPath.apiBaseUrl}productos`,
                    {
                        id: selectedProducto.idProducto,
                        nombre,
                        descripcion,
                        imagen: imageUrl || selectedProducto.imagen,
                        idCategoria: categoria,
                    },
                    {
                        headers: { Authorization: `Bearer ${token}` },
                    }
                );
            } else {
                await axios.post(
                    `${ApiPath.apiBaseUrl}productos`,
                    {
                        nombre,
                        descripcion,
                        imagen: imageUrl,
                        idCategoria: categoria,
                    },
                    {
                        headers: { Authorization: `Bearer ${token}` },
                    }
                );
            }

            fetchProductos();
            handleCloseModal();
        } catch (error) {
            console.error('Error al guardar el producto:', error);
        }
    };

    const handleDelete = async (idProducto) => {
        try {
            await axios.delete(`${ApiPath.apiBaseUrl}productos/${idProducto}`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            fetchProductos();
        } catch (error) {
            console.error('Error al eliminar el producto:', error);
        }
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImagen(file);
            const reader = new FileReader();
            reader.onload = (event) => {
                setPreview(event.target.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const canAdd = user?.rol.nombreRol === 'ADMIN' || user?.rol.nombreRol === 'VENDEDOR';
    const canEdit = user?.rol.nombreRol === 'ADMIN';
    const canDelete = user?.rol.nombreRol === 'ADMIN';

    if (loading) {
        return <Loading message="Cargando Productos ..." />;
    }

    return (
        <Box sx={{ padding: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2 }}>
                <TextField
                    label="Buscar productos"
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
                            <TableCell>Imagen</TableCell>
                            <TableCell>Nombre</TableCell>
                            <TableCell>Categoría</TableCell>
                            <TableCell>Descripción</TableCell>
                            <TableCell>Estado</TableCell>
                            {canEdit || canDelete ? <TableCell>Acciones</TableCell> : null}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredProductos.map((item) => (
                            <TableRow key={item.idProducto}>
                                <TableCell>
                                    {item.imagen ? (
                                        <img src={`${ApiFiles.apiBaseUrl}${item.imagen}`} alt={item.nombre} style={{ width: '50px' }} />
                                    ) : (
                                        <Typography>No Imagen</Typography>
                                    )}
                                </TableCell>
                                <TableCell>{item.nombre}</TableCell>
                                <TableCell>{item.categoria?.nombre}</TableCell>
                                <TableCell>
                                    <Tooltip title={item.descripcion} arrow>
                                        <span>{item.descripcion.substring(0, 20)}...</span>
                                    </Tooltip>
                                </TableCell>
                                <TableCell
                                    sx={{
                                        color: item.estado ? 'green' : 'red',
                                        textAlign: 'center',
                                        fontWeight: 'bold',
                                    }}
                                >
                                    {item.estado ? 'Activo' : 'Inactivo'}
                                </TableCell>
                                <TableCell>
                                    {canEdit && (
                                        <IconButton onClick={() => handleOpenModal(item)}>
                                            <EditIcon />
                                        </IconButton>
                                    )}
                                    {canDelete && (
                                        <IconButton
                                            onClick={() => handleDelete(item.idProducto)}
                                            color={item.estado ? 'error' : 'success'}
                                        >
                                            {item.estado ? <ToggleOffIcon /> : <ToggleOnIcon />}
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
                    <Typography variant="h6">{selectedProducto ? 'Editar Producto' : 'Agregar Producto'}</Typography>
                    <TextField
                        fullWidth
                        label="Nombre"
                        variant="outlined"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />

                    <FormControl fullWidth sx={{ marginBottom: 2 }}>
                        <InputLabel>Categoría</InputLabel>
                        <Select
                            value={categoria || ''}
                            onChange={(e) => setCategoria(e.target.value)}
                            label="Categoría"
                            variant="filled"
                        >
                            {categorias
                                .filter(categoria => categoria.estado )
                                .map((cat) => (
                                <MenuItem key={cat.idCategoria} value={cat.idCategoria}>
                                    {cat.nombre}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <TextField
                        fullWidth
                        label="Descripción"
                        variant="outlined"
                        value={descripcion}
                        onChange={(e) => setDescripcion(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />

                    {preview && (
                        <Box sx={{ marginBottom: 2, textAlign: 'center' }}>
                            <Typography variant="subtitle1">Previsualización de Imagen:</Typography>
                            <img src={preview} alt="Previsualización" style={{ width: '100px', height: '100px' }} />
                        </Box>
                    )}

                    <Button variant="contained" component="label" sx={{ marginBottom: 2 }}>
                        Subir Imagen
                        <input type="file" hidden onChange={handleImageChange} accept="image/*" />
                    </Button>

                    <Box sx={{ textAlign: 'right' }}>
                        <Button variant="contained" onClick={handleSave}>
                            {selectedProducto ? 'Guardar Cambios' : 'Agregar'}
                        </Button>
                    </Box>
                </Box>
            </Modal>
        </Box>
    );
};

export default Productos;