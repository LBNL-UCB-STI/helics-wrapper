package com.github.beam;

import com.java.helics.helics;

public class HelicsExample {
    public static void main(String[] args) {
        HelicsLoader.load();

        System.out.println("HELICS Version: " + helics.helicsGetVersion());
    }
}
