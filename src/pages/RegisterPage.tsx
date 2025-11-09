import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import RegisterForm from '../components/RegisterForm';
import VirtualCard from '../components/VirtualCard';
import SocialLogin from '../components/SocialLogin';
import { Shield } from 'lucide-react';

export default function RegisterPage() {
  return (
    <div className="min-h-screen flex flex-col">
      {/* Header */}
      <header className="py-6 px-8">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <Link to="/">
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              className="flex items-center space-x-3 cursor-pointer"
            >
              <div className="w-10 h-10 bg-gradient-to-br from-teal-600 to-cyan-600 rounded-lg
                            flex items-center justify-center shadow-lg">
                <Shield className="text-white" size={24} />
              </div>
              <span className="text-2xl font-bold bg-gradient-to-r from-teal-600 to-cyan-600
                             bg-clip-text text-transparent">
                VaultCard
              </span>
            </motion.div>
          </Link>

          <motion.nav
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            className="flex items-center space-x-4"
          >
            <span className="text-gray-600">已有账号？</span>
            <Link to="/" className="text-teal-600 hover:text-teal-700 font-semibold">
              立即登录
            </Link>
          </motion.nav>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex items-center justify-center px-8 py-12">
        <div className="max-w-7xl w-full">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            {/* Left Section - Card Display */}
            <div className="flex flex-col items-center lg:items-start space-y-8">
              <VirtualCard cardType="platinum" />

              <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.6, delay: 0.3 }}
                className="text-center lg:text-left"
              >
                <h1 className="text-4xl font-bold text-gray-800 mb-4">
                  创建账户
                </h1>
                <h2 className="text-5xl font-bold bg-gradient-to-r from-teal-600 to-cyan-600
                             bg-clip-text text-transparent mb-6">
                  开启数字支付之旅
                </h2>
                <p className="text-gray-600 text-lg">
                  几分钟即可完成注册 · 立即体验安全便捷的虚拟卡服务
                </p>
              </motion.div>

              {/* Social Login Buttons */}
              <div className="w-full max-w-md">
                <SocialLogin />
              </div>
            </div>

            {/* Right Section - Register Form */}
            <div className="flex justify-center lg:justify-end">
              <RegisterForm />
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="py-6 text-center text-gray-500 text-sm">
        <p>Copyright © 2025 VaultCard · All Rights Reserved</p>
      </footer>
    </div>
  );
}
