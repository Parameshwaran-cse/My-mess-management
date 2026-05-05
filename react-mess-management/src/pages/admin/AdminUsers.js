import { useState, useEffect } from 'react';
import { api } from '../../services/api';

function AdminUsers() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const response = await api.admin.getUsers();
      if (response.success) {
        setUsers(response.data);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleRoleChange = async (userId, currentRole) => {
    const newRole = currentRole === 'ADMIN' ? 'STUDENT' : 'ADMIN';
    if (window.confirm(`Are you sure you want to change this user's role to ${newRole}?`)) {
      try {
        await api.admin.changeRole(userId, newRole);
        loadUsers();
      } catch (err) {
        alert('Failed to update role: ' + err.message);
      }
    }
  };

  const handleDelete = async (userId) => {
    if (window.confirm('Are you sure you want to delete this user? This action cannot be undone.')) {
      try {
        await api.admin.deleteUser(userId);
        loadUsers();
      } catch (err) {
        alert('Failed to delete user: ' + err.message);
      }
    }
  };

  if (loading) return <div className="page-wrapper"><h2 className="dash-title">Loading Users...</h2></div>;

  return (
    <div className="page-wrapper">
      <div className="dash-top">
        <div>
          <h1 className="dash-title">Manage<span>Users</span></h1>
          <p className="dash-subtitle">View and manage all registered users in the system.</p>
        </div>
      </div>

      {error && <div style={{ color: '#ef4444', marginBottom: 20 }}>{error}</div>}

      <div className="panel">
        <div className="panel-header">
          <span className="panel-title">All Users</span>
        </div>
        
        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: 10 }}>
            <thead>
              <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)', color: 'var(--muted)' }}>
                <th style={{ padding: '12px' }}>ID</th>
                <th style={{ padding: '12px' }}>Username</th>
                <th style={{ padding: '12px' }}>Full Name</th>
                <th style={{ padding: '12px' }}>Email</th>
                <th style={{ padding: '12px' }}>Role</th>
                <th style={{ padding: '12px', textAlign: 'right' }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {users.map(user => (
                <tr key={user.id} style={{ borderBottom: '1px solid var(--card)' }}>
                  <td style={{ padding: '12px' }}>{user.id}</td>
                  <td style={{ padding: '12px', fontWeight: 600 }}>{user.username}</td>
                  <td style={{ padding: '12px' }}>{user.fullName || '-'}</td>
                  <td style={{ padding: '12px' }}>{user.email}</td>
                  <td style={{ padding: '12px' }}>
                    <span style={{ 
                      padding: '4px 8px', 
                      borderRadius: 12, 
                      fontSize: 12, 
                      background: user.role === 'ADMIN' ? '#4f46e530' : '#2a2a50',
                      color: user.role === 'ADMIN' ? '#818cf8' : '#e2e8f0'
                    }}>
                      {user.role}
                    </span>
                  </td>
                  <td style={{ padding: '12px', textAlign: 'right' }}>
                    <button 
                      onClick={() => handleRoleChange(user.id, user.role)}
                      style={{ 
                        background: 'transparent', 
                        border: '1px solid var(--border)', 
                        color: 'var(--text)', 
                        padding: '6px 12px', 
                        borderRadius: 6, 
                        cursor: 'pointer',
                        marginRight: 8
                      }}>
                      Change Role
                    </button>
                    <button 
                      onClick={() => handleDelete(user.id)}
                      style={{ 
                        background: '#ef444420', 
                        border: 'none', 
                        color: '#ef4444', 
                        padding: '6px 12px', 
                        borderRadius: 6, 
                        cursor: 'pointer'
                      }}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
              {users.length === 0 && (
                <tr>
                  <td colSpan="6" style={{ padding: 20, textAlign: 'center', color: 'var(--muted)' }}>No users found.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminUsers;
