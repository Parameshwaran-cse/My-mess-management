import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

const MEALS = {
  breakfast: {
    items: [
      { id: 'b1', name: 'Idli', score: 4.1, img: null },
      { id: 'b2', name: 'Dosa', score: 4.5, img: null },
    ]
  },
  lunch: {
    items: [
      { id: 'l1', name: 'Paneer Butter Masala', score: 4.4, img: '/paneer.png' },
      { id: 'l2', name: 'Jeera Rice', score: 3.9, img: '/jeera.png' },
      { id: 'l3', name: 'Mixed Vegetable Curry', score: 3.2, img: '/veg.png' },
      { id: 'l4', name: 'Chapati', score: 4.2, img: '/chapati.png' },
    ]
  },
  dinner: {
    items: [
      { id: 'd1', name: 'Veg Stew', score: 3.8, img: null },
      { id: 'd2', name: 'Chapati', score: 4.2, img: '/chapati.png' },
    ]
  }
};

const RECENT_FEEDBACK = [
  { id: 1, name: 'Rahul Sharma', dish: 'Paneer Butter Masala', date: '12 Apr 2024', stars: 4, text: 'Very tasty, paneer was soft the gravy was delicious!', initials: 'RS', color: '#4f46e5' },
  { id: 2, name: 'Ananya Patel', dish: 'Mixed Vegetable Curry', date: '11 Apr 2024', stars: 4, text: 'Vegetable curry was a bit bland and could learn more spices.', initials: 'AP', color: '#7c3aed' },
  { id: 3, name: 'Mohan Singh', dish: 'Chapati', date: '11 Apr 2024', stars: 4, text: 'Chapati was fresh and soft. Really liked it.', initials: 'MS', color: '#0ea5e9' },
];

function Stars({ count, total = 5 }) {
  return (
    <span className="food-stars">
      {Array.from({ length: total }).map((_, i) => (
        <span key={i} style={{ color: i < Math.round(count) ? '#f59e0b' : '#2a2a50' }}>★</span>
      ))}
    </span>
  );
}

function Dashboard() {
  const { user } = useAuth();
  const [activeTab, setActiveTab] = useState('lunch');
  const displayName = user?.fullName || user?.username || 'Guest';



  return (
    <div className="page-wrapper">
      {/* Top */}
      <div className="dash-top">
        <div>
          <div className="welcome-badge">Welcome back, {displayName.toUpperCase()}</div>
          <h1 className="dash-title">Your Mess<br /><span>Dashboard</span></h1>
          <p className="dash-subtitle">Track and rate your daily mess meals and give feedback<br />to help improve the menu in one place.</p>
        </div>
        <a href="#profile" className="profile-btn">
          <span className="profile-dot" />
          View Public Profile &nbsp;›
        </a>
      </div>

      {/* Stat Cards */}
      <div className="stat-cards">
        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon blue">🍽</div>
            <div className="stat-card-info">
              <div className="stat-label">Today's Meal</div>
              <div style={{ fontWeight: 700, fontSize: 15, marginTop: 2 }}>Lunch</div>
              <div className="stat-sub">12 Apr 2024</div>
            </div>
          </div>
          <div className="star-row">
            <span className="stars">★★★★☆</span>
            <span className="score">4.3</span>
          </div>
          <a href="#menu" className="stat-link">Enable Ratings &nbsp;›</a>
        </div>

        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon teal">⭐</div>
            <div className="stat-card-info">
              <div className="stat-label">Feedback Given</div>
            </div>
          </div>
          <div className="stat-value">256</div>
        </div>

        <div className="stat-card">
          <div className="stat-card-top">
            <div className="stat-icon green">✔</div>
            <div className="stat-card-info">
              <div className="stat-label">Alternate Foods</div>
            </div>
          </div>
          <div className="stat-value">72 <span style={{ fontSize: 14, color: 'var(--muted)', fontWeight: 500 }}>Selected</span></div>
        </div>
      </div>

      {/* Body: Menu + Feedback */}
      <div className="dash-body">
        {/* Menu Panel */}
        <div className="panel">
          <div className="panel-header">
            <span className="panel-title">Today's Menu</span>
            <div className="meal-tabs">
              {['breakfast', 'lunch', 'dinner'].map(t => (
                <button key={t} className={`meal-tab${activeTab === t ? ' active' : ''}`} onClick={() => setActiveTab(t)}>
                  {t.charAt(0).toUpperCase() + t.slice(1)}
                </button>
              ))}
            </div>
            <div className="meal-tab-combo">
              {activeTab.charAt(0).toUpperCase() + activeTab.slice(1)} Lunch ▾
            </div>
          </div>
          <div className="meal-date-bar">
            <span className="date">Lunch &nbsp;-&nbsp; 12 Apr 2024 &nbsp;›</span>
            <span>⌄</span>
          </div>
          <div className="food-grid">
            {MEALS[activeTab].items.map(item => (
              <div key={item.id} className="food-item">
                {item.img
                  ? <img src={item.img} alt={item.name} className="food-img" />
                  : <div className="food-img" style={{ background: 'var(--card2)', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 22 }}>🍛</div>
                }
                <div className="food-info">
                  <div className="food-name">{item.name}</div>
                  <Stars count={item.score} />
                  <div className="food-score">{item.score}</div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Feedback Sidebar */}
        <div className="feedback-panel">
          <div className="feedback-panel-header">
            <div className="feedback-panel-title">Your Recent Feedback</div>
          </div>
          {RECENT_FEEDBACK.map(f => (
            <div key={f.id} className="feedback-item">
              <div className="feedback-avatar" style={{ background: f.color }}>{f.initials}</div>
              <div className="feedback-meta">
                <div className="feedback-top-row">
                  <span className="feedback-user">{f.name}</span>
                  <span className="feedback-date">{f.date}</span>
                </div>
                <div className="feedback-dish">{f.dish}</div>
                <div className="feedback-stars">{'★'.repeat(f.stars)}{'☆'.repeat(5 - f.stars)}</div>
                <div className="feedback-text">{f.text}</div>
              </div>
            </div>
          ))}
          <button className="view-all-btn">View All Feedback</button>
        </div>
      </div>

      {/* Recent Feedback row label at bottom */}
      <div className="panel" style={{ marginTop: 20 }}>
        <div className="panel-header">
          <span className="panel-title">Your Recent Feedback</span>
          <span style={{ color: 'var(--muted)', fontSize: 18 }}>⚙ ›</span>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
