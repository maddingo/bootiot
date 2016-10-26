package no.maddin.bootiot;

import com.twilio.Twilio;
import org.junit.Test;

public class GetSMSTest {

    @Test
    public void lastSMS() {
        Twilio.init(System.getProperty("twilio.accountsid"), System.getProperty("twilio.authtoken"));

    }
}
