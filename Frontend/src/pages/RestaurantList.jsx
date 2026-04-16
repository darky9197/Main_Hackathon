import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Box, Typography, TextField, Card, CardContent, CardActions, Button, Grid, Paper } from '@mui/material';
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu';
import api from '../api';

function RestaurantList() {
  const [restaurants, setRestaurants] = useState([]);
  const [search, setSearch] = useState('');

  useEffect(() => {
    api.get(`/restaurants?search=${encodeURIComponent(search)}`)
      .then(res => setRestaurants(res.data))
      .catch(err => console.error(err));
  }, [search]);

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" fontWeight="bold">Restaurants</Typography>
        <TextField 
          variant="outlined" 
          size="small" 
          placeholder="Search restaurants..." 
          value={search} 
          onChange={e => setSearch(e.target.value)} 
          sx={{ width: 300 }}
        />
      </Box>
      
      {restaurants.length === 0 ? (
        <Paper sx={{ p: 4, textAlign: 'center', backgroundColor: 'transparent' }} elevation={0}>
            <Typography variant="h6" color="text.secondary">No restaurants found</Typography>
            <Typography variant="body2" color="text.secondary">Try searching for a different name.</Typography>
        </Paper>
      ) : (
        <Grid container spacing={3}>
          {restaurants.map(rest => (
            <Grid item xs={12} sm={6} md={4} key={rest.id}>
              <Card elevation={2} sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                <CardContent sx={{ flexGrow: 1 }}>
                  <Typography variant="h6" fontWeight="bold" gutterBottom>{rest.name}</Typography>
                </CardContent>
                <CardActions sx={{ p: 2, pt: 0 }}>
                  <Button 
                    component={Link} 
                    to={`/restaurant/${rest.id}`} 
                    variant="outlined" 
                    fullWidth 
                    startIcon={<RestaurantMenuIcon />}
                  >
                    View Menu
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

export default RestaurantList;
