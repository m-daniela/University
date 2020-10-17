package jpa;

import jpa.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "jpa"
                );

        context.getBean(Console.class).runConsole();
    }
}
