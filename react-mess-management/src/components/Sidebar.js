import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const navItems = [
  { path: '/dashboard', label: 'Dashboard', icon: '⊞' },
  { path: '/menu', label: 'Menu', icon: '☰' },
  { path: '/feedback', label: 'Feedback', icon: '★' },
  { path: '/settings', label: 'Settings', icon: '⚙' },
];

function Sidebar() {
  const { logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => { logout(); navigate('/'); };

  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <div className="sidebar-logo-icon">🍽</div>
        <div className="sidebar-logo-text">
          <strong>Smart Mess</strong>
          <span>Management</span>
        </div>
      </div>

      <div className="sidebar-section-label">Navigation</div>
      <nav className="sidebar-nav">
        {navItems.map(item => (
          <button
            key={item.path}
            className={`sidebar-link${location.pathname === item.path ? ' active' : ''}`}
            onClick={() => navigate(item.path)}
          >
            <span>{item.icon}</span>
            {item.label}
          </button>
        ))}
      </nav>

      <div className="sidebar-section-label" style={{ marginTop: '16px' }}>System Admin</div>
      <div className="sidebar-nav">
        <button className="sidebar-link" onClick={handleLogout}>
          <span>↩</span> Sign Out
        </button>
        <button
          className={`sidebar-link${location.pathname === '/settings' ? ' active' : ''}`}
          onClick={() => navigate('/settings')}
        >
          <span>⚙</span> Settings
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;
