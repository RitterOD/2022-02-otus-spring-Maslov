package org.maslov.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleService {

    private final Scanner sc;

    public ConsoleService() {
        sc = new Scanner(System.in);
    }

    public void printLine(String line) {
        System.out.print(line);
    }

    public void printlnLine(String line) {
        System.out.println(line);
    }

    public String readLine() {
        return sc.nextLine();
    }

    public int readInt() {
        return sc.nextInt();
    }
}
