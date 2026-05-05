const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
};

const handleResponse = async (response) => {
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Something went wrong');
  }
  return data;
};

export const api = {
  auth: {
    login: async (username, password) => {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      return handleResponse(response);
    },
    register: async (username, password, email, fullName, phone, role) => {
      const response = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ 
          username, 
          password, 
          email, 
          fullName, 
          phone, 
          role: role.toUpperCase() 
        })
      });
      return handleResponse(response);
    }
  },
  users: {
    updateProfile: async (userData) => {
      const response = await fetch(`${API_URL}/users/me`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader()
        },
        body: JSON.stringify(userData)
      });
      return handleResponse(response);
    }
  },
  menu: {
    getAll: async () => {
      const response = await fetch(`${API_URL}/menu/all`);
      return handleResponse(response);
    },
    create: async (menuData) => {
      const response = await fetch(`${API_URL}/menu/create`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader()
        },
        body: JSON.stringify(menuData)
      });
      return handleResponse(response);
    }
  },
  feedback: {
    submit: async (ratings, alternates) => {
      const response = await fetch(`${API_URL}/feedback/submit`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader()
        },
        body: JSON.stringify({ ratings, alternates })
      });
      return handleResponse(response);
    },
    getAll: async () => {
      const response = await fetch(`${API_URL}/feedback/all`, {
        headers: getAuthHeader()
      });
      return handleResponse(response);
    },
    getUserFeedback: async () => {
      const response = await fetch(`${API_URL}/feedback/user`, {
        headers: getAuthHeader()
      });
      return handleResponse(response);
    }
  },
  admin: {
    getDashboard: async () => {
      const response = await fetch(`${API_URL}/admin/dashboard`, { headers: getAuthHeader() });
      return handleResponse(response);
    },
    getUsers: async () => {
      const response = await fetch(`${API_URL}/admin/users`, { headers: getAuthHeader() });
      return handleResponse(response);
    },
    getAllFeedback: async () => {
      const response = await fetch(`${API_URL}/admin/feedback`, { headers: getAuthHeader() });
      return handleResponse(response);
    },
    getAllBookings: async () => {
      const response = await fetch(`${API_URL}/admin/bookings`, { headers: getAuthHeader() });
      return handleResponse(response);
    },
    changeRole: async (id, role) => {
      const response = await fetch(`${API_URL}/admin/users/${id}/role?role=${role}`, {
        method: 'PUT',
        headers: getAuthHeader()
      });
      return handleResponse(response);
    },
    deleteUser: async (id) => {
      const response = await fetch(`${API_URL}/admin/users/${id}`, {
        method: 'DELETE',
        headers: getAuthHeader()
      });
      return handleResponse(response);
    }
  }
};
