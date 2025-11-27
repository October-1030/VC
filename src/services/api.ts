/**
 * VaultCard API Client
 *
 * 前端只知道这些API接口，完全不知道后端使用的是Stripe还是Marqeta
 * 即使后端切换了支付服务商，前端代码也不需要任何修改
 */

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// ============= Types =============

export interface PaymentIntentRequest {
  userId: string;
  amountUSD: number;
  paymentMethod: 'alipay' | 'card' | 'wechat_pay';
  description: string;
}

export interface PaymentIntentResponse {
  paymentId: string;
  clientSecret: string;
  amountUSD: number;
  estimatedCNY: number;
  status: string;
  paymentUrl?: string;
}

export interface CreateCardRequest {
  userId: string;
  cardholderName: string;
  spendingLimit: number;
  cardType: 'gold' | 'platinum' | 'black';
}

export interface CardResponse {
  cardId: string;
  cardNumber: string;
  cvv: string;
  expiryMonth: string;
  expiryYear: string;
  cardholderName: string;
  status: string;
  type: string;
  balance?: number;
  spendingLimit: number;
}

export interface Transaction {
  id: string;
  merchant: string;
  amount: number;
  currency: string;
  status: string;
  type: 'income' | 'expense';
  createdAt: string;
}

export interface TransactionListResponse {
  transactions: Transaction[];
  total: number;
  hasMore: boolean;
}

// ============= API Functions =============

class VaultCardAPI {
  private baseUrl: string;

  constructor(baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  /**
   * 创建支付（充值）
   */
  async createPayment(request: PaymentIntentRequest): Promise<PaymentIntentResponse> {
    const response = await fetch(`${this.baseUrl}/api/payment/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      throw new Error('Payment creation failed');
    }

    return response.json();
  }

  /**
   * 创建虚拟卡
   */
  async createCard(request: CreateCardRequest): Promise<CardResponse> {
    const response = await fetch(`${this.baseUrl}/api/payment/cards/create`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(request),
    });

    if (!response.ok) {
      throw new Error('Card creation failed');
    }

    return response.json();
  }

  /**
   * 获取卡片详情
   */
  async getCard(cardId: string, userId: string): Promise<CardResponse> {
    const response = await fetch(
      `${this.baseUrl}/api/payment/cards/${cardId}?userId=${userId}`,
      {
        method: 'GET',
      }
    );

    if (!response.ok) {
      throw new Error('Failed to get card details');
    }

    return response.json();
  }

  /**
   * 冻结卡片
   */
  async freezeCard(cardId: string, userId: string): Promise<CardResponse> {
    const response = await fetch(
      `${this.baseUrl}/api/payment/cards/${cardId}/freeze?userId=${userId}`,
      {
        method: 'POST',
      }
    );

    if (!response.ok) {
      throw new Error('Failed to freeze card');
    }

    return response.json();
  }

  /**
   * 解冻卡片
   */
  async unfreezeCard(cardId: string, userId: string): Promise<CardResponse> {
    const response = await fetch(
      `${this.baseUrl}/api/payment/cards/${cardId}/unfreeze?userId=${userId}`,
      {
        method: 'POST',
      }
    );

    if (!response.ok) {
      throw new Error('Failed to unfreeze card');
    }

    return response.json();
  }

  /**
   * 获取交易列表
   */
  async getTransactions(
    userId: string,
    cardId?: string,
    limit: number = 20,
    offset: number = 0
  ): Promise<TransactionListResponse> {
    const params = new URLSearchParams({
      userId,
      limit: limit.toString(),
      offset: offset.toString(),
    });

    if (cardId) {
      params.append('cardId', cardId);
    }

    const response = await fetch(
      `${this.baseUrl}/api/payment/transactions?${params.toString()}`,
      {
        method: 'GET',
      }
    );

    if (!response.ok) {
      throw new Error('Failed to get transactions');
    }

    return response.json();
  }

  /**
   * 健康检查
   */
  async healthCheck(): Promise<string> {
    const response = await fetch(`${this.baseUrl}/api/payment/health`);
    return response.text();
  }
}

// 导出单例
export const api = new VaultCardAPI(API_BASE_URL);

export default api;
