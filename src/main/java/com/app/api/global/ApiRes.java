package com.app.api.global;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRes<T> {
    private T payload;
    private List<String> messages = new ArrayList<>();
    private int status = 200;

    public ApiRes() {
        this.payload = null;
    }

    public ApiRes(T payload) {
        this.payload = payload;
    }

    public ApiRes(T payload , String message) {
        this.payload = payload;
        this.messages.add(message);
    }

    public ApiRes(T payload , int status) {
        this.payload = payload;
        this.status = status;
    }

    public ApiRes(T payload , int status , String message) {
        this.payload = payload;
        this.status = status;
        this.messages.add(message);
    }
}
