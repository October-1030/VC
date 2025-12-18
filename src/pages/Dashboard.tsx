import { useState } from 'react';
import { motion } from 'framer-motion';
import { Link, useNavigate } from 'react-router-dom';
import {
  Shield,
  CreditCard,
  Activity,
  Settings,
  LogOut,
  Eye,
  EyeOff,
  ArrowUpRight,
  ArrowDownLeft,
  Snowflake,
  AlertTriangle,
  TestTube,
} from 'lucide-react';

// updated for Stripe-safe wording - Internal Developer Dashboard
export default function Dashboard() {
  const navigate = useNavigate();
  const [showBalance, setShowBalance] = useState(true);

  // Mock test data - updated for Stripe-safe wording
  const balance = 1000.00; // Test balance in USD
  const recentTransactions = [
    { id: 1, type: 'expense', merchant: 'Test Merchant A', amount: -25.00, date: '2025-01-08', status: 'completed' },
    { id: 2, type: 'expense', merchant: 'Test Merchant B', amount: -15.99, date: '2025-01-07', status: 'completed' },
    { id: 3, type: 'income', merchant: 'Test Funding', amount: 500.00, date: '2025-01-06', status: 'completed' },
    { id: 4, type: 'expense', merchant: 'Test Merchant C', amount: -9.99, date: '2025-01-05', status: 'completed' },
  ];

  const handleLogout = () => {
    console.log('Logout');
    navigate('/');
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      {/* updated for Stripe-safe banner */}
      <div className="bg-amber-100 border-b border-amber-200 px-4 py-2">
        <p className="text-center text-amber-800 text-sm font-medium">
          This site is an internal prototype for testing Stripe Issuing and spend-control workflows.
          It is not a public financial product and is accessible only to invited test users.
        </p>
      </div>

      {/* Header - updated for Stripe-safe wording */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-8 py-4">
          <div className="flex items-center justify-between">
            <Link to="/" className="flex items-center space-x-3">
              <div className="w-10 h-10 bg-gradient-to-br from-teal-600 to-cyan-600 rounded-lg
                            flex items-center justify-center shadow-lg">
                <Shield className="text-white" size={24} />
              </div>
              <div className="flex flex-col">
                <span className="text-xl font-bold bg-gradient-to-r from-teal-600 to-cyan-600
                               bg-clip-text text-transparent">
                  VaultCard
                </span>
                {/* updated for Stripe-safe wording */}
                <span className="text-xs text-gray-500">Internal Dashboard</span>
              </div>
            </Link>

            <div className="flex items-center space-x-4">
              <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors">
                <Settings size={20} className="text-gray-600" />
              </button>
              <button
                onClick={handleLogout}
                className="flex items-center space-x-2 px-4 py-2 text-gray-600 hover:text-red-600
                         hover:bg-red-50 rounded-lg transition-colors"
              >
                <LogOut size={20} />
                <span>Sign Out</span>
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-8 py-8">
        {/* updated for Stripe-safe wording - Test Data Disclaimer */}
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6 flex items-start space-x-3"
        >
          <TestTube size={20} className="text-blue-600 flex-shrink-0 mt-0.5" />
          <div>
            <p className="text-blue-800 font-medium text-sm">Sandbox Environment</p>
            <p className="text-blue-700 text-sm">
              All cards shown here use Stripe test data. No real cardholder data is stored in this system.
            </p>
          </div>
        </motion.div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Left Section - Cards and Balance */}
          <div className="lg:col-span-2 space-y-6">
            {/* Balance Card - updated for Stripe-safe wording */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              className="bg-white rounded-2xl shadow-lg p-6"
            >
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-xl font-bold text-gray-800">Test Balance</h2>
                <button
                  onClick={() => setShowBalance(!showBalance)}
                  className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                >
                  {showBalance ? <Eye size={20} /> : <EyeOff size={20} />}
                </button>
              </div>
              <div className="mb-6">
                <p className="text-4xl font-bold bg-gradient-to-r from-teal-600 to-cyan-600
                            bg-clip-text text-transparent">
                  {showBalance ? `$${balance.toFixed(2)}` : '$****.**'}
                </p>
                {/* updated for Stripe-safe wording */}
                <p className="text-gray-500 text-sm mt-1">Test Mode Balance (USD)</p>
              </div>
              <div className="flex space-x-4">
                {/* updated for Stripe-safe wording - Changed from 充值/转账 */}
                <button className="flex-1 bg-teal-600 hover:bg-teal-700 text-white font-semibold
                                 py-3 px-6 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <TestTube size={20} />
                  <span>Test Funding</span>
                </button>
                <button className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold
                                 py-3 px-6 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <Activity size={20} />
                  <span>View Logs</span>
                </button>
              </div>
            </motion.div>

            {/* Virtual Cards - updated for Stripe-safe wording */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.1 }}
              className="bg-white rounded-2xl shadow-lg p-6"
            >
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-xl font-bold text-gray-800">Test Cards</h2>
                {/* updated for Stripe-safe wording */}
                <button className="text-teal-600 hover:text-teal-700 font-semibold flex items-center space-x-1">
                  <TestTube size={20} />
                  <span>Test Card Flow</span>
                </button>
              </div>

              {/* Test Card Display - updated for Stripe-safe wording */}
              <div className="flex justify-center">
                <div className="w-80 h-48 bg-gradient-to-br from-gray-700 via-gray-800 to-gray-900
                              rounded-2xl shadow-xl p-6 relative overflow-hidden">
                  {/* Test Mode Badge */}
                  <div className="absolute top-0 right-0 bg-amber-500 text-white text-xs px-2 py-1
                                font-semibold rounded-bl-lg">
                    TEST MODE
                  </div>

                  <div className="flex justify-between items-start mb-6">
                    <Shield className="text-teal-400" size={28} />
                    <span className="text-white font-bold">VaultCard</span>
                  </div>

                  {/* updated for Stripe-safe wording - Only show last 4 digits */}
                  {/* IMPORTANT: Never display full card number, even in test mode */}
                  <div className="text-gray-400 font-mono text-lg tracking-wider mb-4">
                    •••• •••• •••• 4242
                  </div>

                  <div className="flex justify-between items-end">
                    <div>
                      <p className="text-gray-500 text-xs">INTERNAL TEST</p>
                      <p className="text-gray-300 font-semibold">TEST USER</p>
                    </div>
                    <div>
                      <p className="text-gray-500 text-xs">EXPIRES</p>
                      <p className="text-gray-300 font-semibold">12/28</p>
                    </div>
                  </div>
                </div>
              </div>

              <div className="mt-6 flex space-x-4">
                {/* updated for Stripe-safe wording */}
                <button className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold
                                 py-2 px-4 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <Snowflake size={18} />
                  <span>Freeze Card</span>
                </button>
                <button className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold
                                 py-2 px-4 rounded-lg transition-colors flex items-center justify-center space-x-2">
                  <Settings size={18} />
                  <span>Card Settings</span>
                </button>
              </div>
            </motion.div>

            {/* Recent Transactions - updated for Stripe-safe wording */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
              className="bg-white rounded-2xl shadow-lg p-6"
            >
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-xl font-bold text-gray-800">Test Transactions</h2>
                <span className="text-gray-500 text-sm">Sandbox Data</span>
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
                        {transaction.type === 'income' ? '+' : ''}${Math.abs(transaction.amount).toFixed(2)}
                      </p>
                      <p className="text-sm text-gray-500">
                        {transaction.status === 'completed' ? 'Completed' : 'Pending'}
                      </p>
                    </div>
                  </div>
                ))}
              </div>
            </motion.div>
          </div>

          {/* Right Section - Quick Actions */}
          <div className="space-y-6">
            {/* Quick Actions - updated for Stripe-safe wording */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.1 }}
              className="bg-white rounded-2xl shadow-lg p-6"
            >
              <h2 className="text-xl font-bold text-gray-800 mb-4">Developer Actions</h2>
              <div className="space-y-3">
                {/* updated for Stripe-safe wording */}
                <button className="w-full flex items-center space-x-3 p-4 hover:bg-teal-50 rounded-lg transition-colors text-left">
                  <div className="w-10 h-10 bg-teal-100 rounded-lg flex items-center justify-center">
                    <CreditCard size={20} className="text-teal-600" />
                  </div>
                  <div>
                    <p className="font-semibold text-gray-800">Test Card Flow</p>
                    <p className="text-sm text-gray-500">Test card creation workflow</p>
                  </div>
                </button>
                <button className="w-full flex items-center space-x-3 p-4 hover:bg-cyan-50 rounded-lg transition-colors text-left">
                  <div className="w-10 h-10 bg-cyan-100 rounded-lg flex items-center justify-center">
                    <Activity size={20} className="text-cyan-600" />
                  </div>
                  <div>
                    <p className="font-semibold text-gray-800">Transaction Logs</p>
                    <p className="text-sm text-gray-500">View test transaction history</p>
                  </div>
                </button>
                <button className="w-full flex items-center space-x-3 p-4 hover:bg-purple-50 rounded-lg transition-colors text-left">
                  <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                    <Settings size={20} className="text-purple-600" />
                  </div>
                  <div>
                    <p className="font-semibold text-gray-800">Sandbox Settings</p>
                    <p className="text-sm text-gray-500">Configure test environment</p>
                  </div>
                </button>
              </div>
            </motion.div>

            {/* API Status - updated for Stripe-safe wording */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.2 }}
              className="bg-white rounded-2xl shadow-lg p-6"
            >
              <h2 className="text-xl font-bold text-gray-800 mb-4">API Status</h2>
              <div className="space-y-3">
                <div className="flex items-center justify-between">
                  <span className="text-gray-600">Stripe Test Mode</span>
                  <span className="flex items-center text-green-600 text-sm">
                    <span className="w-2 h-2 bg-green-500 rounded-full mr-2"></span>
                    Active
                  </span>
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-gray-600">Issuing API</span>
                  <span className="flex items-center text-green-600 text-sm">
                    <span className="w-2 h-2 bg-green-500 rounded-full mr-2"></span>
                    Connected
                  </span>
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-gray-600">Webhooks</span>
                  <span className="flex items-center text-green-600 text-sm">
                    <span className="w-2 h-2 bg-green-500 rounded-full mr-2"></span>
                    Listening
                  </span>
                </div>
              </div>
            </motion.div>

            {/* Warning Notice */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.3 }}
              className="bg-amber-50 border border-amber-200 rounded-lg p-4"
            >
              <div className="flex items-start space-x-3">
                <AlertTriangle size={20} className="text-amber-600 flex-shrink-0 mt-0.5" />
                <div>
                  <p className="text-amber-800 font-medium text-sm">Test Environment</p>
                  <p className="text-amber-700 text-xs mt-1">
                    This dashboard uses Stripe Test Mode. No real transactions will be processed.
                  </p>
                </div>
              </div>
            </motion.div>
          </div>
        </div>
      </main>
    </div>
  );
}
