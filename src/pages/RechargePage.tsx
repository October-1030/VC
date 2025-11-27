import { useState } from 'react';
import { motion } from 'framer-motion';
import api from '../services/api';

/**
 * 充值页面示例
 *
 * 展示如何使用API客户端进行支付
 * 前端完全不知道后端使用的是Stripe还是Marqeta！
 */
export default function RechargePage() {
  const [amount, setAmount] = useState(100);
  const [paymentMethod, setPaymentMethod] = useState<'alipay' | 'card' | 'wechat_pay'>('alipay');
  const [loading, setLoading] = useState(false);
  const [paymentUrl, setPaymentUrl] = useState<string | null>(null);
  const [estimatedCNY, setEstimatedCNY] = useState(0);

  // 当用户输入金额时，实时显示人民币金额
  const handleAmountChange = (value: number) => {
    setAmount(value);
    setEstimatedCNY(value * 7.26); // 这个汇率可以从后端API获取
  };

  // 处理充值
  const handleRecharge = async () => {
    setLoading(true);
    try {
      // 调用API创建支付
      const response = await api.createPayment({
        userId: 'user123', // 实际应该从登录状态获取
        amountUSD: amount,
        paymentMethod: paymentMethod,
        description: '账户充值',
      });

      console.log('Payment created:', response);

      // 如果有支付URL（Alipay/WeChat），跳转到支付页面
      if (response.paymentUrl) {
        window.location.href = response.paymentUrl;
      }

      setPaymentUrl(response.paymentUrl || null);
      setEstimatedCNY(response.estimatedCNY);

    } catch (error) {
      console.error('Payment failed:', error);
      alert('充值失败，请重试');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-cyan-50 via-teal-50 to-emerald-50">
      <div className="max-w-2xl mx-auto px-8 py-16">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="card"
        >
          <h2 className="text-2xl font-bold text-gray-800 mb-6">账户充值</h2>

          {/* 充值金额 */}
          <div className="mb-6">
            <label className="block text-gray-700 font-semibold mb-2">
              充值金额（美元）
            </label>
            <input
              type="number"
              value={amount}
              onChange={(e) => handleAmountChange(Number(e.target.value))}
              className="input-field"
              min="1"
            />
            <p className="text-sm text-gray-500 mt-2">
              预估支付：¥{estimatedCNY.toFixed(2)}
            </p>
          </div>

          {/* 支付方式 */}
          <div className="mb-6">
            <label className="block text-gray-700 font-semibold mb-2">
              支付方式
            </label>
            <div className="grid grid-cols-3 gap-4">
              <button
                onClick={() => setPaymentMethod('alipay')}
                className={`p-4 border-2 rounded-lg transition-all ${
                  paymentMethod === 'alipay'
                    ? 'border-teal-600 bg-teal-50'
                    : 'border-gray-200 hover:border-teal-300'
                }`}
              >
                <div className="text-center">
                  <div className="text-2xl mb-2">💳</div>
                  <div className="font-semibold">支付宝</div>
                </div>
              </button>

              <button
                onClick={() => setPaymentMethod('wechat_pay')}
                className={`p-4 border-2 rounded-lg transition-all ${
                  paymentMethod === 'wechat_pay'
                    ? 'border-teal-600 bg-teal-50'
                    : 'border-gray-200 hover:border-teal-300'
                }`}
              >
                <div className="text-center">
                  <div className="text-2xl mb-2">💬</div>
                  <div className="font-semibold">微信支付</div>
                </div>
              </button>

              <button
                onClick={() => setPaymentMethod('card')}
                className={`p-4 border-2 rounded-lg transition-all ${
                  paymentMethod === 'card'
                    ? 'border-teal-600 bg-teal-50'
                    : 'border-gray-200 hover:border-teal-300'
                }`}
              >
                <div className="text-center">
                  <div className="text-2xl mb-2">💰</div>
                  <div className="font-semibold">信用卡</div>
                </div>
              </button>
            </div>
          </div>

          {/* 充值按钮 */}
          <button
            onClick={handleRecharge}
            disabled={loading}
            className="btn-primary w-full"
          >
            {loading ? '处理中...' : `充值 $${amount} USD`}
          </button>

          {/* 说明 */}
          <div className="mt-6 p-4 bg-gray-50 rounded-lg">
            <p className="text-sm text-gray-600">
              ✅ 使用官方跨境支付渠道
              <br />
              ✅ 资金安全有保障
              <br />
              ✅ 符合外汇管理规定
              <br />
              ✅ 占用个人年度外汇额度
            </p>
          </div>
        </motion.div>
      </div>
    </div>
  );
}
