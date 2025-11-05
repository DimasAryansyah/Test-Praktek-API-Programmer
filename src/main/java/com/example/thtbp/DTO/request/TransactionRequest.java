package com.example.thtbp.DTO.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private String service_code;
    private Double total_amount;
}
