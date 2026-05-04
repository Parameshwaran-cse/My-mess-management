import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

function ProtectedRoute({ children }) {
  const { isAuthenticated, loading } = useAuth();
  
  if (loading) {
    return <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', background: '#0d0d1a', color: '#e2e8f0', fontSize: 16 }}>Loading...</div>;
  }
  
  return isAuthenticated ? children : <Navigate to="/" replace />;
}

export default ProtectedRoute;
