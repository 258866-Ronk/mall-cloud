package com.mallcloud.order.dto;
import jakarta.validation.constraints.*;
public record CreateOrderRequest(@NotBlank String requestNo,@NotBlank String receiverName,@NotBlank String receiverPhone,@NotBlank String receiverAddress){}
