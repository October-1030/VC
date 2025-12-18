import { useState } from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { Eye, EyeOff, Lock, User, AlertCircle } from 'lucide-react';

// updated for Stripe-safe wording - Internal Login Form
export default function LoginForm() {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    remember: false,
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      // TODO: Implement actual authentication logic
      console.log('Login submitted:', formData);
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 500));
      // After successful login, redirect to dashboard
      navigate('/dashboard');
    } catch {
      setError('Authentication failed. Please check your credentials.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6 }}
      className="bg-white rounded-2xl shadow-xl p-8 w-full"
    >
      {/* updated for Stripe-safe wording - Header */}
      <div className="mb-6">
        <h2 className="text-2xl font-bold text-gray-800 mb-2">Internal Sign In</h2>
        {/* updated for Stripe-safe wording - Restricted access notice */}
        <div className="bg-gray-50 border border-gray-200 rounded-lg p-3 mt-3">
          <p className="text-gray-600 text-sm flex items-start">
            <AlertCircle size={16} className="text-gray-500 mr-2 mt-0.5 flex-shrink-0" />
            Only invited internal users can sign in. Public registration is disabled.
          </p>
        </div>
      </div>

      {error && (
        <div className="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg text-red-700 text-sm">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Username Input */}
        <div className="relative">
          <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
            <User size={20} />
          </div>
          <input
            type="text"
            placeholder="Email or username"
            value={formData.username}
            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
            className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2
                     focus:ring-teal-500 focus:border-transparent transition-all"
            required
            disabled={isLoading}
          />
        </div>

        {/* Password Input */}
        <div className="relative">
          <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
            <Lock size={20} />
          </div>
          <input
            type={showPassword ? 'text' : 'password'}
            placeholder="Password"
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
            className="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2
                     focus:ring-teal-500 focus:border-transparent transition-all"
            required
            disabled={isLoading}
          />
          <button
            type="button"
            onClick={() => setShowPassword(!showPassword)}
            className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600"
            disabled={isLoading}
          >
            {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
          </button>
        </div>

        {/* Remember Me */}
        <div className="flex items-center justify-between text-sm">
          <label className="flex items-center space-x-2 cursor-pointer">
            <input
              type="checkbox"
              checked={formData.remember}
              onChange={(e) => setFormData({ ...formData, remember: e.target.checked })}
              className="w-4 h-4 text-teal-600 border-gray-300 rounded focus:ring-teal-500"
              disabled={isLoading}
            />
            <span className="text-gray-600">Remember me</span>
          </label>
        </div>

        {/* Login Button - updated for Stripe-safe wording */}
        <button
          type="submit"
          disabled={isLoading}
          className="w-full bg-teal-600 hover:bg-teal-700 disabled:bg-teal-400
                   text-white font-semibold py-3 px-6 rounded-lg transition-colors"
        >
          {isLoading ? 'Signing in...' : 'Sign In'}
        </button>

        {/* updated for Stripe-safe wording - No registration link */}
        <div className="text-center text-sm text-gray-500 pt-2">
          <p>This is an internal developer tool.</p>
          <p className="text-xs mt-1">Contact admin for access credentials.</p>
        </div>
      </form>
    </motion.div>
  );
}
