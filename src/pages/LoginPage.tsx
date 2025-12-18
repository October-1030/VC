import { motion } from 'framer-motion';
import { Shield } from 'lucide-react';
import { Link } from 'react-router-dom';
import LoginForm from '../components/LoginForm';

// updated for Stripe-safe wording - Internal Login Page
export default function LoginPage() {
  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-gray-50 to-gray-100">
      {/* updated for Stripe-safe banner */}
      <div className="bg-amber-100 border-b border-amber-200 px-4 py-2">
        <p className="text-center text-amber-800 text-sm font-medium">
          This site is an internal prototype for testing Stripe Issuing and spend-control workflows.
          It is not a public financial product and is accessible only to invited test users.
        </p>
      </div>

      {/* Header */}
      <header className="py-6 px-8">
        <div className="max-w-7xl mx-auto">
          <Link to="/">
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              className="flex items-center space-x-3 cursor-pointer w-fit"
            >
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
                <span className="text-xs text-gray-500">Internal Prototype</span>
              </div>
            </motion.div>
          </Link>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex items-center justify-center px-8 py-12">
        <div className="w-full max-w-md">
          <LoginForm />
        </div>
      </main>

      {/* Footer */}
      <footer className="py-6 text-center text-gray-500 text-sm">
        <p>VaultCard Internal Prototype · Not for public use</p>
      </footer>
    </div>
  );
}
