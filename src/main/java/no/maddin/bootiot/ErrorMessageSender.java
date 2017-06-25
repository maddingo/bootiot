package no.maddin.bootiot;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ErrorMessageSender {
    @Value("${TWILIO_SERVICE_SID:}")
    private String serviceSid;

    @Value("${TWILIO_ACCOUNT_SID:}")
    private String accountSid;

    @Value("${ERROR_SMS_RECEIVER:}")
    private String errorMessageReceiver;

    @Value("${TWILIO_AUTH_TOKEN:}")
    private String authToken;

    @Value("${TWILIO_SMS_SENDER:}")
    private String getErrorMessageSender;

    public void sendMessage(String text) {
        if (StringUtils.isEmpty(accountSid) || StringUtils.isEmpty(authToken) || StringUtils.isEmpty(errorMessageReceiver) || StringUtils.isEmpty(getErrorMessageSender)) {
            throw new IllegalArgumentException("missing message sender parameters");
        }
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(new PhoneNumber(errorMessageReceiver), new PhoneNumber(getErrorMessageSender), "Test From Unit test").create();
    }
}
