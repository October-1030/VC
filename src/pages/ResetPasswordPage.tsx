import { useState } from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import { Mail, ArrowLeft, Shield } from 'lucide-react';

export default function ResetPasswordPage() {
  const [email, setEmail] = useState('');
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Password reset requested for:', email);
    // TODO: Implement password reset logic
    setSubmitted(true);
  };

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
            <Link to="/" className="text-gray-600 hover:text-teal-600 flex items-center space-x-2">
              <ArrowLeft size={18} />
              <span>返回登录</span>
            </Link>
          </motion.nav>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex items-center justify-center px-8 py-12">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
          className="card w-full max-w-md"
        >
          {!submitted ? (
            <>
              <div className="mb-8 text-center">
                <div className="w-16 h-16 bg-gradient-to-br from-teal-600 to-cyan-600 rounded-full
                              flex items-center justify-center mx-auto mb-4 shadow-lg">
                  <Mail className="text-white" size={32} />
                </div>
                <h2 className="text-2xl font-bold text-gray-800 mb-2">忘记密码？</h2>
                <p className="text-gray-600 text-sm">
                  输入您的邮箱地址，我们将发送重置密码的链接
                </p>
              </div>

              <form onSubmit={handleSubmit} className="space-y-4">
                <div className="relative">
                  <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
                    <Mail size={20} />
                  </div>
                  <input
                    type="email"
                    placeholder="请输入您的邮箱"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="input-field pl-10"
                    required
                  />
                </div>

                <button type="submit" className="btn-primary w-full">
                  发送重置链接
                </button>

                <div className="text-center text-sm text-gray-600">
                  记起密码了？
                  <Link to="/" className="text-teal-600 hover:text-teal-700 font-semibold ml-1">
                    立即登录
                  </Link>
                </div>
              </form>
            </>
          ) : (
            <div className="text-center py-8">
              <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <Mail className="text-green-600" size={32} />
              </div>
              <h2 className="text-2xl font-bold text-gray-800 mb-2">邮件已发送</h2>
              <p className="text-gray-600 mb-6">
                我们已向 <span className="font-semibold">{email}</span> 发送了重置密码的链接，
                请查收邮件并按照指示操作。
              </p>
              <p className="text-gray-500 text-sm mb-6">
                没有收到邮件？请检查垃圾邮件文件夹或
                <button
                  onClick={() => setSubmitted(false)}
                  className="text-teal-600 hover:text-teal-700 font-semibold ml-1"
                >
                  重新发送
                </button>
              </p>
              <Link to="/" className="btn-primary inline-block">
                返回登录页面
              </Link>
            </div>
          )}
        </motion.div>
      </main>

      {/* Footer */}
      <footer className="py-6 text-center text-gray-500 text-sm">
        <p>Copyright © 2025 VaultCard · All Rights Reserved</p>
      </footer>
    </div>
  );
}
