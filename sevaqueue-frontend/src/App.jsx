import React from 'react';
import { Routes, Route, Navigate, Link, useNavigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import OfficeDetails from './components/OfficeDetails';
import MyTokens from './components/MyTokens';
import ManageOffice from './components/ManageOffice';
import Profile from './components/Profile';
import { useAuth } from './context/AuthContext';

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar glass-card" style={{ borderRadius: '0 0 16px 16px', borderTop: 'none', marginTop: 0 }}>
      <div style={{ fontSize: '1.5rem', fontWeight: 'bold', background: 'linear-gradient(to right, #8b5cf6, #6366f1)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
        SevaQueue
      </div>
      <div className="nav-links">
        {user ? (
          <>
            <Link to="/dashboard">Dashboard</Link>
            <Link to="/profile">Profile</Link>
            <a href="#" onClick={handleLogout}>Logout</a>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
};

const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();

  if (loading) return <div>Loading...</div>;
  if (!user) return <Navigate to="/login" />;

  return children;
};

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/office/:id"
          element={
            <ProtectedRoute>
              <OfficeDetails />
            </ProtectedRoute>
          }
        />
        <Route
          path="/my-tokens"
          element={
            <ProtectedRoute>
              <MyTokens />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/office/:id"
          element={
            <ProtectedRoute>
              <ManageOffice />
            </ProtectedRoute>
          }
        />
        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />
        <Route path="/" element={<Navigate to="/dashboard" />} />
      </Routes>
    </>
  );
}

export default App;
