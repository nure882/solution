import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from '@mui/material';
import { useLang } from '../../context/LanguageContext';

export default function ConfirmDialog({ open, title, message, onConfirm, onCancel }) {
  const { t } = useLang();
  return (
    <Dialog open={open} onClose={onCancel} maxWidth="xs" fullWidth>
      <DialogTitle sx={{ fontWeight: 700, fontSize: '1rem' }}>{title || t('common_confirm_delete_title')}</DialogTitle>
      <DialogContent>
        <DialogContentText sx={{ fontSize: '0.875rem' }}>{message || t('common_confirm_delete_msg')}</DialogContentText>
      </DialogContent>
      <DialogActions sx={{ px: 3, pb: 2.5, gap: 1 }}>
        <Button onClick={onCancel} variant="outlined" size="small">{t('common_cancel')}</Button>
        <Button onClick={onConfirm} variant="contained" color="error" size="small">{t('common_delete')}</Button>
      </DialogActions>
    </Dialog>
  );
}
