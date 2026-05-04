const express = require('express');
const router = express.Router();
const authMiddleware = require('../middleware/auth');
const { submitFeedback, getAllFeedback, getUserFeedback } = require('../controllers/feedbackController');

router.post('/submit', authMiddleware, submitFeedback);
router.get('/all', authMiddleware, getAllFeedback);
router.get('/user', authMiddleware, getUserFeedback);

module.exports = router;
