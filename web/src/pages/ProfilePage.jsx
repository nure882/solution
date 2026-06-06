import { Box, Card, CardContent, Typography, Button, Stack, Chip, Divider } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useLang } from '../context/LanguageContext';

export default function ProfilePage() {
  const { user, logout } = useAuth();
  const { t } = useLang();
  const navigate = useNavigate();

  return (
    <Box sx={{ maxWidth: 480, mx: 'auto' }}>
      <Typography variant="h5" mb={3}>{t('profile_title')}</Typography>
      <Card>
        <CardContent>
          <Stack spacing={2}>
            <Box>
              <Typography variant="caption" color="text.secondary">{t('auth_full_name')}</Typography>
              <Typography variant="body1">{user?.fullName}</Typography>
            </Box>
            <Divider />
            <Box>
              <Typography variant="caption" color="text.secondary">{t('auth_email')}</Typography>
              <Typography variant="body1">{user?.email}</Typography>
            </Box>
            <Divider />
            <Box>
              <Typography variant="caption" color="text.secondary">{t('user_role')}</Typography>
              <Box><Chip size="small" color="primary" label={user?.role} /></Box>
            </Box>
            <Divider />
            <Button color="error" variant="outlined" startIcon={<LogoutIcon />}
              onClick={() => { logout(); navigate('/login'); }}>{t('nav_signout')}</Button>
          </Stack>
        </CardContent>
      </Card>
    </Box>
  );
}
