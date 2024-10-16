package com.albuquerque.gotreasury.dto;

public record ErrorDTO(String timestamp, Integer status, String error, String message, String path) {
}