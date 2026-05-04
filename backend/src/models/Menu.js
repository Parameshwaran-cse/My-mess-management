const mongoose = require('mongoose');

const menuSchema = new mongoose.Schema({
  mealType: {
    type: String,
    enum: ['breakfast', 'lunch', 'dinner'],
    required: true
  },
  items: [{
    name: String,
    available: { type: Boolean, default: true }
  }],
  alternates: [String],
  date: {
    type: Date,
    default: Date.now
  }
}, { timestamps: true });

module.exports = mongoose.model('Menu', menuSchema);
