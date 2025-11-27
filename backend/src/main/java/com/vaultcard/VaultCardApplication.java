package com.vaultcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VaultCard Backend Application
 *
 * 虚拟卡管理平台后端
 * 使用Provider模式支持多种支付服务商（Stripe/Marqeta等）
 */
@SpringBootApplication
public class VaultCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaultCardApplication.class, args);
        System.out.println("\n" +
            "╔═══════════════════════════════════════════╗\n" +
            "║   VaultCard Backend Started Successfully  ║\n" +
            "║   http://localhost:8080                   ║\n" +
            "╚═══════════════════════════════════════════╝\n");
    }
}
