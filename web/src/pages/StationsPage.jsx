import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box, Card, Table, TableHead, TableRow, TableCell, TableBody, IconButton,
  TextField, Dialog, DialogTitle, DialogContent, DialogActions, Button, Stack, Tooltip,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityIcon from '@mui/icons-material/Visibility';
import * as api from '../api/gasStationApi';
import { useLang } from '../context/LanguageContext';
import PageHeader from '../components/common/PageHeader';
import ConfirmDialog from '../components/common/ConfirmDialog';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

const EMPTY = { city: '', address: '', workTime: '', description: '', equipmentList: '' };

export default function StationsPage() {
  const { t } = useLang();
  const navigate = useNavigate();
  const [rows, setRows] = useState([]);
  const [search, setSearch] = useState('');
  const [dialog, setDialog] = useState(null); // { mode, data }
  const [toDelete, setToDelete] = useState(null);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    try { const res = await api.getStations(search); setRows(res.data); }
    catch { setError('Failed to load stations'); }
  }, [search]);

  useEffect(() => { load(); }, [load]);

  const save = async () => {
    try {
      const { mode, data } = dialog;
      if (mode === 'create') await api.createStation(data);
      else await api.updateStation(data.stationId, data);
      setDialog(null); load();
    } catch { setError('Failed to save station'); }
  };

  const remove = async () => {
    try { await api.deleteStation(toDelete.stationId); setToDelete(null); load(); }
    catch { setError('Failed to delete station'); }
  };

  return (
    <Box>
      <PageHeader title={t('stations_title')} actionLabel={t('station_new')}
        onAction={() => setDialog({ mode: 'create', data: { ...EMPTY } })} />
      <TextField placeholder={t('common_search')} size="small" sx={{ mb: 2, width: 280 }}
        value={search} onChange={(e) => setSearch(e.target.value)} />
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t('station_city')}</TableCell>
              <TableCell>{t('station_address')}</TableCell>
              <TableCell>{t('station_work_time')}</TableCell>
              <TableCell align="right">{t('common_actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map(r => (
              <TableRow key={r.stationId} hover>
                <TableCell>{r.city}</TableCell>
                <TableCell>{r.address}</TableCell>
                <TableCell>{r.workTime}</TableCell>
                <TableCell align="right">
                  <Tooltip title={t('common_view')}><IconButton size="small" onClick={() => navigate(`/stations/${r.stationId}`)}><VisibilityIcon fontSize="small" /></IconButton></Tooltip>
                  <Tooltip title={t('common_edit')}><IconButton size="small" onClick={() => setDialog({ mode: 'edit', data: { ...r } })}><EditIcon fontSize="small" /></IconButton></Tooltip>
                  <Tooltip title={t('common_delete')}><IconButton size="small" color="error" onClick={() => setToDelete(r)}><DeleteIcon fontSize="small" /></IconButton></Tooltip>
                </TableCell>
              </TableRow>
            ))}
            {rows.length === 0 && (
              <TableRow><TableCell colSpan={4} align="center" sx={{ py: 4, color: 'text.secondary' }}>{t('common_empty')}</TableCell></TableRow>
            )}
          </TableBody>
        </Table>
      </Card>

      <Dialog open={Boolean(dialog)} onClose={() => setDialog(null)} maxWidth="sm" fullWidth>
        <DialogTitle>{dialog?.mode === 'create' ? t('station_new') : t('station_edit')}</DialogTitle>
        <DialogContent>
          {dialog && (
            <Stack spacing={2} mt={1}>
              <TextField label={t('station_city')} value={dialog.data.city} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, city: e.target.value } })} />
              <TextField label={t('station_address')} value={dialog.data.address} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, address: e.target.value } })} />
              <TextField label={t('station_work_time')} value={dialog.data.workTime || ''} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, workTime: e.target.value } })} />
              <TextField label={t('station_description')} multiline minRows={2} value={dialog.data.description || ''} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, description: e.target.value } })} />
              <TextField label={t('station_equipment')} value={dialog.data.equipmentList || ''} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, equipmentList: e.target.value } })} />
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
