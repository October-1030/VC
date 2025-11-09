import { motion } from 'framer-motion';
import { CreditCard, Shield } from 'lucide-react';

interface VirtualCardProps {
  cardNumber?: string;
  cardHolder?: string;
  expiryDate?: string;
  brand?: 'VISA' | 'MASTERCARD';
  cardType?: 'gold' | 'black' | 'platinum';
}

export default function VirtualCard({
  cardNumber = '•••• •••• •••• 8888',
  cardHolder = 'VAULT CARD MEMBER',
  expiryDate = '12/28',
  brand = 'VISA',
  cardType = 'gold'
}: VirtualCardProps) {
  // 卡片样式配置
  const cardStyles = {
    gold: {
      gradient: 'bg-gradient-to-br from-yellow-600 via-amber-500 to-yellow-700',
      pattern1: 'bg-yellow-400',
      pattern2: 'bg-orange-500',
      chipGradient: 'from-yellow-300 to-yellow-500',
      chipBorder: 'border-yellow-600',
      textColor: 'text-amber-900',
      secondaryText: 'text-amber-800',
      numberText: 'text-amber-900',
      shine: true
    },
    black: {
      gradient: 'bg-gradient-to-br from-gray-900 via-gray-800 to-black',
      pattern1: 'bg-purple-500',
      pattern2: 'bg-blue-500',
      chipGradient: 'from-yellow-400 to-yellow-600',
      chipBorder: 'border-yellow-700',
      textColor: 'text-white',
      secondaryText: 'text-gray-400',
      numberText: 'text-white',
      shine: false
    },
    platinum: {
      gradient: 'bg-gradient-to-br from-gray-300 via-gray-400 to-gray-500',
      pattern1: 'bg-cyan-400',
      pattern2: 'bg-blue-400',
      chipGradient: 'from-gray-200 to-gray-400',
      chipBorder: 'border-gray-600',
      textColor: 'text-gray-900',
      secondaryText: 'text-gray-700',
      numberText: 'text-gray-900',
      shine: true
    }
  };

  const style = cardStyles[cardType];

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
        className={`w-full h-full rounded-2xl p-6 ${style.gradient}
                   shadow-2xl relative overflow-hidden transform-gpu`}
        style={{
          transformStyle: 'preserve-3d',
        }}
      >
        {/* Metallic Shine Effect (for gold and platinum) */}
        {style.shine && (
          <div className="absolute inset-0 bg-gradient-to-br from-white/30 via-transparent to-transparent opacity-40" />
        )}

        {/* Background Pattern */}
        <div className="absolute inset-0 opacity-10">
          <div className={`absolute top-0 right-0 w-64 h-64 ${style.pattern1} rounded-full blur-3xl`} />
          <div className={`absolute bottom-0 left-0 w-48 h-48 ${style.pattern2} rounded-full blur-3xl`} />
        </div>

        {/* Chip */}
        <div className="absolute top-6 left-6">
          <div className={`w-12 h-10 bg-gradient-to-br ${style.chipGradient} rounded-md
                         shadow-lg flex items-center justify-center`}>
            <div className={`w-8 h-6 border ${style.chipBorder} rounded-sm`} />
          </div>
        </div>

        {/* Brand Logo */}
        <div className="absolute top-6 right-6">
          <span className={`text-2xl font-bold tracking-wider ${style.textColor}`}>
            {brand}
          </span>
        </div>

        {/* VaultCard Logo */}
        <div className="absolute top-6 right-6 mr-20">
          <Shield className={style.textColor} size={20} />
        </div>

        {/* Card Number */}
        <div className="absolute bottom-20 left-6 right-6">
          <p className={`${style.numberText} text-xl font-mono tracking-[0.3em] drop-shadow-lg font-bold`}>
            {cardNumber}
          </p>
        </div>

        {/* Card Holder and Expiry */}
        <div className="absolute bottom-6 left-6 right-6 flex justify-between items-end">
          <div>
            <p className={`${style.secondaryText} text-xs mb-1 font-semibold`}>CARD HOLDER</p>
            <p className={`${style.textColor} text-sm font-bold tracking-wider`}>
              {cardHolder}
            </p>
          </div>
          <div className="text-right">
            <p className={`${style.secondaryText} text-xs mb-1 font-semibold`}>EXPIRES</p>
            <p className={`${style.textColor} text-sm font-bold`}>{expiryDate}</p>
          </div>
        </div>

        {/* Decorative Elements */}
        <div className="absolute bottom-6 right-6 opacity-20">
          <CreditCard size={48} className={style.textColor} />
        </div>

        {/* Security Hologram Effect */}
        <div className="absolute top-1/2 right-8 w-16 h-16 rounded-full
                      bg-gradient-to-br from-white/40 to-transparent opacity-30 blur-sm" />
      </motion.div>
    </motion.div>
  );
}
