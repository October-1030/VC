import { useState } from 'react';
import { motion } from 'framer-motion';
import { Eye, EyeOff, Lock, User } from 'lucide-react';

export default function LoginForm() {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    remember: false,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Login submitted:', formData);
    // TODO: Implement login logic
  };

  return (
    <motion.div
      initial={{ opacity: 0, x: 20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.6, delay: 0.2 }}
      className="card w-full max-w-md"
    >
      <div className="mb-8">
        <h2 className="text-2xl font-bold text-gray-800 mb-2">欢迎回来</h2>
        <p className="text-gray-600 text-sm">登录以继续使用服务</p>
      </div>

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Username Input */}
        <div className="relative">
          <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
            <User size={20} />
          </div>
          <input
            type="text"
            placeholder="请输入手机号或邮箱"
            value={formData.username}
            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
            className="input-field pl-10"
            required
          />
        </div>

        {/* Password Input */}
        <div className="relative">
          <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
            <Lock size={20} />
          </div>
          <input
            type={showPassword ? 'text' : 'password'}
            placeholder="请输入密码"
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

        {/* Remember Me & Forgot Password */}
        <div className="flex items-center justify-between text-sm">
          <label className="flex items-center space-x-2 cursor-pointer">
            <input
              type="checkbox"
              checked={formData.remember}
              onChange={(e) => setFormData({ ...formData, remember: e.target.checked })}
              className="w-4 h-4 text-teal-600 border-gray-300 rounded focus:ring-teal-500"
            />
            <span className="text-gray-600">记住密码</span>
          </label>
          <a href="/reset-password" className="text-teal-600 hover:text-teal-700">
            忘记密码？
          </a>
        </div>

        {/* Login Button */}
        <button type="submit" className="btn-primary w-full">
          登 录
        </button>

        {/* Register Link */}
        <div className="text-center text-sm text-gray-600">
          还没有账号？
          <a href="/register" className="text-teal-600 hover:text-teal-700 font-semibold ml-1">
            立即注册
          </a>
        </div>
      </form>
    </motion.div>
  );
}
