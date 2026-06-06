import { useNavigate } from 'react-router-dom';
import { Box, Card, CardContent, Typography, Chip } from '@mui/material';
import LocalGasStationIcon from '@mui/icons-material/LocalGasStation';
import OilBarrelIcon from '@mui/icons-material/OilBarrel';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import PeopleIcon from '@mui/icons-material/People';
import { useAuth } from '../context/AuthContext';
import { useLang } from '../context/LanguageContext';

export default function DashboardPage() {
  const { user, hasRole } = useAuth();
  const { t } = useLang();
  const navigate = useNavigate();

  const cards = [
    { key: 'nav_stations', path: '/stations', icon: <LocalGasStationIcon />, roles: ['ADMIN'] },
    { key: 'nav_gas_types', path: '/gas-types', icon: <OilBarrelIcon />, roles: ['ADMIN'] },
    { key: 'nav_pumps', path: '/pumps', icon: <LocalGasStationIcon />, roles: ['ADMIN'] },
    { key: 'nav_coupons', path: '/coupons', icon: <LocalOfferIcon />, roles: ['ADMIN', 'WORKER'] },
    { key: 'nav_users', path: '/users', icon: <PeopleIcon />, roles: ['ADMIN'] },
  ].filter(c => hasRole(...c.roles));

  return (
    <Box>
      <Typography variant="h5" mb={0.5}>{t('dash_title')}</Typography>
      <Typography variant="body2" color="text.secondary" mb={3}>
        {t('dash_welcome')}, {user?.fullName} · <Chip size="small" label={user?.role} color="primary" />
      </Typography>
      <Box sx={{ display: 'grid', gridTemplateColumns: { xs: '1fr', sm: '1fr 1fr', md: '1fr 1fr 1fr' }, gap: 2 }}>
        {cards.map(c => (
          <Card key={c.path} onClick={() => navigate(c.path)} sx={{ cursor: 'pointer' }}>
            <CardContent sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
              <Box sx={{ color: 'primary.main' }}>{c.icon}</Box>
              <Typography variant="h6">{t(c.key)}</Typography>
            </CardContent>
          </Card>
        ))}
      </Box>
    </Box>
  );
}
