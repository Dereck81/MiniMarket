import React from 'react';
import { Box, Typography, Paper } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import ImgUs from '../img/unDraw_us.svg';

const Nosotros: React.FC = () => {
  const theme = useTheme();

  return (
    <Box
      sx={{
        padding: theme.spacing(6),
        backgroundColor: theme.palette.background.default,
        minHeight: 'auto',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <Box
        sx={{
          position: 'relative',
          padding: theme.spacing(6),
          display: 'flex',
          flexDirection: { xs: 'column', md: 'row' },
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: theme.palette.background.default,
          borderRadius: theme.shape.borderRadius * 2,
          boxShadow: '0px 10px 20px rgba(0, 0, 0, 0.15)',
          maxWidth: '1000px',
        }}
      >
        <Box
          sx={{
            flex: { xs: '1', md: '0 0 50%' },
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            marginBottom: { xs: theme.spacing(4), md: 0 },
            marginRight: { md: theme.spacing(4) },
          }}
        >
          <img
            src={ImgUs}
            alt="Sobre nosotros"
            style={{
              width: '100%',
              maxWidth: '400px',
              height: 'auto',
              borderRadius: '10px',
              boxShadow: '0px 10px 20px rgba(0, 0, 0, 0.2)',
            }}
          />
        </Box>

        <Paper
          elevation={6}
          sx={{
            flex: { xs: '1', md: '0 0 50%' },
            padding: { xs: theme.spacing(3), md: theme.spacing(5) },
            backgroundColor: theme.palette.background.paper,
            borderRadius: theme.shape.borderRadius * 2,
            width: '100%',
            textAlign: 'left',
            boxShadow: '0px 10px 20px rgba(0, 0, 0, 0.15)',
            color: theme.palette.text.primary,
          }}
        >
          <Typography
            variant="h4"
            gutterBottom
            sx={{
              color: theme.palette.primary.main,
              fontWeight: theme.typography.fontWeightBold,
              fontSize: { xs: '1.75rem', md: '2rem' },
              textTransform: 'uppercase',
              textAlign: 'center',
            }}
          >
            Nosotros
          </Typography>

          <Typography
            variant="body2"
            paragraph
            sx={{
              marginBottom: theme.spacing(3),
              lineHeight: '1.6',
              fontSize: { xs: '0.95rem', md: '1rem' },
              textAlign: 'justify',
            }}
          >
            En MiniMarket, nos esforzamos por ofrecer los mejores productos a nuestros clientes con la mejor calidad y el mejor precio.
          </Typography>

          <Typography
            variant="body2"
            paragraph
            sx={{
              lineHeight: '1.6',
              fontSize: { xs: '0.95rem', md: '1rem' },
              textAlign: 'justify',
            }}
          >
            Nos dedicamos a proporcionar una experiencia de compra rápida y fácil para todos, buscando siempre satisfacer las necesidades del día a día.
          </Typography>
        </Paper>
      </Box>
    </Box>
  );
};

export default Nosotros;
