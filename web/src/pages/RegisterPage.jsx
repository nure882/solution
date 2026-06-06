import { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { Box, Card, CardContent, TextField, Button, Typography, Link, Stack } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { useLang } from '../context/LanguageContext';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

export default function RegisterPage() {
  const { registerUser } = useAuth();
  const { t } = useLang();
  const navigate = useNavigate();
  const [form, setForm] = useState({ fullName: '', email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    if (!form.email || !form.password || !form.fullName) { setError(t('auth_error_empty')); return; }
    setLoading(true);
    try {
      await registerUser(form.email, form.password, form.fullName);
      navigate('/');
    } catch {
      setError(t('auth_error_register'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', p: 2 }}>
      <Card sx={{ maxWidth: 400, width: '100%' }}>
        <CardContent sx={{ p: 4 }}>
          <Stack alignItems="center" spacing={1} mb={3}>
            <Typography variant="h5">{t('auth_register_title')}</Typography>
            <Typography variant="body2" color="text.secondary" textAlign="center">{t('auth_register_subtitle')}</Typography>
          </Stack>
          <form onSubmit={submit}>
            <Stack spacing={2}>
              <TextField label={t('auth_full_name')} fullWidth value={form.fullName}
                onChange={(e) => setForm({ ...form, fullName: e.target.value })} />
              <TextField label={t('auth_email')} fullWidth value={form.email}
                onChange={(e) => setForm({ ...form, email: e.target.value })} />
              <TextField label={t('auth_password')} type="password" fullWidth value={form.password}
                onChange={(e) => setForm({ ...form, password: e.target.value })} />
              <Button type="submit" variant="contained" size="large" disabled={loading}>{t('auth_create_account')}</Button>
            </Stack>
          </form>
          <Typography variant="body2" textAlign="center" mt={2} color="text.secondary">
            {t('auth_already_have')} <Link component={RouterLink} to="/login">{t('auth_signin_btn')}</Link>
          </Typography>
        </CardContent>
      </Card>
      <ErrorSnackbar message={error} onClose={() => setError('')} />
    </Box>
  );
}
