import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Box, Card, CardContent, Typography, Button, Stack, Divider,
  Table, TableHead, TableRow, TableCell, TableBody,
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import * as api from '../api/gasStationApi';
import { useLang } from '../context/LanguageContext';
import ErrorSnackbar from '../components/common/ErrorSnackbar';

export default function StationDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { t } = useLang();
  const [station, setStation] = useState(null);
  const [pumps, setPumps] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    (async () => {
      try {
        const [s, p] = await Promise.all([api.getStation(id), api.getPumps(id)]);
        setStation(s.data); setPumps(p.data);
      } catch { setError('Failed to load station'); }
    })();
  }, [id]);

  return (
    <Box>
      <Button startIcon={<ArrowBackIcon />} onClick={() => navigate('/stations')} sx={{ mb: 2 }}>{t('common_back')}</Button>
      {station && (
        <Card sx={{ mb: 3 }}>
          <CardContent>
            <Typography variant="h6">{station.city}, {station.address}</Typography>
            <Stack spacing={1} mt={2}>
              <Typography variant="body2"><b>{t('station_work_time')}:</b> {station.workTime || '—'}</Typography>
              <Typography variant="body2"><b>{t('station_description')}:</b> {station.description || '—'}</Typography>
              <Typography variant="body2"><b>{t('station_equipment')}:</b> {station.equipmentList || '—'}</Typography>
            </Stack>
          </CardContent>
        </Card>
      )}
      <Typography variant="subtitle2" mb={1}>{t('station_pumps')}</Typography>
      <Card>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t('pump_number')}</TableCell>
              <TableCell>{t('pump_gas_type')}</TableCell>
              <TableCell align="right">{t('pump_cost')}</TableCell>
              <TableCell align="right">{t('pump_gas_count')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {pumps.map(p => (
              <TableRow key={p.pumpId} hover>
                <TableCell>#{p.number}</TableCell>
                <TableCell>{p.gasTypeName}</TableCell>
                <TableCell align="right">{p.cost.toFixed(2)}</TableCell>
                <TableCell align="right">{p.gasCount.toFixed(1)}</TableCell>
              </TableRow>
            ))}
            {pumps.length === 0 && (
              <TableRow><TableCell colSpan={4} align="center" sx={{ py: 3, color: 'text.secondary' }}>{t('common_empty')}</TableCell></TableRow>
            )}
          </TableBody>
        </Table>
      </Card>
      <ErrorSnackbar message={error} onClose={() => setError('')} />
    </Box>
  );
}
