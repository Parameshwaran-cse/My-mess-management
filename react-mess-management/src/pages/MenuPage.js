import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

const MENU_DATA = {
  breakfast: [
    { id: 1, name: 'Idli', score: 4.1, desc: 'Soft steamed rice cakes served with sambar and chutney.', img: null },
    { id: 2, name: 'Dosa', score: 4.5, desc: 'Crispy fermented rice crepe served with coconut chutney.', img: null },
  ],
  lunch: [
    { id: 3, name: 'Paneer Butter Masala', score: 4.4, desc: 'Paneer cooked in a creamy tomato-based sauce with spices.', img: '/paneer.png' },
    { id: 4, name: 'Jeera Rice', score: 3.9, desc: 'Basmati rice flavored with cumin seeds and herbs.', img: '/jeera.png' },
    { id: 5, name: 'Mixed Vegetable Curry', score: 3.2, desc: 'Mixed vegetables cooked in a spicy curry sauce.', img: '/veg.png' },
    { id: 6, name: 'Chapati', score: 4.2, desc: 'Soft, whole wheat flatbread served with meals.', img: '/chapati.png' },
  ],
  dinner: [
    { id: 7, name: 'Veg Stew', score: 3.8, desc: 'Light coconut milk-based stew with mixed vegetables.', img: null },
    { id: 8, name: 'Chapati', score: 4.2, desc: 'Soft, whole wheat flatbread served with meals.', img: '/chapati.png' },
  ]
};

function Stars({ count, total = 5 }) {
  return (
    <span style={{ color: '#f59e0b', fontSize: 14 }}>
      {'★'.repeat(Math.round(count))}{'☆'.repeat(total - Math.round(count))}
    </span>
  );
}

function MenuPage() {
  const [activeTab, setActiveTab] = useState('lunch');
  const [items, setItems] = useState(MENU_DATA);
  const { user } = useAuth();
  const isAdmin = user?.role === 'ADMIN';

  const handleDelete = (id) => {
    setItems(prev => ({
      ...prev,
      [activeTab]: prev[activeTab].filter(i => i.id !== id)
    }));
  };

  return (
    <div className="menu-page">
      <div className="page-header">
        <div>
          <h1 className="page-title">{isAdmin ? 'Manage Menu' : 'Today\'s Menu'}</h1>
          <p className="page-subtitle">View and {isAdmin ? 'edit' : 'explore'} daily meal menus for breakfast, lunch, and dinner.</p>
        </div>
        {isAdmin && <button className="edit-btn">🍳 Edit Menu &nbsp;›</button>}
      </div>

      <div className="tab-bar">
        {['breakfast', 'lunch', 'dinner'].map(t => (
          <button
            key={t}
            className={`tab-btn${activeTab === t ? ' active' : ''}`}
            onClick={() => setActiveTab(t)}
          >
            {t.charAt(0).toUpperCase() + t.slice(1)}
          </button>
        ))}
      </div>

      <div className="menu-panel">
        <div className="menu-panel-header">
          <span className="menu-panel-title">{activeTab.charAt(0).toUpperCase() + activeTab.slice(1)}</span>
          <select className="meal-dropdown">
            <option>Dinner</option>
            <option>Lunch</option>
            <option>Breakfast</option>
          </select>
          <span className="menu-chevron">⌄</span>
        </div>

        {items[activeTab].map(item => (
          <div key={item.id} className="menu-food-item">
            {item.img
              ? <img src={item.img} alt={item.name} className="menu-food-img" />
              : <div className="menu-food-img" style={{ background: 'var(--card2)', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 28, flexShrink: 0 }}>🍛</div>
            }
            <div className="menu-food-info">
              <div className="menu-food-name">{item.name}</div>
              <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                <Stars count={item.score} />
                <span style={{ fontSize: 12, color: 'var(--muted)' }}>{item.desc}</span>
              </div>
              <div className="menu-food-score">{item.score}</div>
            </div>
            {isAdmin && (
              <div className="menu-actions">
                <button className="action-btn edit" title="Edit">✏</button>
                <button className="action-btn del" title="Delete" onClick={() => handleDelete(item.id)}>🗑</button>
              </div>
            )}
          </div>
        ))}

        {isAdmin && (
          <div className="add-item-row">
            + Add Food Item
          </div>
        )}
      </div>
    </div>
  );
}

export default MenuPage;
