import { Shield } from 'lucide-react';

// updated for Stripe-safe wording - Disclaimer footer
export default function Footer() {
  return (
    <footer className="py-8 px-8 bg-gray-800 text-white">
      <div className="max-w-7xl mx-auto">
        {/* Logo and Links */}
        <div className="flex flex-col md:flex-row justify-between items-center mb-6">
          <div className="flex items-center space-x-3 mb-4 md:mb-0">
            <div className="w-8 h-8 bg-gradient-to-br from-teal-600 to-cyan-600 rounded-lg
                          flex items-center justify-center">
              <Shield className="text-white" size={18} />
            </div>
            <div className="flex flex-col">
              <span className="text-lg font-bold">VaultCard</span>
              {/* updated for Stripe-safe wording */}
              <span className="text-xs text-gray-400">Internal Developer Prototype</span>
            </div>
          </div>
          <div className="flex space-x-6 text-gray-400 text-sm">
            <a href="#" className="hover:text-white transition-colors">Privacy Policy</a>
            <a href="#" className="hover:text-white transition-colors">Terms of Service</a>
            <a href="mailto:richard@vaultcard.vip" className="hover:text-white transition-colors">Contact</a>
          </div>
        </div>

        {/* updated for Stripe-safe wording - Disclaimer Section */}
        <div className="border-t border-gray-700 pt-6 mt-6">
          <div className="bg-gray-700/50 rounded-lg p-4 mb-4">
            <h4 className="text-sm font-semibold text-gray-300 mb-2">Disclaimer</h4>
            <p className="text-xs text-gray-400 leading-relaxed">
              VaultCard is an internal prototype created solely for development and testing purposes.
              It is not a public product and does not issue real payment cards or provide financial services.
              All Stripe Issuing interactions use official Stripe APIs in test mode or with appropriately
              approved accounts. No real cardholder data is stored or processed.
            </p>
          </div>
        </div>

        {/* Copyright */}
        <div className="text-center text-gray-500 text-sm">
          <p>Copyright © 2025 VaultCard · Internal Use Only</p>
        </div>
      </div>
    </footer>
  );
}
