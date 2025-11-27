# 🚀 VaultCard 快速开始指南

## 📋 前置条件

- ✅ Node.js 18+（前端）
- ✅ Java 17+（后端）
- ✅ Maven 3.8+（后端）
- ✅ Stripe 测试账户

---

## ⚡ 3分钟快速启动

### 步骤 1：注册 Stripe 测试账户

1. 访问：https://dashboard.stripe.com/register
2. 填写邮箱和密码，完成注册
3. 确保左上角显示 **"Test mode"** 开关已开启

### 步骤 2：获取 Stripe API 密钥

1. 在 Stripe Dashboard，点击 **"Developers"** → **"API keys"**
2. 或直接访问：https://dashboard.stripe.com/test/apikeys
3. 复制 **Secret key**（以 `sk_test_` 开头）

### 步骤 3：启动后端

**打开终端（Windows CMD 或 PowerShell）：**

```bash
# 1. 进入后端目录
cd D:\projects\virtual-payment-mvp\backend

# 2. 设置 Stripe 密钥（替换成您的密钥）
set STRIPE_SECRET_KEY=sk_test_你的测试密钥

# 3. 启动后端（方式 1：使用脚本）
start.bat

# 或者（方式 2：直接运行）
mvn spring-boot:run
```

**等待启动完成，看到以下输出：**
```
Started VaultCardApplication in 3.456 seconds
Tomcat started on port 8080
```

### 步骤 4：访问前端

前端已经在运行：**http://localhost:5177/**

打开浏览器访问即可！

---

## 🧪 测试完整流程

### 1. 测试健康检查

**在浏览器访问：**
```
http://localhost:8080/api/payment/health
```

**预期输出：**
```
OK - Using provider: stripe
```

### 2. 测试创建支付（Alipay）

**使用 curl 或 Postman：**

```bash
curl -X POST http://localhost:8080/api/payment/create \
  -H "Content-Type: application/json" \
  -d "{
    \"userId\": \"user123\",
    \"amountUSD\": 100,
    \"paymentMethod\": \"alipay\",
    \"description\": \"测试充值\"
  }"
```

**预期返回：**
```json
{
  "paymentId": "pi_xxx",
  "clientSecret": "pi_xxx_secret_xxx",
  "amountUSD": 100,
  "estimatedCNY": 726,
  "status": "requires_payment_method",
  "paymentUrl": "https://checkout.stripe.com/xxx"
}
```

### 3. 测试创建虚拟卡

```bash
curl -X POST http://localhost:8080/api/payment/cards/create \
  -H "Content-Type: application/json" \
  -d "{
    \"userId\": \"user123\",
    \"cardholderName\": \"Test User\",
    \"spendingLimit\": 500,
    \"cardType\": \"gold\"
  }"
```

⚠️ **注意**：在测试模式下，虚拟卡创建可能受限，需要申请 Stripe Issuing 生产访问权限才能创建真实卡片。

---

## 🎨 前端页面

- **首页**：http://localhost:5177/
- **仪表盘**：http://localhost:5177/dashboard
- **充值页面**：http://localhost:5177/recharge

---

## ❓ 常见问题

### Q1: 后端启动失败，提示 "STRIPE_SECRET_KEY not set"
**A**: 确保您运行了 `set STRIPE_SECRET_KEY=sk_test_xxx`

### Q2: 端口 8080 已被占用
**A**: 修改 `backend/src/main/resources/application.yml` 中的 `server.port`

### Q3: Maven 下载依赖很慢
**A**: 配置国内镜像源（阿里云）

### Q4: 前端无法连接后端
**A**: 确保：
- 后端已启动（http://localhost:8080/api/payment/health 能访问）
- 前端环境变量 `VITE_API_BASE_URL` 正确（默认 http://localhost:8080）

---

## 📚 下一步

- [ ] 完善 KYC 验证流程
- [ ] 添加用户认证（JWT）
- [ ] 连接真实的 Stripe Issuing（需要申请）
- [ ] 部署到生产环境

---

## 🆘 需要帮助？

- 查看 `README.md` - 完整项目文档
- 查看 `ARCHITECTURE.md` - 架构设计文档
- 提交 Issue：https://github.com/your-repo/issues

---

**祝您开发顺利！🎉**
