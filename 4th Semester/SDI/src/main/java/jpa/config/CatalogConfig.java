package jpa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"jpa.repository", "jpa.service", "jpa.ui"})
public class CatalogConfig {


}
