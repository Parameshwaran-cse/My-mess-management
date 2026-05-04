const express = require('express');
const router = express.Router();
const authMiddleware = require('../middleware/auth');
const { createMenu, getAllMenus, updateMenu } = require('../controllers/menuController');

router.post('/create', authMiddleware, createMenu);
router.get('/all', getAllMenus);
router.put('/update/:id', authMiddleware, updateMenu);

module.exports = router;
