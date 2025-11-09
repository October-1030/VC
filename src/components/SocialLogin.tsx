import { motion } from 'framer-motion';

const socialPlatforms = [
  { name: 'Facebook', color: '#1877f2', logo: 'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/facebook.svg' },
  { name: 'TikTok', color: '#000000', logo: 'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/tiktok.svg' },
  { name: 'Google', color: '#ea4335', logo: 'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/google.svg' },
  { name: 'ChatGPT', color: '#10a37f', logo: 'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/openai.svg' },
];

export default function SocialLogin() {
  const handleSocialLogin = (platform: string) => {
    console.log(`Login with ${platform}`);
    // TODO: Implement OAuth login
  };

  return (
    <div className="mt-8">
      <div className="flex items-center mb-6">
        <div className="flex-1 border-t border-gray-300"></div>
        <span className="px-4 text-gray-500 text-sm">或使用第三方登录</span>
        <div className="flex-1 border-t border-gray-300"></div>
      </div>

      <div className="grid grid-cols-4 gap-4">
        {socialPlatforms.map((platform, index) => (
          <motion.button
            key={platform.name}
            initial={{ opacity: 0, scale: 0.8 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.3, delay: 0.1 * index }}
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.95 }}
            onClick={() => handleSocialLogin(platform.name)}
            className="aspect-square rounded-xl bg-white shadow-md hover:shadow-lg
                     flex items-center justify-center transition-all duration-200
                     border border-gray-200 hover:border-gray-300"
            style={{
              backgroundColor: 'white',
            }}
          >
            <div className="w-8 h-8 flex items-center justify-center">
              <img
                src={platform.logo}
                alt={platform.name}
                className="w-6 h-6"
                style={{ filter: `drop-shadow(0 0 2px ${platform.color})` }}
              />
            </div>
          </motion.button>
        ))}
      </div>

      <p className="text-center text-xs text-gray-500 mt-4">
        点击即表示同意
        <a href="/terms" className="text-purple-600 hover:underline mx-1">服务条款</a>
        和
        <a href="/privacy" className="text-purple-600 hover:underline mx-1">隐私政策</a>
      </p>
    </div>
  );
}
