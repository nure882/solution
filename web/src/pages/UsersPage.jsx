import { useEffect, useState, useCallback } from 'react';
import {
  Box, Card, Table, TableHead, TableRow, TableCell, TableBody, IconButton, Chip,
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, MenuItem, Stack, Tooltip,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import BlockIcon from '@mui/icons-material/Block';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import * as api from '../api/gasStationApi';
import { useLang } from '../context/LanguageContext';
import PageHeader from '../components/common/PageHeader';
import ConfirmDialog from '../components/common/ConfirmDialog';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

const ROLES = ['ADMIN', 'WORKER', 'CUSTOMER'];
const EMPTY = { email: '', password: '', fullName: '', role: 'WORKER' };

export default function UsersPage() {
  const { t } = useLang();
  const [rows, setRows] = useState([]);
  const [roleFilter, setRoleFilter] = useState('');
  const [dialog, setDialog] = useState(null);
  const [toDelete, setToDelete] = useState(null);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    try { setRows((await api.getUsers(roleFilter || undefined)).data); } catch { setError('Failed to load users'); }
  }, [roleFilter]);
  useEffect(() => { load(); }, [load]);

  const save = async () => {
    try { await api.createUser(dialog.data); setDialog(null); load(); }
    catch { setError('Failed to create user. Email may already be in use.'); }
  };
  const toggleBan = async (u) => {
    try { await api.setUserBan(u.id, !u.banned); load(); } catch { setError('Failed to update user'); }
  };
  const remove = async () => {
    try { await api.deleteUser(toDelete.id); setToDelete(null); load(); } catch { setError('Failed to delete user'); }
  };

  return (
    <Box>
      <PageHeader title={t('users_title')} actionLabel={t('user_new')}
        onAction={() => setDialog({ data: { ...EMPTY } })} />
      <TextField select label={t('user_role')} size="small" sx={{ mb: 2, width: 220 }}
        value={roleFilter} onChange={(e) => setRoleFilter(e.target.value)}>
        <MenuItem value="">{t('common_view')} — all</MenuItem>
        {ROLES.map(r => <MenuItem key={r} value={r}>{r}</MenuItem>)}
      </TextField>
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t('user_email')}</TableCell>
              <TableCell>{t('user_name')}</TableCell>
              <TableCell>{t('user_role')}</TableCell>
              <TableCell>{t('user_status')}</TableCell>
              <TableCell align="right">{t('common_actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map(u => (
              <TableRow key={u.id} hover>
                <TableCell>{u.email}</TableCell>
                <TableCell>{u.fullName}</TableCell>
                <TableCell><Chip size="small" label={u.role} color={u.role === 'ADMIN' ? 'primary' : 'default'} /></TableCell>
                <TableCell><Chip size="small" color={u.banned ? 'error' : 'success'} label={u.banned ? t('user_banned') : t('user_active')} /></TableCell>
                <TableCell align="right">
                  <Tooltip title={u.banned ? t('user_unban') : t('user_ban')}>
                    <IconButton size="small" onClick={() => toggleBan(u)}>
                      {u.banned ? <CheckCircleIcon fontSize="small" /> : <BlockIcon fontSize="small" />}
                    </IconButton>
                  </Tooltip>
                  <Tooltip title={t('common_delete')}><IconButton size="small" color="error" onClick={() => setToDelete(u)}><DeleteIcon fontSize="small" /></IconButton></Tooltip>
                </TableCell>
              </TableRow>
            ))}
            {rows.length === 0 && <TableRow><TableCell colSpan={5} align="center" sx={{ py: 4, color: 'text.secondary' }}>{t('common_empty')}</TableCell></TableRow>}
          </TableBody>
        </Table>
      </Card>

      <Dialog open={Boolean(dialog)} onClose={() => setDialog(null)} maxWidth="xs" fullWidth>
        <DialogTitle>{t('user_new')}</DialogTitle>
        <DialogContent>
          {dialog && (
            <Stack spacing={2} mt={1}>
              <TextField label={t('user_name')} value={dialog.data.fullName} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, fullName: e.target.value } })} />
              <TextField label={t('user_email')} value={dialog.data.email} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, email: e.target.value } })} />
              <TextField label={t('user_password')} type="password" value={dialog.data.password} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, password: e.target.value } })} />
              <TextField select label={t('user_role')} value={dialog.data.role} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, role: e.target.value } })}>
                {ROLES.map(r => <MenuItem key={r} value={r}>{r}</MenuItem>)}
              </TextField>
            </Stack>
          )}
        </DialogContent>
        <DialogActions sx={{ px: 3, pb: 2 }}>
          <Button onClick={() => setDialog(null)} variant="outlined">{t('common_cancel')}</Button>
          <Button onClick={save} variant="contained">{t('common_save')}</Button>
        </DialogActions>
      </Dialog>
      <ConfirmDialog open={Boolean(toDelete)} onCancel={() => setToDelete(null)} onConfirm={remove} />
      <ErrorSnackbar message={error} onClose={() => setError('')} />
    </Box>
  );
}
