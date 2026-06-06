import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, CssBaseline } from '@mui/material';
import theme from './theme/theme';
import { AuthProvider } from './context/AuthContext';
import { LanguageProvider } from './context/LanguageContext';
import ProtectedRoute from './components/layout/ProtectedRoute';
import AppLayout from './components/layout/AppLayout';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import StationsPage from './pages/StationsPage';
import StationDetailPage from './pages/StationDetailPage';
import GasTypesPage from './pages/GasTypesPage';
import PumpsPage from './pages/PumpsPage';
import CouponsPage from './pages/CouponsPage';
import UsersPage from './pages/UsersPage';
import ProfilePage from './pages/ProfilePage';

export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <LanguageProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route element={<ProtectedRoute><AppLayout /></ProtectedRoute>}>
                <Route index element={<DashboardPage />} />
                <Route path="/stations" element={<ProtectedRoute roles={['ADMIN']}><StationsPage /></ProtectedRoute>} />
                <Route path="/stations/:id" element={<ProtectedRoute roles={['ADMIN']}><StationDetailPage /></ProtectedRoute>} />
                <Route path="/gas-types" element={<ProtectedRoute roles={['ADMIN']}><GasTypesPage /></ProtectedRoute>} />
                <Route path="/pumps" element={<ProtectedRoute roles={['ADMIN']}><PumpsPage /></ProtectedRoute>} />
                <Route path="/coupons" element={<ProtectedRoute roles={['ADMIN', 'WORKER']}><CouponsPage /></ProtectedRoute>} />
                <Route path="/users" element={<ProtectedRoute roles={['ADMIN']}><UsersPage /></ProtectedRoute>} />
                <Route path="/profile" element={<ProfilePage />} />
              </Route>
              <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
          </BrowserRouter>
        </LanguageProvider>
      </AuthProvider>
    </ThemeProvider>
  );
}
