const Menu = require('../models/Menu');

exports.createMenu = async (req, res) => {
  try {
    const { mealType, items, alternates } = req.body;
    const menu = new Menu({ mealType, items, alternates });
    await menu.save();
    res.status(201).json({ message: 'Menu created successfully', menu });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

exports.getAllMenus = async (req, res) => {
  try {
    const menus = await Menu.find();
    res.json(menus);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

exports.updateMenu = async (req, res) => {
  try {
    const { id } = req.params;
    const menu = await Menu.findByIdAndUpdate(id, req.body, { new: true });
    res.json({ message: 'Menu updated successfully', menu });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};
