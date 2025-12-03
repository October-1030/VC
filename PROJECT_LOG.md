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

**下一步**:
1. 联系 Stripe Sales 申请 Issuing live mode
2. 在 Stripe 测试模式下创建 Cardholder 和虚拟卡
3. 测试完整的充值和发卡流程

---

*最后更新: 2024年12月*
