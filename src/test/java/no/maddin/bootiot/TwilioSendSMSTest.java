package no.maddin.bootiot;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSendSMSTest {

    private static final String ACCOUNT_SID = System.getProperty("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getProperty("TWILIO_AUTH_TOKEN");
    private static final String TWILIO_SMS_RECEIVER = System.getProperty("TWILIO_SMS_RECEIVER");
    private static final String TWILIO_SMS_SENDER = System.getProperty("TWILIO_SMS_SENDER");

    @BeforeClass
    public static void checkParameters() {
        assumeThat("ACCOUNT_SID", ACCOUNT_SID, is(notNullValue()));
        assumeThat("AUTH_TOKEN", AUTH_TOKEN, is(notNullValue()));
        assumeThat("TWILIO_SMS_RECEIVER", TWILIO_SMS_RECEIVER, is(notNullValue()));
        assumeThat("TWILIO_SMS_SENDER", TWILIO_SMS_SENDER, is(notNullValue()));
    }

    @Test
    public void sendSMS() throws Exception {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(TWILIO_SMS_RECEIVER), new PhoneNumber(TWILIO_SMS_SENDER), "Test From Unit test").create();

        assertThat(message, hasProperty("errorMessage", is(nullValue())));
    }
}
