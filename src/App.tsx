import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import './index.css';

// updated for Stripe-safe routing - Internal prototype routes only
// Removed: /register, /reset-password, /en, /recharge (public-facing routes)
function App() {
  return (
    <Router>
      <Routes>
        {/* Main landing page - Internal developer prototype */}
        <Route path="/" element={<LandingPage />} />

        {/* Login page - Internal users only */}
        <Route path="/login" element={<LoginPage />} />

        {/* Dashboard - Authenticated internal users */}
        <Route path="/dashboard" element={<Dashboard />} />

        {/* Fallback - redirect to landing */}
        <Route path="*" element={<LandingPage />} />
      </Routes>
    </Router>
  );
}

export default App;
