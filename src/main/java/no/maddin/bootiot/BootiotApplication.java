package no.maddin.bootiot;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootiotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootiotApplication.class, args);
	}

	@Bean
	public EntryStore entryStore() {
		return new EntryStoreImpl();
	}

}


