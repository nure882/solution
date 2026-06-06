import { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { Box, Card, CardContent, TextField, Button, Typography, Link, Stack } from '@mui/material';
import LocalGasStationIcon from '@mui/icons-material/LocalGasStation';
import { useAuth } from '../context/AuthContext';
import { useLang } from '../context/LanguageContext';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

export default function LoginPage() {
  const { loginUser } = useAuth();
  const { t } = useLang();
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    if (!form.email || !form.password) { setError(t('auth_error_empty')); return; }
    setLoading(true);
    try {
      await loginUser(form.email, form.password);
      navigate('/');
    } catch {
      setError(t('auth_error_invalid'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', p: 2 }}>
      <Card sx={{ maxWidth: 400, width: '100%' }}>
        <CardContent sx={{ p: 4 }}>
          <Stack alignItems="center" spacing={1} mb={3}>
            <Box sx={{ width: 44, height: 44, borderRadius: 2, bgcolor: 'primary.main',
              display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
              <LocalGasStationIcon sx={{ color: '#fff' }} />
            </Box>
            <Typography variant="h5">{t('auth_signin_title')}</Typography>
            <Typography variant="body2" color="text.secondary" textAlign="center">{t('auth_signin_subtitle')}</Typography>
          </Stack>
          <form onSubmit={submit}>
            <Stack spacing={2}>
              <TextField label={t('auth_email')} fullWidth value={form.email}
                onChange={(e) => setForm({ ...form, email: e.target.value })} />
              <TextField label={t('auth_password')} type="password" fullWidth value={form.password}
                onChange={(e) => setForm({ ...form, password: e.target.value })} />
              <Button type="submit" variant="contained" size="large" disabled={loading}>{t('auth_signin_btn')}</Button>
            </Stack>
          </form>
          <Typography variant="body2" textAlign="center" mt={2} color="text.secondary">
            {t('auth_new_here')} <Link component={RouterLink} to="/register">{t('auth_create_account')}</Link>
          </Typography>
        </CardContent>
      </Card>
      <ErrorSnackbar message={error} onClose={() => setError('')} />
    </Box>
  );
}
