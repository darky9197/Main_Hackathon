import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Paper, Typography, TextField, Button, Box, Alert } from '@mui/material';
import api from '../api';

function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    api.post('/auth/login', { email, password })
    .then(res => {
      setLoading(false);
      onLogin(res.data.token, res.data.userId, res.data.role);
      navigate('/');
    })
    .catch(err => {
      setLoading(false);
      setError(err.response?.data?.message || 'Invalid credentials');
    });
  };

  return (
    <Paper elevation={2} sx={{ maxWidth: 400, mx: 'auto', mt: 8, p: 4, borderRadius: 2 }}>
      <Typography variant="h5" fontWeight="bold" gutterBottom textAlign="center">
        Welcome Back
      </Typography>
      
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
      
      <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
        <TextField 
          label="Email Address" 
          type="email" 
          value={email} 
          onChange={e => {setEmail(e.target.value); setError('');}} 
          required 
          fullWidth
          variant="outlined" 
        />
        <TextField 
          label="Password" 
          type="password" 
          value={password} 
          onChange={e => {setPassword(e.target.value); setError('');}} 
          required 
          fullWidth
          variant="outlined" 
        />
        <Button type="submit" variant="contained" color="primary" size="large" fullWidth disableElevation disabled={loading} sx={{ mt: 1 }}>
          {loading ? 'Signing In...' : 'Sign In'}
        </Button>
      </Box>
    </Paper>
  );
}
export default Login;
