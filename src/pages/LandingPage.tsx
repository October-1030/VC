import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import {
  Shield,
  Code,
  TestTube,
  Settings,
  Database,
  Terminal,
} from 'lucide-react';
import Layout from '../components/Layout';

// updated for Stripe-safe wording - Internal Developer Prototype Landing Page
export default function LandingPage() {
  return (
    <Layout>
      {/* Hero Section - updated for Stripe-safe wording */}
      <section className="px-8 py-16">
        <div className="max-w-7xl mx-auto">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            {/* Left Section - Text Content */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
              className="text-center lg:text-left"
            >
              {/* updated for Stripe-safe wording - Hero Title */}
              <h1 className="text-4xl md:text-5xl font-bold text-gray-800 mb-6">
                VaultCard
                <span className="block text-2xl md:text-3xl mt-2 bg-gradient-to-r from-teal-600 to-cyan-600 bg-clip-text text-transparent">
                  Internal Developer Prototype
                </span>
              </h1>

              {/* updated for Stripe-safe wording - Hero Subtitle */}
              <p className="text-lg text-gray-600 mb-8 leading-relaxed">
                An internal tool prototype for exploring Stripe Issuing integrations,
                spend controls, and virtual card workflows. Not a public service.
                No consumer onboarding or financial products are offered.
              </p>

              {/* updated for Stripe-safe wording - CTA Button */}
              <div className="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start">
                <Link
                  to="/login"
                  className="bg-teal-600 hover:bg-teal-700 text-white px-8 py-3 rounded-xl
                           font-semibold text-lg transition-colors text-center"
                >
                  Sign In (Internal Users Only)
                </Link>
              </div>

              {/* updated for Stripe-safe wording - Access restriction notice */}
              <p className="text-sm text-gray-500 mt-4">
                Access is restricted to invited internal test accounts.
                Public sign-up is not available.
              </p>
            </motion.div>

            {/* Right Section - Card Display */}
            <motion.div
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.6, delay: 0.2 }}
              className="flex justify-center"
            >
              <div className="relative">
                {/* Test Card Preview - updated for Stripe-safe wording */}
                <div className="w-80 h-48 bg-gradient-to-br from-gray-700 via-gray-800 to-gray-900 rounded-2xl shadow-2xl p-6 transform rotate-3 hover:rotate-0 transition-transform duration-300">
                  <div className="flex justify-between items-start mb-8">
                    <Shield className="text-teal-400" size={32} />
                    <div className="text-right">
                      <span className="text-white font-bold text-lg">VaultCard</span>
                      {/* updated for Stripe-safe wording */}
                      <p className="text-gray-400 text-xs">TEST MODE</p>
                    </div>
                  </div>
                  {/* updated for Stripe-safe wording - Only show last 4 digits */}
                  <div className="text-gray-400 font-mono text-lg tracking-wider mb-4">
                    •••• •••• •••• 4242
                  </div>
                  <div className="flex justify-between items-end">
                    <div>
                      <p className="text-gray-500 text-xs">SANDBOX</p>
                      <p className="text-gray-300 font-semibold">TEST USER</p>
                    </div>
                    <div>
                      <p className="text-gray-500 text-xs">TEST</p>
                      <p className="text-gray-300 font-semibold">12/28</p>
                    </div>
                  </div>
                </div>
              </div>
            </motion.div>
          </div>
        </div>
      </section>

      {/* About Section - updated for Stripe-safe wording */}
      <section id="about" className="px-8 py-16 bg-white">
        <div className="max-w-4xl mx-auto text-center">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
          >
            <h2 className="text-3xl font-bold text-gray-800 mb-6">About This Prototype</h2>
            {/* updated for Stripe-safe wording - About text */}
            <p className="text-gray-600 text-lg leading-relaxed">
              VaultCard is an internal developer tool designed to test and validate workflows
              around Stripe Issuing, cardholder creation, and spend control features.
              The project serves as a technical sandbox for understanding Issuing APIs,
              webhook handling, and card lifecycle management in a controlled,
              non-production environment.
            </p>
          </motion.div>
        </div>
      </section>

      {/* Features Section - updated for Stripe-safe wording */}
      <section id="features" className="px-8 py-16">
        <div className="max-w-7xl mx-auto">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            className="text-center mb-12"
          >
            {/* updated for Stripe-safe wording */}
            <h2 className="text-3xl font-bold text-gray-800 mb-4">Developer Features</h2>
            <p className="text-gray-600 text-lg">Technical capabilities for internal testing</p>
          </motion.div>

          {/* updated for Stripe-safe wording - Feature list */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {[
              {
                icon: TestTube,
                title: 'Stripe Issuing Test Mode',
                description: 'Test integration with Stripe Issuing APIs using sandbox credentials.',
                gradient: 'from-blue-500 to-cyan-500',
              },
              {
                icon: Code,
                title: 'Internal Cardholder Provisioning',
                description: 'Internal cardholder provisioning workflow for development testing.',
                gradient: 'from-purple-500 to-pink-500',
              },
              {
                icon: Settings,
                title: 'Spend Controls Experimentation',
                description: 'Basic spend controls experimentation in a sandbox environment.',
                gradient: 'from-orange-500 to-red-500',
              },
              {
                icon: Terminal,
                title: 'Developer Dashboard',
                description: 'Developer-friendly dashboard for testing sandbox behavior.',
                gradient: 'from-green-500 to-teal-500',
              },
              {
                icon: Database,
                title: 'Webhook Testing',
                description: 'Test Stripe webhook events and event handling workflows.',
                gradient: 'from-indigo-500 to-purple-500',
              },
              {
                icon: Shield,
                title: 'No Real Payments',
                description: 'No real payments, no public onboarding, and no consumer functionality.',
                gradient: 'from-gray-500 to-gray-700',
              },
            ].map((feature, index) => {
              const Icon = feature.icon;
              return (
                <motion.div
                  key={index}
                  initial={{ opacity: 0, y: 20 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  viewport={{ once: true }}
                  transition={{ duration: 0.5, delay: 0.1 * index }}
                  className="bg-white rounded-2xl p-6 shadow-md hover:shadow-lg transition-shadow duration-300"
                >
                  <div className={`w-12 h-12 rounded-lg bg-gradient-to-br ${feature.gradient}
                                 flex items-center justify-center mb-4`}>
                    <Icon className="text-white" size={24} />
                  </div>
                  <h3 className="text-lg font-bold text-gray-800 mb-2">{feature.title}</h3>
                  <p className="text-gray-600 text-sm">{feature.description}</p>
                </motion.div>
              );
            })}
          </div>
        </div>
      </section>

      {/* Developer Notes Section */}
      <section id="docs" className="px-8 py-16 bg-white">
        <div className="max-w-4xl mx-auto">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
          >
            <h2 className="text-3xl font-bold text-gray-800 mb-6 text-center">Developer Notes</h2>
            <div className="bg-gray-50 rounded-xl p-6 font-mono text-sm">
              <p className="text-gray-600 mb-4"># VaultCard Internal Prototype</p>
              <ul className="space-y-2 text-gray-700">
                <li>• All API calls use Stripe Test Mode credentials</li>
                <li>• No real card numbers or sensitive data stored</li>
                <li>• Webhook endpoints configured for sandbox events</li>
                <li>• Access restricted to internal development team</li>
                <li>• See backend /api/payment/health for status</li>
              </ul>
            </div>
          </motion.div>
        </div>
      </section>
    </Layout>
  );
}
