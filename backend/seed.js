const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const dotenv = require('dotenv');
const User = require('./src/models/User');
const Menu = require('./src/models/Menu');
const Feedback = require('./src/models/Feedback');

dotenv.config();

const seedData = async () => {
  try {
    await mongoose.connect(process.env.MONGODB_URI);
    console.log('MongoDB connected');

    // Clear existing data
    await User.deleteMany({});
    await Menu.deleteMany({});
    await Feedback.deleteMany({});
    console.log('Cleared existing data');

    // Create users
    const hashedPassword = await bcrypt.hash('password123', 10);
    
    const users = await User.insertMany([
      { username: 'admin', password: hashedPassword, role: 'admin' },
      { username: 'student1', password: hashedPassword, role: 'student' },
      { username: 'student2', password: hashedPassword, role: 'student' }
    ]);
    console.log('Users created');

    // Create menus
    const menus = await Menu.insertMany([
      {
        mealType: 'breakfast',
        items: [
          { name: 'Idli', available: true },
          { name: 'Dosa', available: true }
        ],
        alternates: ['Pongal', 'Upma', 'Rava Dosa']
      },
      {
        mealType: 'lunch',
        items: [
          { name: 'Sambar', available: true }
        ],
        alternates: ['Dal Fry', 'Kootu', 'Veg Kurma']
      },
      {
        mealType: 'dinner',
        items: [
          { name: 'Chapati', available: true }
        ],
        alternates: ['Veg Stew', 'Paneer Curry', 'Channa Masala', 'Mixed Veg Gravy']
      }
    ]);
    console.log('Menus created');

    // Create sample feedback
    const feedback = await Feedback.insertMany([
      {
        userId: users[1]._id,
        ratings: new Map([
          ['Idli', 5],
          ['Dosa', 4],
          ['Sambar', 3]
        ]),
        alternates: new Map([
          ['breakfast', 'Upma'],
          ['lunch', 'Dal Fry']
        ])
      },
      {
        userId: users[2]._id,
        ratings: new Map([
          ['Idli', 4],
          ['Chapati', 5]
        ]),
        alternates: new Map([
          ['dinner', 'Paneer Curry']
        ])
      }
    ]);
    console.log('Feedback created');

    console.log('\n✅ Database seeded successfully!');
    console.log('\nTest Credentials:');
    console.log('Admin - username: admin, password: password123');
    console.log('Student - username: student1, password: password123');
    
    process.exit(0);
  } catch (error) {
    console.error('Error seeding database:', error);
    process.exit(1);
  }
};

seedData();
