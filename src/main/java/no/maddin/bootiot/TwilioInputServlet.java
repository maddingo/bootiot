package no.maddin.bootiot;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkCredentials(req.getParameterMap());
        Writer writer = resp.getWriter();

        sendResponse(resp, writer);

        logParameters(req);
    }

    private void checkCredentials(Map<String, String[]> parameters) throws ServletException {
        if (!System.getenv("TWILIO_ACCOUNT_SID").equals(parameters.get("AccountSid")[0]) ||
                !System.getenv("TWILIO_SERVICE_SID").equals(parameters.get("MessagingServiceSid")))
        {
            throw new ServletException("Illegal request");
        }
    }

    private void sendResponse(HttpServletResponse resp, Writer writer) throws IOException {
        resp.setContentType("text/xml");
        writer.append("<Response/>");
    }

    private void logParameters(HttpServletRequest req) {
        String paramString = req.getParameterMap()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                .collect(java.util.stream.Collectors.joining(", "));

        log.info("RequestParameters: " + paramString);
    }
}
