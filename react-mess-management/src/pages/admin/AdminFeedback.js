import { useState, useEffect } from 'react';
import { api } from '../../services/api';

function Stars({ count, total = 5 }) {
  return (
    <span style={{ color: '#f59e0b', fontSize: 14 }}>
      {'★'.repeat(Math.round(count))}{'☆'.repeat(total - Math.round(count))}
    </span>
  );
}

function AdminFeedback() {
  const [feedback, setFeedback] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadFeedback();
  }, []);

  const loadFeedback = async () => {
    try {
      const response = await api.admin.getAllFeedback();
      if (response.success) {
        setFeedback(response.data);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="page-wrapper"><h2 className="dash-title">Loading Feedback...</h2></div>;

  return (
    <div className="page-wrapper">
      <div className="dash-top">
        <div>
          <h1 className="dash-title">System<span>Feedback</span></h1>
          <p className="dash-subtitle">Review all feedback submitted by students across the system.</p>
        </div>
      </div>

      <div className="panel">
        <div className="panel-header">
          <span className="panel-title">All User Feedback</span>
        </div>
        
        <div style={{ display: 'flex', flexDirection: 'column', gap: 16, marginTop: 16 }}>
          {feedback.map(item => (
            <div key={item.id} style={{ 
              background: 'var(--card2)', 
              padding: 20, 
              borderRadius: 16,
              border: '1px solid var(--border)'
            }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: 12 }}>
                <div>
                  <div style={{ fontWeight: 600, color: 'var(--text)', fontSize: 16 }}>
                    User ID: {item.userId}
                  </div>
                  <div style={{ color: 'var(--muted)', fontSize: 14, marginTop: 4 }}>
                    {new Date(item.submittedAt).toLocaleDateString()}
                  </div>
                </div>
                <div style={{ textAlign: 'right' }}>
                  <div style={{ fontSize: 14, color: 'var(--text)' }}>{item.menuItem?.name || 'General'}</div>
                  <Stars count={item.rating || 0} />
                </div>
              </div>
              
              {item.comments && (
                <div style={{ color: 'var(--text)', background: 'var(--bg)', padding: 12, borderRadius: 8, fontSize: 14 }}>
                  "{item.comments}"
                </div>
              )}
            </div>
          ))}
          {feedback.length === 0 && (
            <div style={{ padding: 40, textAlign: 'center', color: 'var(--muted)' }}>
              No feedback has been submitted yet.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default AdminFeedback;
