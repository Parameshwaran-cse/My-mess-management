import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { api } from '../services/api';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('student');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    if (password !== confirmPassword) { setError('Passwords do not match'); return; }
    if (password.length < 6) { setError('Password must be at least 6 characters'); return; }
    setLoading(true);
    try {
      await api.auth.register(username, password, role);
      navigate('/', { state: { message: 'Registration successful! Please login.' } });
    } catch (err) {
      setError(err.message || 'Registration failed. Username may already exist.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-screen">
      <div className="auth-box">
        <div className="auth-logo">
          <div className="auth-logo-icon">🍽</div>
          <div className="auth-logo-text">
            <strong>Smart Mess</strong>
            <span>Management System</span>
          </div>
        </div>
        <h2 className="auth-title">Create account</h2>
        <p className="auth-sub">Join Smart Mess Management today</p>

        <form onSubmit={handleRegister}>
          <label className="auth-label">Username</label>
          <input className="auth-input" type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Choose a username" required minLength="3" disabled={loading} />

          <label className="auth-label" style={{ marginTop: 16 }}>Password</label>
          <input className="auth-input" type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Min 6 characters" required minLength="6" disabled={loading} />

          <label className="auth-label" style={{ marginTop: 16 }}>Confirm Password</label>
          <input className="auth-input" type="password" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} placeholder="Re-enter password" required disabled={loading} />

          <label className="auth-label" style={{ marginTop: 16 }}>Role</label>
          <select className="auth-select" value={role} onChange={e => setRole(e.target.value)} disabled={loading}>
            <option value="student">Student</option>
            <option value="admin">Admin</option>
          </select>

          {error && <p className="auth-error">{error}</p>}

          <button className="auth-btn" type="submit" disabled={loading}>
            {loading ? 'Creating Account...' : 'Create Account'}
          </button>
        </form>

        <p className="auth-footer">
          Already have an account? <Link to="/">Sign in</Link>
        </p>
      </div>
    </div>
  );
}

export default Register;
