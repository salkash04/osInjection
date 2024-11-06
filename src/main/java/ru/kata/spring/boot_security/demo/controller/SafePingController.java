package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/auth")
public class SafePingController {

    @GetMapping("/safePing")
    public String safePing(
            @RequestParam(required = false, defaultValue = "ping -n 4 127.0.0.1") String cmd) {

        if (!cmd.matches("^ping -n \\d+ (\\d{1,3}\\.){3}\\d{1,3}$")) {
            return "Ошибка: Неподдерживаемая или потенциально небезопасная команда.";
        }

        StringBuilder result = new StringBuilder();
        try {
            // Выполняем команду только с проверенными параметрами
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            process.waitFor();
        } catch (Exception e) {
            result.append("Ошибка выполнения команды: ").append(e.getMessage());
        }
        return result.toString();
    }
}

