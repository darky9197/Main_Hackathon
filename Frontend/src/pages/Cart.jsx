import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Typography, Button, Paper, Alert, List, ListItem, ListItemText, ListItemSecondaryAction, IconButton, Divider } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import api from '../api';

function Cart({ cart, removeFromCart, clearCart }) {
  const navigate = useNavigate();
  const [message, setMessage] = useState(null);
  const [loading, setLoading] = useState(false);
  const totalAmount = cart.reduce((sum, item) => sum + item.price, 0);

  const placeOrder = () => {
    if (cart.length === 0) return;
    setLoading(true);
    
    const orderItems = cart.map(item => ({
        menuItem: { id: item.id },
        quantity: 1
    }));

    api.post('/orders', { totalAmount: totalAmount, items: orderItems })
    .then(res => {
        setLoading(false);
        setMessage({ type: 'success', text: 'Order placed successfully! Redirecting...' });
        clearCart();
        setTimeout(() => navigate('/orders'), 2000);
    }).catch(err => {
        setLoading(false);
        setMessage({ type: 'error', text: 'Failed to place order. Please try again.' });
    });
  };

  if (cart.length === 0) {
    return (
        <Paper elevation={1} sx={{ p: 5, textAlign: 'center', mt: 4, borderRadius: 2 }}>
            <ShoppingBagIcon sx={{ fontSize: 60, color: 'text.disabled', mb: 2 }} />
            <Typography variant="h5" color="text.secondary" gutterBottom>Your cart is empty</Typography>
            <Button variant="outlined" color="primary" onClick={() => navigate('/')} sx={{ mt: 2 }}>
                Browse Restaurants
            </Button>
        </Paper>
    );
  }

  return (
    <Box sx={{ maxWidth: 600, mx: 'auto', mt: 4 }}>
      <Typography variant="h4" fontWeight="bold" gutterBottom>Your Cart</Typography>
      
      {message && <Alert severity={message.type} sx={{ mb: 3 }}>{message.text}</Alert>}

      <Paper elevation={2} sx={{ borderRadius: 2 }}>
        <List disablePadding>
          {cart.map((item, index) => (
            <React.Fragment key={item.tempId}>
              <ListItem>
                <ListItemText 
                  primary={<Typography variant="subtitle1" fontWeight="600">{item.name}</Typography>} 
                  secondary={`₹${item.price.toFixed(2)}`} 
                />
                <ListItemSecondaryAction>
                  <IconButton edge="end" aria-label="delete" color="error" onClick={() => removeFromCart(item.tempId)}>
                    <DeleteIcon />
                  </IconButton>
                </ListItemSecondaryAction>
              </ListItem>
              {index < cart.length - 1 && <Divider />}
            </React.Fragment>
          ))}
        </List>
        <Divider sx={{ borderBottomWidth: 2 }} />
        <Box sx={{ display: 'flex', justifyContent: 'space-between', p: 3, bgcolor: 'background.default' }}>
          <Typography variant="h6" fontWeight="bold">Total Amount</Typography>
          <Typography variant="h6" fontWeight="bold" color="primary">₹{totalAmount.toFixed(2)}</Typography>
        </Box>
      </Paper>

      <Box sx={{ display: 'flex', gap: 2, mt: 3 }}>
         <Button variant="outlined" color="error" onClick={clearCart} fullWidth disabled={loading}>
            Clear Cart
         </Button>
         <Button variant="contained" color="primary" disableElevation onClick={placeOrder} fullWidth disabled={loading}>
            {loading ? 'Processing...' : 'Place Order'}
         </Button>
      </Box>
    </Box>
  );
}

export default Cart;
