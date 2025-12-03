import { motion } from 'framer-motion';
import { Shield, CreditCard, Lock, Globe, Zap, HeadphonesIcon } from 'lucide-react';
import { Link } from 'react-router-dom';

export default function LandingPageEN() {
  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-gray-50 to-gray-100">
      {/* Header */}
      <header className="py-6 px-8">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            className="flex items-center space-x-3"
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

          <motion.nav
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            className="hidden md:flex items-center space-x-8"
          >
            <a href="#features" className="text-gray-600 hover:text-teal-600 transition-colors">
              Features
            </a>
            <a href="#pricing" className="text-gray-600 hover:text-teal-600 transition-colors">
              Pricing
            </a>
            <a href="#about" className="text-gray-600 hover:text-teal-600 transition-colors">
              About
            </a>
            <Link to="/" className="text-gray-600 hover:text-teal-600 transition-colors">
              中文
            </Link>
          </motion.nav>
        </div>
      </header>

      {/* Hero Section */}
      <main className="flex-1">
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
                <h1 className="text-5xl font-bold text-gray-800 mb-6">
                  Your Digital Payment
                  <span className="block bg-gradient-to-r from-teal-600 to-cyan-600 bg-clip-text text-transparent">
                    Vault
                  </span>
                </h1>
                <p className="text-xl text-gray-600 mb-8">
                  Secure virtual cards for global subscriptions.
                  Subscribe to ChatGPT, Netflix, Spotify, and more with ease.
                </p>
                <div className="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start">
                  <Link to="/register" className="btn-primary px-8 py-3 text-lg">
                    Get Started
                  </Link>
                  <a href="#features" className="btn-secondary px-8 py-3 text-lg border-2 border-teal-600 text-teal-600 rounded-xl hover:bg-teal-50 transition-colors text-center">
                    Learn More
                  </a>
                </div>
              </motion.div>

              {/* Right Section - Card Display */}
              <motion.div
                initial={{ opacity: 0, scale: 0.9 }}
                animate={{ opacity: 1, scale: 1 }}
                transition={{ duration: 0.6, delay: 0.2 }}
                className="flex justify-center"
              >
                <div className="relative">
                  {/* Virtual Card Preview */}
                  <div className="w-80 h-48 bg-gradient-to-br from-amber-400 via-yellow-500 to-amber-600 rounded-2xl shadow-2xl p-6 transform rotate-3 hover:rotate-0 transition-transform duration-300">
                    <div className="flex justify-between items-start mb-8">
                      <Shield className="text-white/80" size={32} />
                      <span className="text-white font-bold text-lg">VaultCard</span>
                    </div>
                    <div className="text-white/90 font-mono text-lg tracking-wider mb-4">
                      •••• •••• •••• 8888
                    </div>
                    <div className="flex justify-between items-end">
                      <div>
                        <p className="text-white/60 text-xs">CARDHOLDER</p>
                        <p className="text-white font-semibold">VAULT USER</p>
                      </div>
                      <div>
                        <p className="text-white/60 text-xs">EXPIRES</p>
                        <p className="text-white font-semibold">12/28</p>
                      </div>
                    </div>
                  </div>
                </div>
              </motion.div>
            </div>
          </div>
        </section>

        {/* Features Section */}
        <section id="features" className="px-8 py-16 bg-white">
          <div className="max-w-7xl mx-auto">
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              className="text-center mb-12"
            >
              <h2 className="text-3xl font-bold text-gray-800 mb-4">Why Choose VaultCard?</h2>
              <p className="text-gray-600 text-lg">Secure, fast, and reliable virtual card services</p>
            </motion.div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
              {[
                {
                  icon: Lock,
                  title: 'Secure Payments',
                  description: 'Your transactions are protected with bank-level encryption and security protocols.',
                  gradient: 'from-blue-500 to-cyan-500',
                },
                {
                  icon: Zap,
                  title: 'Instant Setup',
                  description: 'Get your virtual card in minutes. No lengthy applications or waiting periods.',
                  gradient: 'from-purple-500 to-pink-500',
                },
                {
                  icon: Globe,
                  title: 'Global Acceptance',
                  description: 'Use your card anywhere Visa/Mastercard is accepted worldwide.',
                  gradient: 'from-orange-500 to-red-500',
                },
                {
                  icon: CreditCard,
                  title: 'Flexible Limits',
                  description: 'Set and adjust your spending limits based on your needs.',
                  gradient: 'from-green-500 to-teal-500',
                },
                {
                  icon: Shield,
                  title: 'Privacy Protection',
                  description: 'Keep your real card details private when shopping online.',
                  gradient: 'from-indigo-500 to-purple-500',
                },
                {
                  icon: HeadphonesIcon,
                  title: '24/7 Support',
                  description: 'Our dedicated team is always ready to help you with any questions.',
                  gradient: 'from-pink-500 to-rose-500',
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
                    className="bg-gray-50 rounded-2xl p-6 hover:shadow-lg transition-shadow duration-300"
                  >
                    <div className={`w-14 h-14 rounded-full bg-gradient-to-br ${feature.gradient}
                                   flex items-center justify-center mb-4 shadow-lg`}>
                      <Icon className="text-white" size={28} />
                    </div>
                    <h3 className="text-xl font-bold text-gray-800 mb-2">{feature.title}</h3>
                    <p className="text-gray-600">{feature.description}</p>
                  </motion.div>
                );
              })}
            </div>
          </div>
        </section>

        {/* Pricing Section */}
        <section id="pricing" className="px-8 py-16">
          <div className="max-w-7xl mx-auto">
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              className="text-center mb-12"
            >
              <h2 className="text-3xl font-bold text-gray-800 mb-4">Simple, Transparent Pricing</h2>
              <p className="text-gray-600 text-lg">No hidden fees, no surprises</p>
            </motion.div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-4xl mx-auto">
              <motion.div
                initial={{ opacity: 0, x: -20 }}
                whileInView={{ opacity: 1, x: 0 }}
                viewport={{ once: true }}
                className="bg-white rounded-2xl p-8 shadow-lg"
              >
                <h3 className="text-2xl font-bold text-gray-800 mb-2">Card Issuance</h3>
                <div className="text-4xl font-bold text-teal-600 mb-4">$10<span className="text-lg text-gray-500">/card</span></div>
                <ul className="space-y-3 text-gray-600">
                  <li className="flex items-center"><span className="text-teal-500 mr-2">✓</span> Instant virtual card</li>
                  <li className="flex items-center"><span className="text-teal-500 mr-2">✓</span> Valid for 3 years</li>
                  <li className="flex items-center"><span className="text-teal-500 mr-2">✓</span> Works globally</li>
                </ul>
              </motion.div>

              <motion.div
                initial={{ opacity: 0, x: 20 }}
                whileInView={{ opacity: 1, x: 0 }}
                viewport={{ once: true }}
                className="bg-white rounded-2xl p-8 shadow-lg"
              >
                <h3 className="text-2xl font-bold text-gray-800 mb-2">Top-up Fee</h3>
                <div className="text-4xl font-bold text-teal-600 mb-4">1-2%<span className="text-lg text-gray-500">/transaction</span></div>
                <ul className="space-y-3 text-gray-600">
                  <li className="flex items-center"><span className="text-teal-500 mr-2">✓</span> Multiple payment methods</li>
                  <li className="flex items-center"><span className="text-teal-500 mr-2">✓</span> Instant balance update</li>
                  <li className="flex items-center"><span className="text-teal-500 mr-2">✓</span> No minimum amount</li>
                </ul>
              </motion.div>
            </div>
          </div>
        </section>

        {/* About Section */}
        <section id="about" className="px-8 py-16 bg-white">
          <div className="max-w-4xl mx-auto text-center">
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
            >
              <h2 className="text-3xl font-bold text-gray-800 mb-6">About VaultCard</h2>
              <p className="text-gray-600 text-lg mb-8">
                VaultCard is a virtual card service designed for users who need secure and convenient
                payment solutions for international subscriptions and online services. Our platform
                leverages Stripe's secure payment infrastructure to provide reliable virtual cards
                that work with all major online services.
              </p>
              <div className="flex justify-center gap-8 text-gray-500">
                <div>
                  <p className="text-3xl font-bold text-teal-600">Stripe</p>
                  <p className="text-sm">Payment Partner</p>
                </div>
                <div>
                  <p className="text-3xl font-bold text-teal-600">256-bit</p>
                  <p className="text-sm">SSL Encryption</p>
                </div>
                <div>
                  <p className="text-3xl font-bold text-teal-600">24/7</p>
                  <p className="text-sm">Support</p>
                </div>
              </div>
            </motion.div>
          </div>
        </section>

        {/* CTA Section */}
        <section className="px-8 py-16">
          <div className="max-w-4xl mx-auto text-center">
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              className="bg-gradient-to-r from-teal-600 to-cyan-600 rounded-3xl p-12 text-white"
            >
              <h2 className="text-3xl font-bold mb-4">Ready to Get Started?</h2>
              <p className="text-xl mb-8 text-white/90">
                Join thousands of users who trust VaultCard for their online payments.
              </p>
              <Link to="/register" className="inline-block bg-white text-teal-600 px-8 py-3 rounded-xl font-semibold text-lg hover:bg-gray-100 transition-colors">
                Create Your Card Now
              </Link>
            </motion.div>
          </div>
        </section>
      </main>

      {/* Footer */}
      <footer className="py-8 px-8 bg-gray-800 text-white">
        <div className="max-w-7xl mx-auto">
          <div className="flex flex-col md:flex-row justify-between items-center">
            <div className="flex items-center space-x-3 mb-4 md:mb-0">
              <div className="w-8 h-8 bg-gradient-to-br from-teal-600 to-cyan-600 rounded-lg
                            flex items-center justify-center">
                <Shield className="text-white" size={18} />
              </div>
              <span className="text-xl font-bold">VaultCard</span>
            </div>
            <div className="flex space-x-6 text-gray-400 text-sm">
              <a href="#" className="hover:text-white transition-colors">Privacy Policy</a>
              <a href="#" className="hover:text-white transition-colors">Terms of Service</a>
              <a href="mailto:richard@vaultcard.vip" className="hover:text-white transition-colors">Contact</a>
            </div>
          </div>
          <div className="text-center text-gray-500 text-sm mt-6">
            <p>Copyright © 2025 VaultCard · All Rights Reserved</p>
            <p className="mt-1">Email: richard@vaultcard.vip</p>
          </div>
        </div>
      </footer>
    </div>
  );
}
