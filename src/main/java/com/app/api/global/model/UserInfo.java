package com.app.api.global.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private int access;
    private String unique;
    private int id;
}
