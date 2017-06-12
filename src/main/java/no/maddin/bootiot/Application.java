package no.maddin.bootiot;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public EntryStore entryStore() {
		return new EntryStoreImpl();
	}

	@Bean
	public JsonParser jsonParser() {
        return new JacksonJsonParser();
    }

    @Bean
    public org.joda.time.format.DateTimeFormatter dateTimeFormatter() {
		DateTimeFormatterFactory dtf = new DateTimeFormatterFactory("yyyy-mm-dd HH:MM:ss.SS");

		return dtf.createDateTimeFormatter();
	}
}


