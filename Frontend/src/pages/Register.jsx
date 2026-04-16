import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Paper, Typography, TextField, Button, Box, Alert } from '@mui/material';
import api from '../api';

function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    api.post('/auth/register', { name, email, password })
    .then(res => {
        setLoading(false);
        setMessage({ type: 'success', text: "Registered successfully! Redirecting to login..." });
        setTimeout(() => navigate('/login'), 2000);
    })
    .catch(err => {
        setLoading(false);
        setMessage({ type: 'error', text: err.response?.data || 'Registration failed' });
    });
  };

  return (
    <Paper elevation={2} sx={{ maxWidth: 400, mx: 'auto', mt: 8, p: 4, borderRadius: 2 }}>
      <Typography variant="h5" fontWeight="bold" gutterBottom textAlign="center">
        Create Account
      </Typography>
      
      {message && <Alert severity={message.type} sx={{ mb: 2 }}>{message.text}</Alert>}
      
      <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
        <TextField 
          label="Full Name" 
          value={name} 
          onChange={e => {setName(e.target.value); setMessage(null);}} 
          required 
          fullWidth
          variant="outlined" 
        />
        <TextField 
          label="Email Address" 
          type="email" 
          value={email} 
          onChange={e => {setEmail(e.target.value); setMessage(null);}} 
          required 
          fullWidth
          variant="outlined" 
        />
        <TextField 
          label="Password" 
          type="password" 
          value={password} 
          onChange={e => {setPassword(e.target.value); setMessage(null);}} 
          required 
          fullWidth
          variant="outlined" 
        />
        <Button type="submit" variant="contained" color="primary" size="large" fullWidth disableElevation disabled={loading} sx={{ mt: 1 }}>
          {loading ? 'Creating...' : 'Register'}
        </Button>
      </Box>
    </Paper>
  );
}
export default Register;
