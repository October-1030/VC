# VaultCard 项目工作日志

## 项目概述
- **项目名称**: VaultCard - 虚拟卡管理平台
- **目标用户**: 海外华人，用于订阅美国APP（ChatGPT、Netflix等）
- **技术栈**: React 19 + TypeScript + Vite（前端）, Spring Boot 3.2 + Java 17（后端）
- **支付集成**: Stripe Issuing API

---

## 2024年工作记录

### 1. 项目初始化与部署

#### 1.1 修复 TypeScript 错误
- **文件**: `src/pages/RechargePage.tsx`
- **问题**: `'paymentUrl' is declared but its value is never read`
- **解决**: 将 `const [paymentUrl, setPaymentUrl]` 改为 `const [_paymentUrl, setPaymentUrl]`

#### 1.2 部署到 Vercel
- **GitHub 仓库**: https://github.com/October-1030/VC.git
- **Vercel 项目名**: vaultcard
- **Vercel URL**: https://vaultcard.vercel.app

---

### 2. 社交媒体账号设置

#### 2.1 X (Twitter) 账号
- **用户名**: @VaultCardPay
- **显示名称**: 数字支付
- **用途**: Stripe 注册时的业务网站验证

#### 2.2 Logo 和 Banner 设计
- 使用 Nano Banana AI 工具生成
- Logo 提示词: 金色盾牌 + 虚拟卡片元素
- Banner 提示词: 科技感金融背景

---

### 3. Stripe 账号注册

#### 3.1 账号信息
- **状态**: 已激活 ✅
- **业务类型**: Software as a service (SaaS)
- **业务描述**: 虚拟卡服务，面向海外华人订阅美国应用

#### 3.2 Stripe Issuing
- **状态**: 需要联系 Stripe Sales 开通 live mode
- **测试模式**: 可用
- **下一步**: 使用 richard@vaultcard.vip 填写 Issuing 申请

---

### 4. 域名与邮箱配置

#### 4.1 域名购买
- **域名**: vaultcard.vip
- **价格**: $12.20
- **注册商**: Cloudflare

#### 4.2 Cloudflare 邮箱路由
- **企业邮箱**: richard@vaultcard.vip
- **转发到**: rjtang712@gmail.com
- **状态**: 已配置成功 ✅

#### 4.3 DNS 配置（进行中）
需要添加以下记录将域名绑定到 Vercel：

| 类型 | 名称 | 内容 | 代理状态 |
|------|------|------|----------|
| A | @ | 76.76.21.21 | 仅 DNS（灰色） |
| CNAME | www | cname.vercel-dns.com | 仅 DNS（灰色） |

---

### 5. 商业模式

#### 5.1 收费模式
- **开卡费**: $10/张
- **充值手续费**: 1-2%

#### 5.2 资金模式
- 共享资金池模式
- 每张卡设置消费限额
- 用户充值后更新卡片限额

#### 5.3 创始人推荐计划
- **创始人名额**: 8人
- **推荐佣金**: 50%（被推荐人的手续费）

---

## 待完成任务

### 高优先级
- [x] 在 Cloudflare 添加 DNS 记录（A 记录和 CNAME 记录） ✅
- [x] 验证 vaultcard.vip 域名绑定到 Vercel ✅
- [ ] 联系 Stripe Sales 申请 Issuing live mode

### 中优先级
- [ ] 在 Stripe 测试模式下创建 Cardholder
- [ ] 在 Stripe 测试模式下创建虚拟卡
- [ ] 测试完整的充值和发卡流程

### 低优先级
- [x] 添加英文版首页 (https://vaultcard.vip/en) ✅
- [ ] 实现用户认证系统（JWT）
- [ ] 实现 KYC 身份验证
- [ ] 数据库持久化（PostgreSQL）
- [ ] 实现创始人推荐系统

---

## 重要链接

| 服务 | 链接 |
|------|------|
| GitHub 仓库 | https://github.com/October-1030/VC.git |
| Vercel 部署 | https://vaultcard.vercel.app |
| Cloudflare | https://dash.cloudflare.com |
| Stripe Dashboard | https://dashboard.stripe.com |
| X (Twitter) | https://x.com/VaultCardPay |

---

## 参考网站
- anlianfu.com（主要参考）
- Privacy.com（使用 Lithic API）
- Revolut（有自己的银行牌照）

---

## 当前进度

**已完成**: 域名 vaultcard.vip 成功绑定到 Vercel ✅

---

## 2025年12月17日 工作记录

### 1. 前端重构为内部开发者原型（Stripe 合规）

#### 1.1 页面修改
- **LandingPage.tsx**: 改为英文版，标注 "Internal Developer Prototype"
- **Dashboard.tsx**: 添加 Test Mode 标识，Sandbox 环境提示
- **LoginPage.tsx**: 新增内部用户登录页面
- **Layout.tsx / Navbar.tsx / Footer.tsx**: 新增布局组件

#### 1.2 删除的页面（符合 Stripe 规范）
- RegisterPage.tsx（公开注册）
- ResetPasswordPage.tsx（密码重置）
- RechargePage.tsx（充值页面）
- LandingPageEN.tsx（英文首页，已合并到主页）
- FeatureCards.tsx / RegisterForm.tsx / SocialLogin.tsx / VirtualCard.tsx

#### 1.3 关键措辞更新
- "Internal Developer Prototype" - 内部开发者原型
- "Test Mode" / "Sandbox Environment" - 测试环境
- "No public onboarding" - 无公开注册
- "Invited internal test users only" - 仅限邀请的内部测试用户

### 2. 后端 API 扩展

#### 2.1 新增 Controller
- **FundingController.java**: 充值 API (`/api/funding`)
- **SubscriptionController.java**: 订阅管理 API (`/api/subscription`)
- **WebhookController.java**: Stripe Webhook 处理 (`/api/webhook/stripe`)

#### 2.2 新增 Service
- **FundingService.java**: 充值业务逻辑
- **SubscriptionService.java**: 订阅业务逻辑
- **IssuingCardService.java**: 虚拟卡管理
- **StripeWebhookService.java**: Webhook 事件处理

#### 2.3 新增 Entity 和 Repository
- User, IssuingCard, FundingTransaction, SubscriptionProfile, IssuingTransaction
- 对应的 Repository 接口

### 3. Git 提交记录
```
commit 72d443c - Refactor to internal developer prototype for Stripe compliance
- 43 files changed, 3479 insertions(+), 1360 deletions(-)
```

### 4. Stripe Issuing 申请已提交 ✅

#### 4.1 申请表填写内容
| 字段 | 填写内容 |
|------|---------|
| First name | Richard |
| Last name | Tang |
| Work email | richard@vaultcard.vip |
| Company website | https://www.vaultcard.vip |
| Country/Region | United States |
| Customers | My own business |
| Customer location | United States |
| Crypto | No |
| Business expense card | Yes |
| Estimated volume | Less than $1,000,000 |
| Funding | We're not a funded startup |
| Use case | Internal developer prototype for testing Stripe Issuing integration |

#### 4.2 申请状态
- **提交时间**: 2025年12月17日
- **当前状态**: 等待 Stripe 审核
- **预期结果**: 获得 Stripe Issuing Test Mode 访问权限

---

## 下一步计划

### 等待 Stripe 审核通过后
1. [ ] 在 Stripe Dashboard 激活 Issuing 功能
2. [ ] 在 Test Mode 创建 Cardholder
3. [ ] 在 Test Mode 创建虚拟卡
4. [ ] 测试完整的发卡流程
5. [ ] 连接前后端，调用真实 Stripe API

### 功能开发（待 Issuing 权限后）
1. [ ] 实现用户认证系统（JWT）
2. [ ] 前端连接后端 API
3. [ ] 实现充值流程（PaymentIntent）
4. [ ] 实现卡片管理功能
5. [ ] 部署后端到云服务器

### 申请 Live Mode（产品完善后）
1. [ ] 准备完整商业计划
2. [ ] 联系 Stripe Sales 申请 Live Mode
3. [ ] 完成 KYC/KYB 合规审核

---

## 重要提醒

⚠️ **当前是 Test Mode 申请**
- 通过后获得沙盒测试环境
- 可以用测试卡号（4242...）开发
- 无真实资金流动

⚠️ **网站当前状态**
- 首页：英文版内部原型说明
- Dashboard：测试数据展示
- 无注册功能（符合 Stripe 规范）

---

*最后更新: 2025年12月17日*
