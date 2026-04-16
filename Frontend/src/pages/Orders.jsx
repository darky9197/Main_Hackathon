import React, { useState, useEffect } from 'react';
import { Box, Typography, Card, CardContent, Button, Paper, Chip, Divider, Stack } from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import ReceiptLongIcon from '@mui/icons-material/ReceiptLong';
import api from '../api';

function Orders({ token, userId }) {
  const [orders, setOrders] = useState([]);
  const role = localStorage.getItem('role') || 'USER';

  const fetchOrders = () => {
    const url = role === 'ADMIN' 
        ? '/orders/all' 
        : `/orders/user/${userId}`;

    api.get(url)
      .then(res => setOrders(res.data))
      .catch(err => console.log(err));
  };

  useEffect(() => {
    if(token) fetchOrders();
  }, [token, userId, role]);

  const updateStatus = (orderId, newStatus) => {
    api.put(`/orders/${orderId}/status`, { status: newStatus })
    .then(res => fetchOrders())
    .catch(err => alert('Failed to update status'));
  };

  const cancelOrder = (orderId) => {
    api.put(`/orders/${orderId}/cancel`)
    .then(res => fetchOrders())
    .catch(err => alert('Failed to cancel order'));
  };

  const getStatusColor = (status) => {
      switch(status) {
          case 'CONFIRMED': return 'success';
          case 'REJECTED': return 'error';
          case 'CANCELLED': return 'default';
          case 'PENDING':
          default: return 'warning';
      }
  };

  return (
    <Box sx={{ maxWidth: 800, mx: 'auto', mt: 4 }}>
      <Typography variant="h4" fontWeight="bold" gutterBottom>
        {role === 'ADMIN' ? 'Manage All Orders' : 'My Orders'}
      </Typography>
      
      {orders.length === 0 ? (
        <Paper elevation={1} sx={{ p: 5, textAlign: 'center', mt: 4, borderRadius: 2 }}>
            <ReceiptLongIcon sx={{ fontSize: 60, color: 'text.disabled', mb: 2 }} />
            <Typography variant="h5" color="text.secondary">No past orders</Typography>
            <Typography variant="body1" color="text.secondary">You haven't placed any orders yet.</Typography>
        </Paper>
      ) : (
        <Stack spacing={3} sx={{ mt: 3 }}>
          {orders.map(order => {
             const currentStatus = order.status || 'PENDING';
             return (
               <Card key={order.id} elevation={2} sx={{ borderLeft: 6, borderColor: `${getStatusColor(currentStatus)}.main` }}>
                 <CardContent>
                   <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                     <Typography variant="h6" fontWeight="bold">Order #{order.id}</Typography>
                     
                     <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                       <Chip 
                          label={currentStatus} 
                          color={getStatusColor(currentStatus)} 
                          size="small" 
                          fontWeight="bold" 
                       />
                       
                       {role === 'ADMIN' && currentStatus === 'PENDING' && (
                           <Box sx={{ display: 'flex', gap: 1 }}>
                               <Button size="small" variant="contained" color="success" disableElevation startIcon={<CheckCircleIcon/>} onClick={() => updateStatus(order.id, 'CONFIRMED')}>Confirm</Button>
                               <Button size="small" variant="contained" color="error" disableElevation startIcon={<CancelIcon/>} onClick={() => updateStatus(order.id, 'REJECTED')}>Reject</Button>
                           </Box>
                       )}
                       
                       {role === 'USER' && currentStatus === 'PENDING' && (
                           <Button size="small" variant="outlined" color="error" onClick={() => cancelOrder(order.id)}>Cancel Order</Button>
                       )}
                     </Box>
                   </Box>
                   
                   <Divider sx={{ mb: 2 }} />
                   
                   <Box component="ul" sx={{ pl: 2, m: 0, color: 'text.secondary' }}>
                       {order.items.map(i => (
                           <Typography component="li" key={i.id} variant="body2" sx={{ mb: 1 }}>
                               {i.menuItem.name} <Box component="span" fontWeight="bold">x{i.quantity}</Box>
                           </Typography>
                       ))}
                   </Box>
                   
                   <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2, pt: 2, borderTop: '1px dashed #ccc' }}>
                       <Typography variant="subtitle1" fontWeight="bold" color="text.primary">Total Amount</Typography>
                       <Typography variant="subtitle1" fontWeight="bold" color="primary">₹{order.totalAmount ? order.totalAmount.toFixed(2) : '0.00'}</Typography>
                   </Box>
                 </CardContent>
               </Card>
             );
          })}
        </Stack>
      )}
    </Box>
  );
}
export default Orders;
