package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/auth")
public class UnsafePingController {

    @GetMapping("/unsafePing")
    public String unsafePing(@RequestParam String cmd) {
        //http://localhost:8080/api/auth/unsafePing?cmd=ping -n 4 127.0.0.1 & whoami
        StringBuilder result = new StringBuilder();
        try {
            // Выполняем команду напрямую, что может привести к инъекции
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

