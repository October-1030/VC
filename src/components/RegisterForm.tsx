import { useState } from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { Eye, EyeOff, Lock, User, Mail } from 'lucide-react';

export default function RegisterForm() {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    agreeTerms: false,
  });

  const [errors, setErrors] = useState<{
    password?: string;
    confirmPassword?: string;
    agreeTerms?: string;
  }>({});

  const validateForm = () => {
    const newErrors: typeof errors = {};

    if (formData.password.length < 8) {
      newErrors.password = '密码至少需要8个字符';
    }

    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = '两次输入的密码不一致';
    }

    if (!formData.agreeTerms) {
      newErrors.agreeTerms = '请同意服务条款和隐私政策';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (validateForm()) {
      console.log('Register submitted:', formData);
      // TODO: Implement registration logic
      // After successful registration, redirect to dashboard
      navigate('/dashboard');
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, x: 20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.6, delay: 0.2 }}
      className="card w-full max-w-md"
    >
      <div className="mb-8">
        <h2 className="text-2xl font-bold text-gray-800 mb-2">注册账户</h2>
        <p className="text-gray-600 text-sm">填写信息开始使用</p>
      </div>

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Username Input */}
        <div className="relative">
          <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
            <User size={20} />
          </div>
          <input
            type="text"
            placeholder="请输入用户名"
            value={formData.username}
            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
            className="input-field pl-10"
            required
          />
        </div>

        {/* Email Input */}
        <div className="relative">
          <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
            <Mail size={20} />
          </div>
          <input
            type="email"
            placeholder="请输入邮箱"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            className="input-field pl-10"
            required
          />
        </div>

        {/* Password Input */}
        <div>
          <div className="relative">
            <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
              <Lock size={20} />
            </div>
            <input
              type={showPassword ? 'text' : 'password'}
              placeholder="请输入密码（至少8位）"
              value={formData.password}
              onChange={(e) => setFormData({ ...formData, password: e.target.value })}
              className="input-field pl-10 pr-10"
              required
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600"
            >
              {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
            </button>
          </div>
          {errors.password && (
            <p className="text-red-500 text-xs mt-1">{errors.password}</p>
          )}
        </div>

        {/* Confirm Password Input */}
        <div>
          <div className="relative">
            <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
              <Lock size={20} />
            </div>
            <input
              type={showConfirmPassword ? 'text' : 'password'}
              placeholder="请确认密码"
              value={formData.confirmPassword}
              onChange={(e) => setFormData({ ...formData, confirmPassword: e.target.value })}
              className="input-field pl-10 pr-10"
              required
            />
            <button
              type="button"
              onClick={() => setShowConfirmPassword(!showConfirmPassword)}
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600"
            >
              {showConfirmPassword ? <EyeOff size={20} /> : <Eye size={20} />}
            </button>
          </div>
          {errors.confirmPassword && (
            <p className="text-red-500 text-xs mt-1">{errors.confirmPassword}</p>
          )}
        </div>

        {/* Terms Agreement */}
        <div>
          <label className="flex items-start space-x-2 cursor-pointer">
            <input
              type="checkbox"
              checked={formData.agreeTerms}
              onChange={(e) => setFormData({ ...formData, agreeTerms: e.target.checked })}
              className="w-4 h-4 text-teal-600 border-gray-300 rounded focus:ring-teal-500 mt-1"
            />
            <span className="text-gray-600 text-sm">
              我已阅读并同意
              <a href="#" className="text-teal-600 hover:text-teal-700 mx-1">
                服务条款
              </a>
              和
              <a href="#" className="text-teal-600 hover:text-teal-700 mx-1">
                隐私政策
              </a>
            </span>
          </label>
          {errors.agreeTerms && (
            <p className="text-red-500 text-xs mt-1">{errors.agreeTerms}</p>
          )}
        </div>

        {/* Register Button */}
        <button type="submit" className="btn-primary w-full">
          立即注册
        </button>
      </form>
    </motion.div>
  );
}
