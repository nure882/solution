import { createTheme } from '@mui/material/styles';

// GasStation theme — white / blue / black (IBM-inspired palette)
const theme = createTheme({
  palette: {
    mode: 'light',
    primary: { main: '#0F62FE', light: '#4589FF', dark: '#0043CE', contrastText: '#FFFFFF' },
    secondary: { main: '#6E7E91', contrastText: '#FFFFFF' },
    background: { default: '#F4F4F4', paper: '#FFFFFF' },
    text: { primary: '#161616', secondary: '#6E7E91' },
    divider: '#E0E0E0',
    error: { main: '#DA1E28' },
    success: { main: '#198038' },
    warning: { main: '#F1C21B' },
  },
  typography: {
    fontFamily: "'Inter', 'IBM Plex Sans', 'Roboto', sans-serif",
    h4: { fontWeight: 700, letterSpacing: '-0.02em' },
    h5: { fontWeight: 700, letterSpacing: '-0.01em' },
    h6: { fontWeight: 600 },
    button: { fontWeight: 600, textTransform: 'none' },
    subtitle2: { fontWeight: 600 },
  },
  shape: { borderRadius: 8 },
  components: {
    MuiButton: {
      defaultProps: { disableElevation: true },
      styleOverrides: { root: { borderRadius: 6, padding: '8px 18px', transition: 'all 0.15s ease' } },
    },
    MuiCard: {
      defaultProps: { elevation: 0 },
      styleOverrides: { root: { border: '1px solid #E0E0E0', borderRadius: 10 } },
    },
    MuiPaper: {
      defaultProps: { elevation: 0 },
      styleOverrides: { root: { border: '1px solid #E0E0E0' } },
    },
    MuiTextField: { defaultProps: { variant: 'outlined', size: 'small' } },
    MuiChip: { styleOverrides: { root: { borderRadius: 4, fontWeight: 600, fontSize: '0.75rem' } } },
    MuiAppBar: {
      defaultProps: { elevation: 0 },
      styleOverrides: { root: { backgroundColor: '#FFFFFF', borderBottom: '1px solid #E0E0E0', color: '#161616' } },
    },
    MuiTableCell: {
      styleOverrides: { head: { fontWeight: 600, color: '#6E7E91', fontSize: '0.75rem', textTransform: 'uppercase', letterSpacing: '0.05em' } },
    },
    MuiDialog: { styleOverrides: { paper: { borderRadius: 12, border: '1px solid #E0E0E0' } } },
  },
});

export default theme;
