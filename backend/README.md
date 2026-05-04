# Mess Management Backend API

Node.js/Express backend for Mess Management System with MongoDB.

## 📁 Structure

```
backend/
├── src/
│   ├── config/
│   │   └── db.js              # MongoDB connection
│   ├── controllers/
│   │   ├── authController.js   # Authentication logic
│   │   ├── feedbackController.js
│   │   └── menuController.js
│   ├── models/
│   │   ├── User.js
│   │   ├── Feedback.js
│   │   └── Menu.js
│   ├── routes/
│   │   ├── authRoutes.js
│   │   ├── feedbackRoutes.js
│   │   └── menuRoutes.js
│   ├── middleware/
│   │   └── auth.js            # JWT authentication
│   └── server.js              # Entry point
├── .env
└── package.json
```

## 🚀 Setup

1. Install dependencies:
```bash
cd backend
npm install
```

2. Install MongoDB:
- Download from https://www.mongodb.com/try/download/community
- Or use MongoDB Atlas (cloud)

3. Update `.env` file with your MongoDB URI

4. Start server:
```bash
npm run dev
```

Server runs on http://localhost:5000

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login user

### Feedback
- `POST /api/feedback/submit` - Submit feedback (protected)
- `GET /api/feedback/all` - Get all feedback (protected)
- `GET /api/feedback/user` - Get user feedback (protected)

### Menu
- `POST /api/menu/create` - Create menu (protected)
- `GET /api/menu/all` - Get all menus
- `PUT /api/menu/update/:id` - Update menu (protected)

## 🔐 Authentication

Protected routes require JWT token in header:
```
Authorization: Bearer <token>
```

## 📦 Dependencies

- express - Web framework
- mongoose - MongoDB ODM
- bcryptjs - Password hashing
- jsonwebtoken - JWT authentication
- cors - CORS middleware
- dotenv - Environment variables
