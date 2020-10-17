package client;

import client.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "client.config"
                );

        context.getBean(Console.class).runConsole();
    }
}
