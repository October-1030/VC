import { useState } from 'react';
import { motion } from 'framer-motion';
import { Link, useNavigate } from 'react-router-dom';
import {
  Shield,
  CreditCard,
  Activity,
  Settings,
  LogOut,
  Plus,
  Eye,
  EyeOff,
  ArrowUpRight,
  ArrowDownLeft,
  Freeze,
} from 'lucide-react';
import VirtualCard from '../components/VirtualCard';

export default function Dashboard() {
  const navigate = useNavigate();
  const [showBalance, setShowBalance] = useState(true);
  const [activeTab, setActiveTab] = useState('overview');

  // Mock data
  const balance = 15234.56;
  const recentTransactions = [
    { id: 1, type: 'expense', merchant: 'Amazon.com', amount: -89.99, date: '2025-01-08', status: 'completed' },
    { id: 2, type: 'expense', merchant: 'Netflix', amount: -15.99, date: '2025-01-07', status: 'completed' },
    { id: 3, type: 'income', merchant: '充值', amount: 1000.00, date: '2025-01-06', status: 'completed' },
    { id: 4, type: 'expense', merchant: 'Spotify', amount: -9.99, date: '2025-01-05', status: 'completed' },
  ];

  const handleLogout = () => {
    console.log('Logout');
    // TODO: Implement logout logic
    navigate('/');
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-cyan-50 via-teal-50 to-emerald-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-8 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <div className="w-10 h-10 bg-gradient-to-br from-teal-600 to-cyan-600 rounded-lg
                            flex items-center justify-center shadow-lg">
                <Shield className="text-white" size={24} />
              </div>
              <span className="text-2xl font-bold bg-gradient-to-r from-teal-600 to-cyan-600
                             bg-clip-text text-transparent">
                VaultCard
              </span>
            </div>

            <div className="flex items-center space-x-4">
              <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors">
                <Settings size={20} className="text-gray-600" />
              </button>
              <button
                onClick={handleLogout}
                className="flex items-center space-x-2 px-4 py-2 text-gray-600 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
              >
                <LogOut size={20} />
                <span>退出登录</span>
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-8 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Left Section - Cards and Balance */}
          <div className="lg:col-span-2 space-y-6">
            {/* Balance Card */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              className="card"
            >
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-xl font-bold text-gray-800">账户余额</h2>
                <button
                  onClick={() => setShowBalance(!showBalance)}
                  className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                >
                  {showBalance ? <Eye size={20} /> : <EyeOff size={20} />}
                </button>
              </div>
              <div className="mb-6">
                <p className="text-4xl font-bold bg-gradient-to-r from-teal-600 to-cyan-600 bg-clip-text text-transparent">
                  {showBalance ? `¥${balance.toFixed(2)}` : '¥****.**'}
                </p>
                <p className="text-gray-500 text-sm mt-1">可用余额</p>
              </div>
              <div className="flex space-x-4">
                <button className="btn-primary flex-1 flex items-center justify-center space-x-2">
                  <Plus size={20} />
                  <span>充值</span>
                </button>
                <button className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold py-3 px-6 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <ArrowUpRight size={20} />
                  <span>转账</span>
                </button>
              </div>
            </motion.div>

            {/* Virtual Cards */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.1 }}
              className="card"
            >
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-xl font-bold text-gray-800">我的虚拟卡</h2>
                <button className="text-teal-600 hover:text-teal-700 font-semibold flex items-center space-x-1">
                  <Plus size={20} />
                  <span>创建新卡</span>
                </button>
              </div>
              <div className="flex justify-center">
                <VirtualCard
                  cardType="gold"
                  cardNumber="4532 **** **** 8888"
                  cardHolder="JOHN DOE"
                  expiryDate="12/28"
                />
              </div>
              <div className="mt-6 flex space-x-4">
                <button className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <Freeze size={18} />
                  <span>冻结卡片</span>
                </button>
                <button className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <Settings size={18} />
                  <span>卡片设置</span>
                </button>
              </div>
            </motion.div>

            {/* Recent Transactions */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
              className="card"
            >
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-xl font-bold text-gray-800">最近交易</h2>
                <Link to="/transactions" className="text-teal-600 hover:text-teal-700 font-semibold text-sm">
                  查看全部
                </Link>
              </div>
              <div className="space-y-4">
                {recentTransactions.map((transaction) => (
                  <div
                    key={transaction.id}
                    className="flex items-center justify-between p-4 hover:bg-gray-50 rounded-lg transition-colors"
                  >
                    <div className="flex items-center space-x-4">
                      <div
                        className={`w-10 h-10 rounded-full flex items-center justify-center ${
                          transaction.type === 'income'
                            ? 'bg-green-100'
                            : 'bg-red-100'
                        }`}
                      >
                        {transaction.type === 'income' ? (
                          <ArrowDownLeft size={20} className="text-green-600" />
                        ) : (
                          <ArrowUpRight size={20} className="text-red-600" />
                        )}
                      </div>
                      <div>
                        <p className="font-semibold text-gray-800">{transaction.merchant}</p>
                        <p className="text-sm text-gray-500">{transaction.date}</p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p
                        className={`font-bold ${
                          transaction.type === 'income' ? 'text-green-600' : 'text-red-600'
                        }`}
                      >
                        {transaction.type === 'income' ? '+' : ''}¥{Math.abs(transaction.amount).toFixed(2)}
                      </p>
                      <p className="text-sm text-gray-500">{transaction.status === 'completed' ? '已完成' : '处理中'}</p>
                    </div>
                  </div>
                ))}
              </div>
            </motion.div>
          </div>

          {/* Right Section - Quick Actions and Stats */}
          <div className="space-y-6">
            {/* Quick Actions */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.1 }}
              className="card"
            >
              <h2 className="text-xl font-bold text-gray-800 mb-4">快捷操作</h2>
              <div className="space-y-3">
                <button className="w-full flex items-center space-x-3 p-4 hover:bg-teal-50 rounded-lg transition-colors text-left">
                  <div className="w-10 h-10 bg-teal-100 rounded-lg flex items-center justify-center">
                    <CreditCard size={20} className="text-teal-600" />
                  </div>
                  <div>
                    <p className="font-semibold text-gray-800">创建虚拟卡</p>
                    <p className="text-sm text-gray-500">一键生成新的虚拟卡</p>
                  </div>
                </button>
                <button className="w-full flex items-center space-x-3 p-4 hover:bg-cyan-50 rounded-lg transition-colors text-left">
                  <div className="w-10 h-10 bg-cyan-100 rounded-lg flex items-center justify-center">
                    <Activity size={20} className="text-cyan-600" />
                  </div>
                  <div>
                    <p className="font-semibold text-gray-800">交易记录</p>
                    <p className="text-sm text-gray-500">查看所有交易详情</p>
                  </div>
                </button>
                <button className="w-full flex items-center space-x-3 p-4 hover:bg-purple-50 rounded-lg transition-colors text-left">
                  <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                    <Settings size={20} className="text-purple-600" />
                  </div>
                  <div>
                    <p className="font-semibold text-gray-800">账户设置</p>
                    <p className="text-sm text-gray-500">管理个人信息和安全</p>
                  </div>
                </button>
              </div>
            </motion.div>

            {/* Monthly Stats */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.2 }}
              className="card"
            >
              <h2 className="text-xl font-bold text-gray-800 mb-4">本月统计</h2>
              <div className="space-y-4">
                <div>
                  <div className="flex items-center justify-between mb-2">
                    <span className="text-gray-600">总支出</span>
                    <span className="font-bold text-red-600">¥2,145.67</span>
                  </div>
                  <div className="w-full bg-gray-200 rounded-full h-2">
                    <div className="bg-red-500 h-2 rounded-full" style={{ width: '65%' }}></div>
                  </div>
                </div>
                <div>
                  <div className="flex items-center justify-between mb-2">
                    <span className="text-gray-600">总收入</span>
                    <span className="font-bold text-green-600">¥5,000.00</span>
                  </div>
                  <div className="w-full bg-gray-200 rounded-full h-2">
                    <div className="bg-green-500 h-2 rounded-full" style={{ width: '100%' }}></div>
                  </div>
                </div>
                <div className="pt-4 border-t">
                  <div className="flex items-center justify-between">
                    <span className="text-gray-600">净收入</span>
                    <span className="font-bold text-teal-600">¥2,854.33</span>
                  </div>
                </div>
              </div>
            </motion.div>
          </div>
        </div>
      </main>
    </div>
  );
}
