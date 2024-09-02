package com.hakaryzhang.lab.asset.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayDeque;
import java.util.Deque;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TempTest {
    @Autowired
    Up up;

    @Test
    public void t1() {
        up.u1();
    }

    @Test
    public void t2() {
        up.u2();
    }

    @Test
    public void t3() {
        up.u3();
        Deque<Integer> deque = new ArrayDeque<>();
    }
}
