package com.hakaryzhang.lab.asset.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Down {
    int c = 0;

    public void d1() {
        d2();
    }

    @Transactional(rollbackFor = {})
    public void d2() {
        c += 1;
        throw new RuntimeException("mine");
    }

    public void print() {
        System.out.println(c);
    }
}
