package no.maddin.bootiot;

import com.fasterxml.jackson.core.JsonParseException;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.*;

/**
 * RequestParameters:
 *  ToCountry: [GB],
 *  ToState: [],
 *  SmsMessageSid: [],
 *  NumMedia: [0],
 *  ToCity: [],
 *  FromZip: [],
 *  SmsSid: [],
 *  FromState: [],
 *  SmsStatus: [received],
 *  FromCity: [],
 *  Body: [<"counter":1, "temp": 12.3>],
 *  FromCountry: [NO],
 *  To: [+447400246619],
 *  MessagingServiceSid: [],
 *  ToZip: [],
 *  NumSegments: [1],
 *  MessageSid: [],
 *  AccountSid: [],
 *  From: [],
 *  ApiVersion: [2010-04-01]
 */
@WebServlet(urlPatterns = {
        "/twilioinput",
        "/twilioinput/*"
})
@Slf4j
public class TwilioInputServlet extends HttpServlet {

    @Autowired
    private EntryStore entryStore;

    @Value("${TWILIO_SERVICE_SID:T(java.util.UUID).randomUUID().toString()}")
    private String serviceSid;

    @Value("${TWILIO_ACCOUNT_SID:T(java.util.UUID).randomUUID().toString()}")
    private String accountSid;

    @Value("${ERROR_SMS_RECEIVER:}")
    private String errorMessageReceiver;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        checkCredentials(parameterMap);

        try {
            saveEntry(parameterMap);
            sendSuccessResponse(resp, parameterMap);
        } catch (IllegalArgumentException ex) {
            sendErrorResponse(resp, parameterMap, ex);
        }
    }

    private void saveEntry(Map<String, String[]> parameters) {
        String bodyValue = parameters.get("Body")[0];
        if (bodyValue != null && !bodyValue.isEmpty()) {
            String json = bodyValue.replace('<', '{').replace('>','}');
            entryStore.saveJson(json);
        } else {
            throw new IllegalArgumentException("empty body");
        }
    }

    private void checkCredentials(Map<String, String[]> parameters) throws ServletException {
        if (StringUtils.isEmpty(accountSid) || StringUtils.isEmpty(serviceSid)) {
            throw new ServletException("invalid configuration");
        }
        if (!accountSid.equals(parameters.get("AccountSid")[0]) ||
                !serviceSid.equals(parameters.get("MessagingServiceSid")[0]))
        {
            throw new ServletException("Illegal request");
        }
    }

    private void sendSuccessResponse(HttpServletResponse resp, Map<String, String[]> parameterMap) throws IOException {
        resp.setContentType("application/xml");
        try {
            Writer writer = resp.getWriter();
            String respString = new MessagingResponse.Builder().build().toXml();
            writer.append(respString);
        } catch (TwiMLException e) {
            throw new IOException(e);
        }
    }

    private void sendErrorResponse(HttpServletResponse resp, Map<String, String[]> parameterMap, Throwable tr) throws IOException {
        resp.setContentType("application/xml");

        MessagingResponse.Builder b = new MessagingResponse.Builder();
        if (!StringUtils.isEmpty(errorMessageReceiver)) {
            b.message(
              new Message.Builder()
                      .to(errorMessageReceiver)
                      .body(new Body("Received erroneous message: Check message log at https://papertrailapp.com/dashboard"))
                      .build()
            );
        }

        StringWriter errorRequestString = new StringWriter();
        logParameters(parameterMap, errorRequestString);
        log.error("Received erroneous message: " + errorRequestString.toString());
        try {
            Writer writer = resp.getWriter();
            writer.append(b.build().toXml());
        } catch (TwiMLException e) {
            throw new IOException(e);
        }
    }

    private void logParameters(Map<String, String[]> parameterMap, Writer writer) throws IOException {

        String paramString = parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                .collect(java.util.stream.Collectors.joining(", "));

        writer.append("RequestParameters: ").append(paramString);
    }
}
