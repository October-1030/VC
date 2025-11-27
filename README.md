# 🔐 VaultCard - 虚拟卡管理平台

[![React](https://img.shields.io/badge/React-19.1.1-blue)](https://react.dev/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.6.2-blue)](https://www.typescriptlang.org/)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)

安全、合规的虚拟卡发行与管理平台，支持多种支付服务商（Stripe/Marqeta等）

## ✨ 核心特性

- 🎨 **精美UI**: 基于React + TailwindCSS + Framer Motion
- 🏗️ **可扩展架构**: Provider模式，随时切换支付服务商
- 🔒 **安全第一**: 所有敏感信息在后端，前端无法获取
- 🌍 **国际化支付**: 支持Alipay、WeChat Pay、信用卡
- 💳 **虚拟卡发行**: 即时生成虚拟Visa/Mastercard
- ✅ **完全合规**: 使用官方API，符合监管要求

## 🚀 技术栈

### 前端
- **框架**: React 19.1.1 + TypeScript 5.6.2
- **构建工具**: Vite 7.2.2
- **样式**: TailwindCSS 3.4.18
- **动画**: Framer Motion 12.23.24
- **路由**: React Router DOM 7.9.5
- **图标**: Lucide React 0.553.0

### 后端
- **框架**: Spring Boot 3.2.0
- **语言**: Java 17
- **支付集成**: Stripe Java SDK 24.0.0
- **构建工具**: Maven 3.8+

## ✨ 核心功能

### ✅ 已实现
- 响应式登录/注册页面
- 用户仪表盘（Dashboard）
- 3D 虚拟卡片展示（Gold/Platinum/Black）
- 充值功能（Alipay/WeChat/Card）
- 交易记录查询
- 卡片管理（冻结/解冻）
- Provider模式后端架构
- Stripe Issuing集成
- 完整的REST API

### 🔄 开发中
- 用户认证系统（JWT）
- KYC身份验证
- 数据库持久化（PostgreSQL）
- Marqeta Provider实现
- 实时通知系统（WebSocket）

## 📦 快速开始

### 前置要求
- Node.js 18+
- Java 17+
- Maven 3.8+
- Stripe测试账号

### 1. 启动后端

```bash
cd backend

# 设置环境变量
export STRIPE_SECRET_KEY=sk_test_你的测试密钥
export STRIPE_WEBHOOK_SECRET=whsec_你的webhook密钥

# 启动Spring Boot
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动

### 2. 启动前端

```bash
# 在项目根目录
npm install
npm run dev
```

前端将在 http://localhost:5173 启动

## 🏗️ 项目结构

```
src/
├── components/          # React 组件
│   ├── VirtualCard.tsx  # 虚拟卡片组件
│   ├── LoginForm.tsx    # 登录表单
│   ├── FeatureCards.tsx # 功能特色卡片
│   └── SocialLogin.tsx  # 第三方登录
├── pages/              # 页面组件
│   └── LandingPage.tsx # 首页
├── layouts/            # 布局组件
├── hooks/              # 自定义 Hooks
├── utils/              # 工具函数
├── types/              # TypeScript 类型定义
└── assets/             # 静态资源
```

## 🔐 合规说明

本项目采用 **Stripe Issuing** 作为虚拟卡发行后端：

- ✅ Stripe 负责所有金融监管和合规
- ✅ 符合美国 FinCEN、KYC、AML 要求
- ✅ PCI DSS 认证
- ✅ 适合个人开发者和初创公司

**重要**: 本项目仅用于技术演示和学习目的。如需商业化运营，需：
1. 申请 Stripe Issuing 账号
2. 完成 Stripe 业务审核
3. 遵守当地金融法规

## 🎨 设计参考

UI/UX 设计参考：
- [安联付 (anlianfu.com)](https://anlianfu.com)
- [Privacy.com](https://privacy.com)
- [Revolut](https://revolut.com)

## 📄 许可证

MIT License

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

**注意**: 本项目目前处于 MVP 阶段，仅供学习和演示使用。不建议用于生产环境。
