import { motion } from 'framer-motion';
import VirtualCard from '../components/VirtualCard';
import LoginForm from '../components/LoginForm';
import FeatureCards from '../components/FeatureCards';
import SocialLogin from '../components/SocialLogin';

export default function LandingPage() {
  return (
    <div className="min-h-screen flex flex-col">
      {/* Header */}
      <header className="py-6 px-8">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            className="flex items-center space-x-3"
          >
            <div className="w-10 h-10 bg-gradient-to-br from-purple-600 to-indigo-600 rounded-lg
                          flex items-center justify-center shadow-lg">
              <span className="text-white font-bold text-xl">A</span>
            </div>
            <span className="text-2xl font-bold bg-gradient-to-r from-purple-600 to-indigo-600
                           bg-clip-text text-transparent">
              AnLianFu
            </span>
          </motion.div>

          <motion.nav
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            className="hidden md:flex items-center space-x-8"
          >
            <a href="#features" className="text-gray-600 hover:text-purple-600 transition-colors">
              功能特性
            </a>
            <a href="#pricing" className="text-gray-600 hover:text-purple-600 transition-colors">
              价格方案
            </a>
            <a href="#about" className="text-gray-600 hover:text-purple-600 transition-colors">
              关于我们
            </a>
          </motion.nav>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex items-center justify-center px-8 py-12">
        <div className="max-w-7xl w-full">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            {/* Left Section - Card Display */}
            <div className="flex flex-col items-center lg:items-start space-y-8">
              <VirtualCard />

              <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.6, delay: 0.3 }}
                className="text-center lg:text-left"
              >
                <h1 className="text-4xl font-bold text-gray-800 mb-4">
                  快速注册
                </h1>
                <h2 className="text-5xl font-bold bg-gradient-to-r from-purple-600 to-indigo-600
                             bg-clip-text text-transparent mb-6">
                  开启体验
                </h2>
                <p className="text-gray-600 text-lg">
                  安全、便捷的虚拟支付解决方案
                </p>
              </motion.div>

              {/* Social Login Buttons */}
              <div className="w-full max-w-md">
                <SocialLogin />
              </div>
            </div>

            {/* Right Section - Login Form */}
            <div className="flex justify-center lg:justify-end">
              <LoginForm />
            </div>
          </div>

          {/* Feature Cards */}
          <FeatureCards />
        </div>
      </main>

      {/* Footer */}
      <footer className="py-6 text-center text-gray-500 text-sm">
        <p>Copyright © 2018-2025 All Rights Reserved</p>
      </footer>
    </div>
  );
}
