import { useEffect, useState, useCallback } from 'react';
import {
  Box, Card, Table, TableHead, TableRow, TableCell, TableBody, IconButton, Chip,
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, MenuItem, Stack, Tooltip,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import * as api from '../api/gasStationApi';
import { useLang } from '../context/LanguageContext';
import PageHeader from '../components/common/PageHeader';
import ConfirmDialog from '../components/common/ConfirmDialog';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

const EMPTY = { customerId: '', gasTypeId: '', liters: 0, expirationDate: '' };
const toLocalInput = (iso) => (iso ? iso.slice(0, 16) : '');

export default function CouponsPage() {
  const { t } = useLang();
  const [rows, setRows] = useState([]);
  const [gasTypes, setGasTypes] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [dialog, setDialog] = useState(null);
  const [toDelete, setToDelete] = useState(null);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    try {
      const [c, g] = await Promise.all([api.getCoupons(), api.getGasTypes()]);
      setRows(c.data); setGasTypes(g.data);
      try { setCustomers((await api.getUsers('CUSTOMER')).data); } catch { /* worker has no user access */ }
    } catch { setError('Failed to load coupons'); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const save = async () => {
    try {
      const { mode, data } = dialog;
      const expiration = data.expirationDate ? new Date(data.expirationDate).toISOString() : null;
      if (mode === 'create') {
        await api.createCoupon({ customerId: data.customerId || null, gasTypeId: data.gasTypeId, liters: Number(data.liters), expirationDate: expiration });
      } else {
        await api.updateCoupon(data.couponId, { gasTypeId: data.gasTypeId, liters: Number(data.liters), active: data.active, expirationDate: expiration, useDate: data.useDate || null });
      }
      setDialog(null); load();
    } catch { setError('Failed to save coupon'); }
  };
  const remove = async () => {
    try { await api.deleteCoupon(toDelete.couponId); setToDelete(null); load(); } catch { setError('Failed to delete coupon'); }
  };

  return (
    <Box>
      <PageHeader title={t('coupons_title')} actionLabel={t('coupon_new')}
        onAction={() => setDialog({ mode: 'create', data: { ...EMPTY } })} />
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t('coupon_customer')}</TableCell>
              <TableCell>{t('coupon_gas_type')}</TableCell>
              <TableCell align="right">{t('coupon_liters')}</TableCell>
              <TableCell>{t('coupon_expiration')}</TableCell>
              <TableCell>{t('coupon_active')}</TableCell>
              <TableCell align="right">{t('common_actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map(r => (
              <TableRow key={r.couponId} hover>
                <TableCell>{r.customerEmail || '—'}</TableCell>
                <TableCell>{r.gasTypeName}</TableCell>
                <TableCell align="right">{r.liters.toFixed(1)}</TableCell>
                <TableCell>{r.expirationDate ? new Date(r.expirationDate).toLocaleDateString() : '—'}</TableCell>
                <TableCell>
                  <Chip size="small" color={r.active ? 'success' : 'default'} label={r.active ? t('coupon_active') : t('coupon_used')} />
                </TableCell>
                <TableCell align="right">
                  <Tooltip title={t('common_edit')}><IconButton size="small" onClick={() => setDialog({ mode: 'edit', data: { ...r, expirationDate: toLocalInput(r.expirationDate) } })}><EditIcon fontSize="small" /></IconButton></Tooltip>
                  <Tooltip title={t('common_delete')}><IconButton size="small" color="error" onClick={() => setToDelete(r)}><DeleteIcon fontSize="small" /></IconButton></Tooltip>
                </TableCell>
              </TableRow>
            ))}
            {rows.length === 0 && <TableRow><TableCell colSpan={6} align="center" sx={{ py: 4, color: 'text.secondary' }}>{t('common_empty')}</TableCell></TableRow>}
          </TableBody>
        </Table>
      </Card>

      <Dialog open={Boolean(dialog)} onClose={() => setDialog(null)} maxWidth="sm" fullWidth>
        <DialogTitle>{dialog?.mode === 'create' ? t('coupon_new') : t('coupon_edit')}</DialogTitle>
        <DialogContent>
          {dialog && (
            <Stack spacing={2} mt={1}>
              {dialog.mode === 'create' && (
                <TextField select label={t('coupon_customer')} value={dialog.data.customerId || ''}
                  onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, customerId: e.target.value } })}>
                  <MenuItem value="">—</MenuItem>
                  {customers.map(c => <MenuItem key={c.id} value={c.id}>{c.email}</MenuItem>)}
                </TextField>
              )}
              <TextField select label={t('coupon_gas_type')} value={dialog.data.gasTypeId || ''}
                onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, gasTypeId: e.target.value } })}>
                {gasTypes.map(g => <MenuItem key={g.gasTypeId} value={g.gasTypeId}>{g.name}</MenuItem>)}
              </TextField>
              <TextField label={t('coupon_liters')} type="number" value={dialog.data.liters}
                onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, liters: e.target.value } })} />
              <TextField label={t('coupon_expiration')} type="datetime-local" InputLabelProps={{ shrink: true }}
                value={dialog.data.expirationDate || ''} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, expirationDate: e.target.value } })} />
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
