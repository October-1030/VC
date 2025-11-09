import { motion } from 'framer-motion';
import { Rocket, Gift, Star } from 'lucide-react';

const features = [
  {
    icon: Rocket,
    title: '🚀快速开通',
    description: '一分钟完成注册',
    gradient: 'from-blue-500 to-cyan-500',
  },
  {
    icon: Gift,
    title: '💎新人特权',
    description: '专享优惠礼包',
    gradient: 'from-purple-500 to-pink-500',
  },
  {
    icon: Star,
    title: '⭐会员服务',
    description: '尊享VIP特权',
    gradient: 'from-orange-500 to-red-500',
  },
];

export default function FeatureCards() {
  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-12">
      {features.map((feature, index) => {
        const Icon = feature.icon;
        return (
          <motion.div
            key={index}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.1 * index }}
            whileHover={{ y: -5, scale: 1.02 }}
            className="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 cursor-pointer"
          >
            <div className={`w-14 h-14 rounded-full bg-gradient-to-br ${feature.gradient}
                           flex items-center justify-center mb-4 shadow-lg`}>
              <Icon className="text-white" size={28} />
            </div>
            <h3 className="text-lg font-bold text-gray-800 mb-2">
              {feature.title}
            </h3>
            <p className="text-gray-600 text-sm">
              {feature.description}
            </p>
          </motion.div>
        );
      })}
    </div>
  );
}
