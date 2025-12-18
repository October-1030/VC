import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { Shield, FileText } from 'lucide-react';

// updated for Stripe-safe wording - Internal tool navigation only
export default function Navbar() {
  return (
    <header className="py-4 px-8 bg-white border-b border-gray-200">
      <div className="max-w-7xl mx-auto flex items-center justify-between">
        {/* Logo */}
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

        {/* Navigation - updated for Stripe-safe wording: only internal tool links */}
        <motion.nav
          initial={{ opacity: 0, x: 20 }}
          animate={{ opacity: 1, x: 0 }}
          className="flex items-center space-x-6"
        >
          <Link
            to="/dashboard"
            className="text-gray-600 hover:text-teal-600 transition-colors flex items-center space-x-1"
          >
            <span>Internal Dashboard</span>
          </Link>
          <a
            href="#docs"
            className="text-gray-600 hover:text-teal-600 transition-colors flex items-center space-x-1"
          >
            <FileText size={16} />
            <span>Developer Notes</span>
          </a>
          {/* updated for Stripe-safe wording: Sign In only, no Sign Up/Register */}
          <Link
            to="/login"
            className="bg-teal-600 hover:bg-teal-700 text-white px-4 py-2 rounded-lg
                     font-medium transition-colors"
          >
            Sign In
          </Link>
        </motion.nav>
      </div>
    </header>
  );
}
