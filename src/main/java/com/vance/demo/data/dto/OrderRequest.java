package com.vance.demo.data.dto;

import java.util.List;

public record OrderRequest(String customerName, List<Item> items) {
    // 嵌套 record 表示訂單中的單個項目
    public record Item(String productId, int quantity, double unitPrice) {
        // 自定義構造函數，驗證輸入
        public Item {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for product ID: " + productId);
            }
            if (unitPrice < 0) {
                throw new IllegalArgumentException("Unit price cannot be negative for product ID: " + productId);
            }
        }
    }
}
