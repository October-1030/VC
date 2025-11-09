import { motion } from 'framer-motion';
import { CreditCard } from 'lucide-react';

interface VirtualCardProps {
  cardNumber?: string;
  cardHolder?: string;
  expiryDate?: string;
  brand?: 'VISA' | 'MASTERCARD';
}

export default function VirtualCard({
  cardNumber = '•••• •••• •••• 8888',
  cardHolder = 'DINANTE CARD',
  expiryDate = '12/28',
  brand = 'VISA'
}: VirtualCardProps) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6 }}
      className="relative w-[400px] h-[250px] perspective-1000"
    >
      <motion.div
        whileHover={{ rotateY: 10, rotateX: -5, scale: 1.02 }}
        transition={{ type: 'spring', stiffness: 300, damping: 20 }}
        className="w-full h-full rounded-2xl p-6 bg-gradient-to-br from-gray-900 via-gray-800 to-black
                   shadow-2xl relative overflow-hidden transform-gpu"
        style={{
          transformStyle: 'preserve-3d',
        }}
      >
        {/* Background Pattern */}
        <div className="absolute inset-0 opacity-10">
          <div className="absolute top-0 right-0 w-64 h-64 bg-purple-500 rounded-full blur-3xl" />
          <div className="absolute bottom-0 left-0 w-48 h-48 bg-blue-500 rounded-full blur-3xl" />
        </div>

        {/* Chip */}
        <div className="absolute top-6 left-6">
          <div className="w-12 h-10 bg-gradient-to-br from-yellow-400 to-yellow-600 rounded-md
                         shadow-lg flex items-center justify-center">
            <div className="w-8 h-6 border border-yellow-700 rounded-sm" />
          </div>
        </div>

        {/* Brand Logo */}
        <div className="absolute top-6 right-6">
          <span className="text-white text-2xl font-bold tracking-wider">
            {brand}
          </span>
        </div>

        {/* Card Number */}
        <div className="absolute bottom-20 left-6 right-6">
          <p className="text-white text-xl font-mono tracking-[0.3em] drop-shadow-lg">
            {cardNumber}
          </p>
        </div>

        {/* Card Holder and Expiry */}
        <div className="absolute bottom-6 left-6 right-6 flex justify-between items-end">
          <div>
            <p className="text-gray-400 text-xs mb-1">CARD HOLDER</p>
            <p className="text-white text-sm font-semibold tracking-wider">
              {cardHolder}
            </p>
          </div>
          <div className="text-right">
            <p className="text-gray-400 text-xs mb-1">EXPIRES</p>
            <p className="text-white text-sm font-semibold">{expiryDate}</p>
          </div>
        </div>

        {/* Decorative Elements */}
        <div className="absolute bottom-6 right-6 opacity-20">
          <CreditCard size={48} className="text-white" />
        </div>
      </motion.div>
    </motion.div>
  );
}
