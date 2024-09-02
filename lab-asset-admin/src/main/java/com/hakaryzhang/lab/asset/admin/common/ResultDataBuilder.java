package com.hakaryzhang.lab.asset.admin.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class ResultDataBuilder {

    Map<String, Object> data = new HashMap();

    public static ResultDataBuilder builder() {
        return new ResultDataBuilder();
    }

    public Map<String, Object> build() {
        return data;
    }

    public ResultDataBuilder put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public ResultDataBuilder putPageList(Object value) {
        data.put("list", value);
        if (value instanceof Page) {
            data.put("totalCount", (int) ((Page) value).getTotal());
        } else {
            data.put("totalCount", 0);
        }
        return this;
    }

    public ResultDataBuilder buildPageInfoList(PageInfo pageInfo) {
        data.put("list", pageInfo.getList());
        data.put("totalCount", new Long(pageInfo.getTotal()).intValue());
        return this;
    }
}
