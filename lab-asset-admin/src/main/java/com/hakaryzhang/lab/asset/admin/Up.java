package com.hakaryzhang.lab.asset.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Up {

    @Autowired
    Down down;

    public void u1() {
        try {
            down.d1();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            down.d1();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        down.print();
    }

    public void u2() {
        try {
            down.d2();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        try {
            down.d2();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        down.print();
    }

    public void u3() {
        try {
            down.d1();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        try {
            down.d2();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        down.print();
    }
}
