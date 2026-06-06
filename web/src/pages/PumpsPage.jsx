import { useEffect, useState, useCallback } from 'react';
import {
  Box, Card, Table, TableHead, TableRow, TableCell, TableBody, IconButton,
  Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, MenuItem, Stack, Tooltip,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import * as api from '../api/gasStationApi';
import { useLang } from '../context/LanguageContext';
import PageHeader from '../components/common/PageHeader';
import ConfirmDialog from '../components/common/ConfirmDialog';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

const EMPTY = { number: 1, gasCount: 0, cost: 0, stationId: '', gasTypeId: '' };

export default function PumpsPage() {
  const { t } = useLang();
  const [rows, setRows] = useState([]);
  const [stations, setStations] = useState([]);
  const [gasTypes, setGasTypes] = useState([]);
  const [dialog, setDialog] = useState(null);
  const [toDelete, setToDelete] = useState(null);
  const [error, setError] = useState('');

  const load = useCallback(async () => {
    try {
      const [p, s, g] = await Promise.all([api.getPumps(), api.getStations(), api.getGasTypes()]);
      setRows(p.data); setStations(s.data); setGasTypes(g.data);
    } catch { setError('Failed to load pumps'); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const save = async () => {
    try {
      const { mode, data } = dialog;
      const body = { number: Number(data.number), gasCount: Number(data.gasCount), cost: Number(data.cost), stationId: data.stationId, gasTypeId: data.gasTypeId };
      if (mode === 'create') await api.createPump(body);
      else await api.updatePump(data.pumpId, body);
      setDialog(null); load();
    } catch { setError('Failed to save pump'); }
  };
  const remove = async () => {
    try { await api.deletePump(toDelete.pumpId); setToDelete(null); load(); } catch { setError('Failed to delete pump'); }
  };

  const stationLabel = (id) => { const s = stations.find(x => x.stationId === id); return s ? `${s.city}, ${s.address}` : id; };

  return (
    <Box>
      <PageHeader title={t('pumps_title')} actionLabel={t('pump_new')}
        onAction={() => setDialog({ mode: 'create', data: { ...EMPTY } })} />
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t('pump_number')}</TableCell>
              <TableCell>{t('pump_station')}</TableCell>
              <TableCell>{t('pump_gas_type')}</TableCell>
              <TableCell align="right">{t('pump_cost')}</TableCell>
              <TableCell align="right">{t('pump_gas_count')}</TableCell>
              <TableCell align="right">{t('common_actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map(r => (
              <TableRow key={r.pumpId} hover>
                <TableCell>#{r.number}</TableCell>
                <TableCell>{stationLabel(r.stationId)}</TableCell>
                <TableCell>{r.gasTypeName}</TableCell>
                <TableCell align="right">{r.cost.toFixed(2)}</TableCell>
                <TableCell align="right">{r.gasCount.toFixed(1)}</TableCell>
                <TableCell align="right">
                  <Tooltip title={t('common_edit')}><IconButton size="small" onClick={() => setDialog({ mode: 'edit', data: { ...r } })}><EditIcon fontSize="small" /></IconButton></Tooltip>
                  <Tooltip title={t('common_delete')}><IconButton size="small" color="error" onClick={() => setToDelete(r)}><DeleteIcon fontSize="small" /></IconButton></Tooltip>
                </TableCell>
              </TableRow>
            ))}
            {rows.length === 0 && <TableRow><TableCell colSpan={6} align="center" sx={{ py: 4, color: 'text.secondary' }}>{t('common_empty')}</TableCell></TableRow>}
          </TableBody>
        </Table>
      </Card>

      <Dialog open={Boolean(dialog)} onClose={() => setDialog(null)} maxWidth="sm" fullWidth>
        <DialogTitle>{dialog?.mode === 'create' ? t('pump_new') : t('pump_edit')}</DialogTitle>
        <DialogContent>
          {dialog && (
            <Stack spacing={2} mt={1}>
              <TextField label={t('pump_number')} type="number" value={dialog.data.number} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, number: e.target.value } })} />
              <TextField label={t('pump_gas_count')} type="number" value={dialog.data.gasCount} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, gasCount: e.target.value } })} />
              <TextField label={t('pump_cost')} type="number" value={dialog.data.cost} onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, cost: e.target.value } })} />
              <TextField select label={t('pump_station')} value={dialog.data.stationId || ''} disabled={dialog.mode === 'edit'}
                onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, stationId: e.target.value } })}>
                {stations.map(s => <MenuItem key={s.stationId} value={s.stationId}>{s.city}, {s.address}</MenuItem>)}
              </TextField>
              <TextField select label={t('pump_gas_type')} value={dialog.data.gasTypeId || ''}
                onChange={(e) => setDialog({ ...dialog, data: { ...dialog.data, gasTypeId: e.target.value } })}>
                {gasTypes.map(g => <MenuItem key={g.gasTypeId} value={g.gasTypeId}>{g.name}</MenuItem>)}
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
