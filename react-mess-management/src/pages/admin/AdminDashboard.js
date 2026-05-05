import { useState, useEffect } from 'react';
import { api } from '../../services/api';

function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      const response = await api.admin.getDashboard();
      if (response.success) {
        setStats(response.data);
      }
    } catch (error) {
      console.error('Failed to load dashboard', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="page-wrapper"><h2 className="dash-title">Loading Dashboard...</h2></div>;

  return (
    <div className="page-wrapper">
      <div className="dash-top">
        <div>
          <div className="welcome-badge">SYSTEM OVERVIEW</div>
          <h1 className="dash-title">Admin<span>Dashboard</span></h1>
          <p className="dash-subtitle">High-level statistics and metrics for the Smart Mess system.</p>
        </div>
      </div>

      <div className="stat-cards" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))' }}>
        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon blue">👥</div>
            <div className="stat-card-info">
              <div className="stat-label">Total Users</div>
            </div>
          </div>
          <div className="stat-value">{stats?.totalUsers || 0}</div>
          <div className="stat-sub" style={{ marginTop: 8 }}>{stats?.totalStudents || 0} Students, {stats?.totalAdmins || 0} Admins</div>
        </div>

        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon green">📅</div>
            <div className="stat-card-info">
              <div className="stat-label">Total Bookings</div>
            </div>
          </div>
          <div className="stat-value">{stats?.totalBookings || 0}</div>
          <div className="stat-sub" style={{ marginTop: 8 }}>{stats?.activeBookings || 0} Active, {stats?.cancelledBookings || 0} Cancelled</div>
        </div>

        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon teal">🍽</div>
            <div className="stat-card-info">
              <div className="stat-label">Total Menus</div>
            </div>
          </div>
          <div className="stat-value">{stats?.totalMenus || 0}</div>
        </div>

        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon" style={{ background: '#f59e0b20', color: '#f59e0b' }}>⭐</div>
            <div className="stat-card-info">
              <div className="stat-label">Total Feedback</div>
            </div>
          </div>
          <div className="stat-value">{stats?.totalFeedback || 0}</div>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
