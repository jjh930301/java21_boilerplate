package com.app.api.global.enums;

public enum TokenEnum {
    ACCESS_TOKEN(0),
    REFRESH_TOKEN(1);
  
    private final int value;
  
    private TokenEnum(int i) {
      value = i;
    }
    public int getValue() {
      return this.value;
    }
  }