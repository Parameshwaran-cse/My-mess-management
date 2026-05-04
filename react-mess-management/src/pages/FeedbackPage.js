import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

const FOOD_ITEMS = [
  { id: 'l1', name: 'Paneer Butter Masala', img: '/paneer.png', meal: 'lunch' },
  { id: 'l2', name: 'Jeera Rice', img: '/jeera.png', meal: 'lunch' },
  { id: 'l3', name: 'Mixed Vegetable Curry', img: '/veg.png', meal: 'lunch' },
  { id: 'l4', name: 'Chapati', img: '/chapati.png', meal: 'lunch' },
];

const ALTERNATES = { lunch: ['Dal Fry', 'Kootu', 'Veg Kurma', 'Rajma Rice'] };

function FeedbackPage() {

  const [ratings, setRatings] = useState({});
  const [alternate, setAlternate] = useState('');
  const [submitted, setSubmitted] = useState(false);
  const [hover, setHover] = useState({});

  const handleRate = (id, val) => setRatings(r => ({ ...r, [id]: val }));

  const handleSubmit = () => {
    setSubmitted(true);
    setTimeout(() => setSubmitted(false), 3000);
  };

  return (
    <div className="feedback-page">
      <div className="page-header">
        <div>
          <h1 className="page-title">Give Feedback</h1>
          <p className="page-subtitle">Rate today's meals and suggest alternatives to help us improve.</p>
        </div>
      </div>

      <div className="feedback-form-panel">
        <div className="feedback-form-title">Rate Today's Lunch Items</div>

        {FOOD_ITEMS.map(item => (
          <div key={item.id} className="feedback-food-row">
            <img src={item.img} alt={item.name} className="feedback-food-img" />
            <span className="feedback-food-name">{item.name}</span>
            <div className="interactive-stars">
              {[1, 2, 3, 4, 5].map(v => (
                <span
                  key={v}
                  className={`istar${(hover[item.id] || ratings[item.id] || 0) >= v ? ' active' : ''}`}
                  onClick={() => handleRate(item.id, v)}
                  onMouseEnter={() => setHover(h => ({ ...h, [item.id]: v }))}
                  onMouseLeave={() => setHover(h => ({ ...h, [item.id]: 0 }))}
                >★</span>
              ))}
            </div>
          </div>
        ))}

        <div className="alternate-section">
          <div className="alternate-label">Suggest an alternate for the least-rated item</div>
          <select className="alternate-select" value={alternate} onChange={e => setAlternate(e.target.value)}>
            <option value="">Select alternate dish</option>
            {ALTERNATES.lunch.map(a => <option key={a} value={a}>{a}</option>)}
          </select>
        </div>

        <button className="submit-feedback-btn" onClick={handleSubmit}>
          Submit Feedback
        </button>

        {submitted && (
          <div style={{ marginTop: 12, color: '#34d399', fontSize: 13, fontWeight: 600 }}>
            ✔ Feedback submitted successfully! Thank you.
          </div>
        )}
      </div>
    </div>
  );
}

export default FeedbackPage;
