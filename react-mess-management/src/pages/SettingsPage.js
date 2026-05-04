import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

function SettingsPage() {

  const [profileOpen, setProfileOpen] = useState(true);
  const [passwordOpen, setPasswordOpen] = useState(false);
  const [notifOpen, setNotifOpen] = useState(false);
  const [name, setName] = useState('John Doe');
  const [email, setEmail] = useState('john.doe@example.com');
  const [saved, setSaved] = useState(false);

  const handleSave = () => {
    setSaved(true);
    setTimeout(() => setSaved(false), 2000);
  };

  return (
    <div className="settings-page">
      <div style={{ marginBottom: 28 }}>
        <h1 className="page-title">Settings</h1>
        <p className="page-subtitle">Update your account settings, preferences, and notification preferences.</p>
      </div>

      {/* Profile */}
      <div className="settings-section">
        <div className="settings-section-header" onClick={() => setProfileOpen(o => !o)}>
          <span className="settings-section-title">Profile</span>
          <span className={`settings-chevron${profileOpen ? ' open' : ''}`}>⌄</span>
        </div>
        {profileOpen && (
          <div className="settings-body">
            <div className="profile-info">
              <div className="profile-avatar">
                <span style={{ fontSize: 28 }}>👤</span>
              </div>
              <div>
                <div className="profile-name">{name}</div>
                <div className="profile-email">{email}</div>
              </div>
            </div>

            <div className="form-row">
              <div className="form-field">
                <label className="form-label">Full Name</label>
                <input className="form-input" value={name} onChange={e => setName(e.target.value)} />
              </div>
              <div className="form-field">
                <label className="form-label">Role</label>
                <select className="form-select">
                  <option>Student</option>
                  <option>Admin</option>
                </select>
              </div>
            </div>

            <div className="form-row-full">
              <div className="form-field">
                <label className="form-label">Email</label>
                <input className="form-input" value={email} onChange={e => setEmail(e.target.value)} />
              </div>
              <button className="save-btn" onClick={handleSave}>
                {saved ? '✔ Saved!' : 'Save Changes'}
              </button>
            </div>
          </div>
        )}
      </div>

      {/* Change Password */}
      <div className="settings-section">
        <div className="settings-section-header" onClick={() => setPasswordOpen(o => !o)}>
          <span className="settings-section-title">Change Password</span>
          <span className="settings-chevron">›</span>
        </div>
        {passwordOpen && (
          <div className="settings-body">
            <div className="form-field" style={{ marginBottom: 12 }}>
              <label className="form-label">Current Password</label>
              <input type="password" className="form-input" placeholder="••••••••" />
            </div>
            <div className="form-row">
              <div className="form-field">
                <label className="form-label">New Password</label>
                <input type="password" className="form-input" placeholder="••••••••" />
              </div>
              <div className="form-field">
                <label className="form-label">Confirm Password</label>
                <input type="password" className="form-input" placeholder="••••••••" />
              </div>
            </div>
            <button className="save-btn" style={{ marginTop: 4 }}>Update Password</button>
          </div>
        )}
      </div>

      {/* Notification Preferences */}
      <div className="settings-section">
        <div className="settings-section-header" onClick={() => setNotifOpen(o => !o)}>
          <span className="settings-section-title">Notification Preferences</span>
          <span className="settings-chevron">›</span>
        </div>
        {notifOpen && (
          <div className="settings-body">
            <p style={{ color: 'var(--muted)', fontSize: 13 }}>Notification settings coming soon.</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default SettingsPage;
