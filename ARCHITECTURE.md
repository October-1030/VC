# VaultCard 架构设计文档

## 📐 核心设计理念

VaultCard采用**前后端完全分离 + Provider模式**架构，实现以下目标：

1. ✅ **前端无感知**：前端完全不知道后端使用哪个支付服务商
2. ✅ **随时可切换**：通过修改配置文件即可切换Stripe/Marqeta等服务商
3. ✅ **隐藏实现细节**：竞争对手无法通过前端代码看出你使用的API
4. ✅ **易于扩展**：新增支付服务商只需实现PaymentProvider接口
5. ✅ **完全合规**：使用官方API，符合监管要求

---

## 🏗️ 系统架构图

```
┌──────────────────────────────────────────────────────────────┐
│                        用户浏览器                              │
│  React前端 (只知道统一的REST API)                              │
│  - src/services/api.ts                                        │
│  - src/pages/Dashboard.tsx                                   │
│  - src/pages/RechargePage.tsx                                │
└────────────────┬─────────────────────────────────────────────┘
                 │ HTTPS REST API
                 │ /api/payment/*
                 ▼
┌──────────────────────────────────────────────────────────────┐
│             Spring Boot 中间件层（核心代理）                   │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Controller Layer (REST API入口)                     │    │
│  │  - PaymentController.java                           │    │
│  │  - 处理HTTP请求                                      │    │
│  │  - 统一异常处理                                      │    │
│  └────────────────────┬────────────────────────────────┘    │
│                       │                                       │
│  ┌────────────────────▼───────────────────────────────┐    │
│  │  Service Layer (业务逻辑)                           │    │
│  │  - PaymentService.java                              │    │
│  │  - KYC验证                                          │    │
│  │  - 风控检测                                         │    │
│  │  - 数据持久化                                       │    │
│  └────────────────────┬────────────────────────────────┘    │
│                       │                                       │
│  ┌────────────────────▼───────────────────────────────┐    │
│  │  Provider Interface (抽象层) ★核心★                 │    │
│  │  - PaymentProvider.java                             │    │
│  │  - 定义统一的接口规范                               │    │
│  └────────────────────┬────────────────────────────────┘    │
│                       │                                       │
│       ┌───────────────┼───────────────┐                     │
│       │               │               │                     │
│  ┌────▼────┐   ┌──────▼─────┐  ┌─────▼─────┐              │
│  │ Stripe  │   │  Marqeta   │  │   Mock    │              │
│  │ Provider│   │  Provider  │  │  Provider │              │
│  │ (当前)  │   │  (备用)    │  │  (测试)   │              │
│  └─────────┘   └────────────┘  └───────────┘              │
└────┬──────────────────────────────────────────────────────┘
     │ 外部API调用（HTTPS）
     │ (前端看不到这一层！)
     ▼
┌──────────────────────────────────────────────────────────────┐
│              第三方支付服务商                                  │
│  - Stripe Issuing (api.stripe.com)                           │
│  - Marqeta (api.marqeta.com)                                 │
│  - Lithic (api.lithic.com)                                   │
└──────────────────────────────────────────────────────────────┘
```

---

## 🔑 核心优势

### 1. 前端完全解耦

**前端代码示例：**
```typescript
// 前端只需要调用统一的API，不知道底层实现
import api from '@/services/api';

// 创建支付
const response = await api.createPayment({
  userId: currentUser.id,
  amountUSD: 100,
  paymentMethod: 'alipay',
  description: '充值'
});

// 前端完全不知道这是Stripe还是Marqeta！
```

### 2. 配置驱动切换

**application.yml配置文件：**
```yaml
payment:
  provider:
    active: stripe  # 改成 marqeta 即可切换！

    stripe:
      secret-key: sk_live_xxx
      webhook-secret: whsec_xxx

    marqeta:
      app-token: app_token_xxx
      admin-token: admin_token_xxx
```

**切换步骤：**
1. 修改 `payment.provider.active: marqeta`
2. 重启Spring Boot应用
3. ✅ 前端代码无需任何修改！

### 3. 隐藏实现细节

**竞争对手看到的：**
- ❌ 看不到前端有Stripe.js引用
- ❌ 看不到任何Stripe/Marqeta的API Key
- ❌ 看不到具体的API调用
- ✅ 只能看到你的统一REST API：`/api/payment/create`

**实际的API调用（在后端）：**
```java
// StripePaymentProvider.java
// 这些代码在服务器端，前端看不到！
PaymentIntent intent = PaymentIntent.create(
    PaymentIntentCreateParams.builder()
        .setAmount(amount)
        .setCurrency("usd")
        .addPaymentMethodType("alipay")
        .build()
);
```

---

## 📦 项目结构

```
virtual-payment-mvp/
├── src/                           # 前端 (React)
│   ├── services/
│   │   └── api.ts                 # ★ API客户端（前端只用这个）
│   ├── pages/
│   │   ├── Dashboard.tsx          # 仪表板
│   │   ├── RechargePage.tsx       # 充值页面
│   │   └── ...
│   └── components/
│       └── VirtualCard.tsx
│
└── backend/                       # 后端 (Spring Boot)
    ├── src/main/java/com/vaultcard/
    │   ├── controller/
    │   │   └── PaymentController.java    # REST API入口
    │   │
    │   ├── service/
    │   │   └── PaymentService.java       # 业务逻辑
    │   │
    │   ├── provider/                     # ★ Provider层（核心）
    │   │   ├── PaymentProvider.java      # 接口定义
    │   │   ├── StripePaymentProvider.java # Stripe实现
    │   │   └── MarqetaPaymentProvider.java # Marqeta实现
    │   │
    │   ├── dto/                          # 数据传输对象
    │   │   ├── PaymentIntentRequest.java
    │   │   ├── PaymentIntentResponse.java
    │   │   ├── CardResponse.java
    │   │   └── ...
    │   │
    │   └── VaultCardApplication.java     # 启动类
    │
    ├── src/main/resources/
    │   └── application.yml               # ★ 配置文件（切换Provider）
    │
    └── pom.xml                           # Maven依赖
```

---

## 🔄 如何切换Provider

### **场景：从Stripe切换到Marqeta**

#### 步骤1：实现MarqetaProvider

```java
@Component("marqetaProvider")
public class MarqetaPaymentProvider implements PaymentProvider {

    @Override
    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest req) {
        // 调用Marqeta API
        // POST https://api.marqeta.com/v3/fundingsources
        ...
    }

    @Override
    public CardResponse createCard(CreateCardRequest req) {
        // POST https://api.marqeta.com/v3/cards
        ...
    }

    // 实现其他接口方法...
}
```

#### 步骤2：修改配置文件

```yaml
# application.yml
payment:
  provider:
    active: marqeta  # 从 stripe 改成 marqeta

    marqeta:
      app-token: ${MARQETA_APP_TOKEN}
      admin-token: ${MARQETA_ADMIN_TOKEN}
      base-url: https://api.marqeta.com/v3
```

#### 步骤3：重启应用

```bash
cd backend
mvn spring-boot:run
```

#### 步骤4：前端无需任何修改！

```typescript
// 前端代码完全不变，依然这样调用：
const response = await api.createPayment({
  userId: 'user123',
  amountUSD: 100,
  paymentMethod: 'alipay',
  description: '充值'
});

// 但后端已经切换到Marqeta了！
```

---

## 🛡️ 安全性设计

### 1. API Key安全

**❌ 错误做法（暴露在前端）：**
```javascript
// 前端直接调用Stripe - 危险！
const stripe = Stripe('pk_live_xxx'); // API Key暴露！
```

**✅ 正确做法（隐藏在后端）：**
```java
// 后端配置
@Value("${payment.provider.stripe.secret-key}")
private String stripeSecretKey; // 环境变量，不提交到Git

// 前端只调用你的API
await api.createPayment(...); // 安全！
```

### 2. Webhook验证

```java
@Override
public boolean verifyWebhookSignature(String payload, String signature) {
    try {
        Webhook.constructEvent(payload, signature, webhookSecret);
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

### 3. CORS配置

```java
@CrossOrigin(origins = "https://yourdomain.com") // 只允许你的域名
```

---

## 📊 性能优化

### 1. 连接池

```java
// OkHttp连接池（用于Marqeta）
OkHttpClient client = new OkHttpClient.Builder()
    .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
    .build();
```

### 2. 缓存策略

```java
// 缓存汇率，避免频繁查询
@Cacheable("exchange-rates")
public BigDecimal getExchangeRate() {
    // 查询实时汇率
}
```

---

## 🚀 部署指南

### 后端部署

```bash
# 构建
cd backend
mvn clean package

# 运行
java -jar target/vaultcard-backend-1.0.0.jar \
  --spring.profiles.active=prod \
  --payment.provider.stripe.secret-key=$STRIPE_SECRET_KEY \
  --payment.provider.stripe.webhook-secret=$STRIPE_WEBHOOK_SECRET
```

### 前端部署

```bash
# 构建
cd virtual-payment-mvp
npm run build

# 部署到Netlify/Vercel
# 设置环境变量 VITE_API_BASE_URL=https://api.yourdomain.com
```

---

## 🔍 监控与日志

### 查看当前使用的Provider

```bash
curl http://localhost:8080/api/payment/health
# 输出: OK - Using provider: stripe
```

### 日志示例

```
2025-11-10 10:30:15 - Creating payment for user user123 using provider: stripe
2025-11-10 10:30:16 - Payment succeeded: pi_xxx, user: user123
```

---

## 💡 最佳实践

### 1. 环境变量管理

```bash
# .env (不要提交到Git!)
STRIPE_SECRET_KEY=sk_live_xxx
STRIPE_WEBHOOK_SECRET=whsec_xxx
MARQETA_APP_TOKEN=app_token_xxx
MARQETA_ADMIN_TOKEN=admin_token_xxx
```

### 2. 测试策略

```java
// 使用MockProvider进行单元测试
@Component("mockProvider")
public class MockPaymentProvider implements PaymentProvider {
    // 返回假数据，不调用真实API
}
```

### 3. 逐步迁移

```java
// 可以根据用户ID决定使用哪个Provider
PaymentProvider provider;
if (userId.startsWith("beta_")) {
    provider = marqetaProvider; // Beta用户使用新Provider
} else {
    provider = stripeProvider; // 其他用户继续使用Stripe
}
```

---

## 📝 总结

VaultCard的架构设计核心思想：

1. **抽象层隔离**：前端永远不知道后端细节
2. **配置驱动**：通过配置文件控制行为
3. **Provider模式**：易于扩展和切换
4. **安全第一**：所有敏感信息在后端
5. **合规优先**：使用官方API，符合监管

**这就是安联付那样的架构！**竞争对手完全看不出你用的是什么技术栈。🎯
