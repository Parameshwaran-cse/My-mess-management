import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const studentNavItems = [
  { path: '/dashboard', label: 'Dashboard', icon: '⊞' },
  { path: '/menu', label: 'Menu', icon: '☰' },
  { path: '/feedback', label: 'Feedback', icon: '★' },
  { path: '/settings', label: 'Settings', icon: '⚙' },
];

const adminNavItems = [
  { path: '/admin', label: 'Dashboard', icon: '⊞' },
  { path: '/admin/users', label: 'Manage Users', icon: '👥' },
  { path: '/menu', label: 'Manage Menu', icon: '☰' },
  { path: '/admin/feedback', label: 'All Feedback', icon: '★' },
  { path: '/settings', label: 'Settings', icon: '⚙' },
];

function Sidebar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => { logout(); navigate('/'); };

  const isAdmin = user?.role === 'ADMIN';
  const navItemsToRender = isAdmin ? adminNavItems : studentNavItems;

  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <div className="sidebar-logo-icon">🍽</div>
        <div className="sidebar-logo-text">
          <strong>Smart Mess</strong>
          <span>Management</span>
        </div>
      </div>

      <div className="sidebar-section-label">{isAdmin ? 'Admin Console' : 'Navigation'}</div>
      <nav className="sidebar-nav">
        {navItemsToRender.map(item => (
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

      <div className="sidebar-section-label" style={{ marginTop: 'auto' }}>Account</div>
      <div className="sidebar-nav">
        <button className="sidebar-link" onClick={handleLogout}>
          <span>↩</span> Sign Out
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;
