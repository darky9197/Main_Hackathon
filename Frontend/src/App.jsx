import React, { useState } from 'react';
import { Link as RouterLink, useNavigate, Outlet } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button, Container, Box, Chip, Badge, CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import RestaurantIcon from '@mui/icons-material/Restaurant';

const theme = createTheme({
  palette: {
    primary: { main: '#ff5722' },
    secondary: { main: '#2196f3' },
    background: { default: '#f8fafc' }
  },
  typography: {
    fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
  }
});

function App() {
  const [token, setToken] = useState(localStorage.getItem('token') || '');
  const [userId, setUserId] = useState(localStorage.getItem('userId') || '');
  const [role, setRole] = useState(localStorage.getItem('role') || 'USER');
  const [cart, setCart] = useState([]);
  const navigate = useNavigate();

  const handleLogin = (t, uid, r) => {
    setToken(t);
    setUserId(uid);
    setRole(r);
    localStorage.setItem('token', t);
    localStorage.setItem('userId', uid);
    localStorage.setItem('role', r);
  };

  const handleLogout = () => {
    setToken('');
    setUserId('');
    setRole('USER');
    setCart([]);
    localStorage.clear();
    navigate('/login');
  };

  const addToCart = (item) => setCart([...cart, { ...item, quantity: 1, tempId: Math.random() }]);
  const removeFromCart = (tempId) => setCart(cart.filter((c) => c.tempId !== tempId));
  const clearCart = () => setCart([]);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AppBar position="sticky" color="inherit" elevation={1} sx={{ px: 2 }}>
        <Toolbar disableGutters>
          <RestaurantIcon sx={{ color: 'primary.main', mr: 1 }} />
          <Typography variant="h6" component={RouterLink} to="/" sx={{ flexGrow: 1, textDecoration: 'none', color: 'text.primary', fontWeight: 'bold' }}>
            FoodAnt
          </Typography>

          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            <Button component={RouterLink} to="/" color="inherit">Restaurants</Button>
            {token ? (
              <>
                <Button component={RouterLink} to="/cart" color="inherit" startIcon={
                   <Badge badgeContent={cart.length} color="primary"><ShoppingCartIcon /></Badge>
                }>
                  Cart
                </Button>
                <Button component={RouterLink} to="/orders" color="inherit">Orders</Button>
                <Chip label={role} color={role === 'ADMIN' ? 'secondary' : 'default'} size="small" sx={{ fontWeight: 'bold' }} />
                <Button onClick={handleLogout} variant="outlined" color="inherit" size="small">Logout</Button>
              </>
            ) : (
              <>
                 <Button component={RouterLink} to="/login" color="inherit">Login</Button>
                 <Button component={RouterLink} to="/register" variant="contained" color="primary" disableElevation>Register</Button>
              </>
            )}
          </Box>
        </Toolbar>
      </AppBar>
      
      <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
        <Outlet context={{ token, userId, role, cart, handleLogin, handleLogout, addToCart, removeFromCart, clearCart }} />
      </Container>
    </ThemeProvider>
  );
}

export default App;

