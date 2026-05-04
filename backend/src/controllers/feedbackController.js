const Feedback = require('../models/Feedback');

exports.submitFeedback = async (req, res) => {
  try {
    const { ratings, alternates } = req.body;
    const userId = req.user.userId;

    const feedback = new Feedback({
      userId,
      ratings,
      alternates
    });

    await feedback.save();
    res.status(201).json({ message: 'Feedback submitted successfully', feedback });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

exports.getAllFeedback = async (req, res) => {
  try {
    const feedback = await Feedback.find().populate('userId', 'username');
    res.json(feedback);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

exports.getUserFeedback = async (req, res) => {
  try {
    const userId = req.user.userId;
    const feedback = await Feedback.find({ userId });
    res.json(feedback);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};
