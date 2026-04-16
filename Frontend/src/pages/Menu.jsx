import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, Typography, TextField, Card, CardContent, CardActions, Button, Grid, IconButton, Alert, Paper } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import api from '../api';

function Menu({ token, addToCart }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [menuItems, setMenuItems] = useState([]);
  const [search, setSearch] = useState('');
  const [message, setMessage] = useState(null);

  useEffect(() => {
    api.get(`/restaurants/${id}/menu?search=${encodeURIComponent(search)}`)
      .then(res => setMenuItems(res.data))
      .catch(err => console.error(err));
  }, [id, search]);

  const handleAdd = (item) => {
      if(!token) {
          setMessage({ type: 'error', text: 'Please login first to add items.' });
          setTimeout(() => navigate('/login'), 2000);
          return;
      }
      addToCart(item);
      setMessage({ type: 'success', text: `Added ${item.name} to cart!` });
      setTimeout(() => setMessage(null), 2000);
  }

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
            <IconButton onClick={() => navigate('/')} color="primary" aria-label="back">
              <ArrowBackIcon />
            </IconButton>
            <Typography variant="h4" fontWeight="bold">Menu</Typography>
        </Box>
        <TextField 
          variant="outlined" 
          size="small" 
          placeholder="Search menu items..." 
          value={search} 
          onChange={e => setSearch(e.target.value)} 
          sx={{ width: 300 }}
        />
      </Box>

      {message && <Alert severity={message.type} sx={{ mb: 3 }}>{message.text}</Alert>}

      {menuItems.length === 0 ? (
        <Paper sx={{ p: 4, textAlign: 'center', backgroundColor: 'transparent' }} elevation={0}>
             <Typography variant="h6" color="text.secondary">No items found</Typography>
        </Paper>
      ) : (
        <Grid container spacing={3}>
          {menuItems.map(item => (
            <Grid item xs={12} sm={6} md={4} key={item.id}>
              <Card elevation={2} sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                <CardContent sx={{ flexGrow: 1 }}>
                  <Typography variant="h6" fontWeight="600">{item.name}</Typography>
                  <Typography variant="h6" color="primary" sx={{ mt: 1 }}>₹{item.price.toFixed(2)}</Typography>
                </CardContent>
                <CardActions sx={{ p: 2, pt: 0 }}>
                  <Button 
                    variant="contained" 
                    color="primary" 
                    fullWidth 
                    disableElevation
                    startIcon={<AddShoppingCartIcon />}
                    onClick={() => handleAdd(item)}
                  >
                    Add to Cart
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
}

export default Menu;
