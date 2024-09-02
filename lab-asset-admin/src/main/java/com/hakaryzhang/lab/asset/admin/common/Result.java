package com.hakaryzhang.lab.asset.admin.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result success(T t) {
        return new Result(0, "ok", t);
    }

    public static <T> Result build(int code, T t) {
        return new Result(code, "ok", t);
    }

    public static Result success() {
        return new Result(0, "ok", Collections.EMPTY_MAP);
    }

    public static Result fail(int code, String msg) {
        return fail(code, msg, null);
    }

    public static <T> Result fail(int code, String msg, T t) {
        return new Result(code, msg, t);
    }

}
