import { useState } from 'react';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import {
  AppBar, Toolbar, Typography, IconButton, Box, Avatar, Menu, MenuItem,
  ListItemIcon, Divider, Container, ToggleButtonGroup, ToggleButton, Button,
} from '@mui/material';
import LocalGasStationIcon from '@mui/icons-material/LocalGasStation';
import PersonIcon from '@mui/icons-material/Person';
import LogoutIcon from '@mui/icons-material/Logout';
import { useAuth } from '../../context/AuthContext';
import { useLang } from '../../context/LanguageContext';

export default function AppLayout() {
  const { user, logout, hasRole } = useAuth();
  const { lang, switchLang, t } = useLang();
  const navigate = useNavigate();
  const location = useLocation();
  const [anchorEl, setAnchorEl] = useState(null);

  const nav = [
    { key: 'nav_dashboard', path: '/', roles: ['ADMIN', 'WORKER', 'CUSTOMER'] },
    { key: 'nav_stations', path: '/stations', roles: ['ADMIN'] },
    { key: 'nav_gas_types', path: '/gas-types', roles: ['ADMIN'] },
    { key: 'nav_pumps', path: '/pumps', roles: ['ADMIN'] },
    { key: 'nav_coupons', path: '/coupons', roles: ['ADMIN', 'WORKER'] },
    { key: 'nav_users', path: '/users', roles: ['ADMIN'] },
  ].filter(item => hasRole(...item.roles));

  const initials = user?.fullName
    ? user.fullName.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2)
    : user?.email?.[0]?.toUpperCase() || 'U';

  const handleLogout = () => { setAnchorEl(null); logout(); navigate('/login'); };

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: 'background.default', display: 'flex', flexDirection: 'column' }}>
      <AppBar position="sticky">
        <Container maxWidth="lg">
          <Toolbar disableGutters sx={{ gap: 0.5, minHeight: 56 }}>
            <Box onClick={() => navigate('/')} sx={{ display: 'flex', alignItems: 'center', gap: 1, cursor: 'pointer', mr: 2 }}>
              <Box sx={{ width: 30, height: 30, borderRadius: '7px', bgcolor: 'primary.main',
                display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <LocalGasStationIcon sx={{ fontSize: 18, color: '#fff' }} />
              </Box>
              <Typography fontWeight={700} fontSize="0.95rem">{t('app_name')}</Typography>
            </Box>

            <Box sx={{ display: 'flex', gap: 0.25, flexWrap: 'wrap' }}>
              {nav.map(item => {
                const active = item.path === '/' ? location.pathname === '/' : location.pathname.startsWith(item.path);
                return (
                  <Button key={item.path} onClick={() => navigate(item.path)} size="small"
                    sx={{ color: active ? 'primary.main' : 'text.secondary',
                      bgcolor: active ? '#EBF2FF' : 'transparent', fontWeight: 600, fontSize: '0.8rem' }}>
                    {t(item.key)}
                  </Button>
                );
              })}
            </Box>

            <Box sx={{ flex: 1 }} />

            <ToggleButtonGroup value={lang} exclusive onChange={(_, val) => val && switchLang(val)} size="small"
              sx={{ height: 30, '& .MuiToggleButton-root': { px: 1.25, py: 0, fontSize: '0.72rem', fontWeight: 700,
                '&.Mui-selected': { bgcolor: 'primary.main', color: '#fff', '&:hover': { bgcolor: 'primary.dark' } } } }}>
              <ToggleButton value="en">EN</ToggleButton>
              <ToggleButton value="uk">UK</ToggleButton>
            </ToggleButtonGroup>

            <IconButton onClick={(e) => setAnchorEl(e.currentTarget)} size="small" sx={{ p: 0, ml: 0.5 }}>
              <Avatar sx={{ width: 32, height: 32, fontSize: '0.8rem', fontWeight: 700, bgcolor: 'primary.main', color: '#fff' }}>
                {initials}
              </Avatar>
            </IconButton>
            <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={() => setAnchorEl(null)}
              transformOrigin={{ horizontal: 'right', vertical: 'top' }}
              anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}>
              <Box sx={{ px: 2, py: 1.5 }}>
                <Typography variant="subtitle2" fontWeight={700} noWrap>{user?.fullName || 'User'}</Typography>
                <Typography variant="caption" color="text.secondary" noWrap>{user?.email} · {user?.role}</Typography>
              </Box>
              <Divider />
              <MenuItem onClick={() => { setAnchorEl(null); navigate('/profile'); }} sx={{ gap: 1.5, py: 1 }}>
                <ListItemIcon sx={{ minWidth: 'auto' }}><PersonIcon fontSize="small" /></ListItemIcon>
                <Typography variant="body2">{t('nav_profile')}</Typography>
              </MenuItem>
              <Divider />
              <MenuItem onClick={handleLogout} sx={{ gap: 1.5, py: 1 }}>
                <ListItemIcon sx={{ minWidth: 'auto', color: 'error.main' }}><LogoutIcon fontSize="small" /></ListItemIcon>
                <Typography variant="body2" color="error.main">{t('nav_signout')}</Typography>
              </MenuItem>
            </Menu>
          </Toolbar>
        </Container>
      </AppBar>
      <Container maxWidth="lg" sx={{ py: 4, flex: 1 }}>
        <Outlet />
      </Container>
    </Box>
  );
}
