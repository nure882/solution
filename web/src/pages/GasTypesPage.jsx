import { useEffect, useState, useCallback } from 'react';
import {
  Box, Card, Table, TableHead, TableRow, TableCell, TableBody, IconButton,
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Stack, Tooltip,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import * as api from '../api/gasStationApi';
import { useLang } from '../context/LanguageContext';
import PageHeader from '../components/common/PageHeader';
import ConfirmDialog from '../components/common/ConfirmDialog';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

export default function GasTypesPage() {
  const { t } = useLang();
  const [rows, setRows] = useState([]);
  const [dialog, setDialog] = useState(null);
  const [toDelete, setToDelete] = useState(null);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    try { setRows((await api.getGasTypes()).data); } catch { setError('Failed to load fuel types'); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const save = async () => {
    try {
      if (dialog.mode === 'create') await api.createGasType(dialog.name);
      else await api.updateGasType(dialog.gasTypeId, dialog.name);
      setDialog(null); load();
    } catch { setError('Failed to save fuel type'); }
  };
  const remove = async () => {
    try { await api.deleteGasType(toDelete.gasTypeId); setToDelete(null); load(); }
    catch { setError('Cannot delete: fuel type is used by pumps or coupons'); }
  };

  return (
    <Box>
      <PageHeader title={t('gas_types_title')} actionLabel={t('gas_type_new')}
        onAction={() => setDialog({ mode: 'create', name: '' })} />
      <Card>
        <Table>
          <TableHead><TableRow><TableCell>{t('gas_type_name')}</TableCell><TableCell align="right">{t('common_actions')}</TableCell></TableRow></TableHead>
          <TableBody>
            {rows.map(r => (
              <TableRow key={r.gasTypeId} hover>
                <TableCell>{r.name}</TableCell>
                <TableCell align="right">
                  <Tooltip title={t('common_edit')}><IconButton size="small" onClick={() => setDialog({ mode: 'edit', gasTypeId: r.gasTypeId, name: r.name })}><EditIcon fontSize="small" /></IconButton></Tooltip>
                  <Tooltip title={t('common_delete')}><IconButton size="small" color="error" onClick={() => setToDelete(r)}><DeleteIcon fontSize="small" /></IconButton></Tooltip>
                </TableCell>
              </TableRow>
            ))}
            {rows.length === 0 && <TableRow><TableCell colSpan={2} align="center" sx={{ py: 4, color: 'text.secondary' }}>{t('common_empty')}</TableCell></TableRow>}
          </TableBody>
        </Table>
      </Card>

      <Dialog open={Boolean(dialog)} onClose={() => setDialog(null)} maxWidth="xs" fullWidth>
        <DialogTitle>{dialog?.mode === 'create' ? t('gas_type_new') : t('gas_type_edit')}</DialogTitle>
        <DialogContent>
          {dialog && <Stack mt={1}><TextField label={t('gas_type_name')} value={dialog.name} onChange={(e) => setDialog({ ...dialog, name: e.target.value })} /></Stack>}
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
