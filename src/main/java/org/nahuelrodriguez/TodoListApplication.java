package org.nahuelrodriguez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TodoListApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }
}
