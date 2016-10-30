package no.maddin.bootiot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
//@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwilioInputTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createClient() {
//        ResponseEntity<Client> response|Entity =
//                restTemplate.postForEntity("/clients", new CreateClientRequest("Foo"), Client.class);
//        Client client = responseEntity.getBody();
//
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals("Foo", client.getName());
    }

    @Test
    public void input() {
        assertThat(true, is(true));
    }
}
