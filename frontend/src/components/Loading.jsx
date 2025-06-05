import React from 'react';
import { Box, CircularProgress, Typography } from '@mui/material';

const Loading = ({ message = 'Cargando...' }) => {
    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                flexDirection: 'column',
                height: '100vh',
            }}
        >
            <CircularProgress />
            <Typography variant="h6" sx={{ marginTop: 2 }}>
                {message}
            </Typography>
        </Box>
    );
};

export default Loading;