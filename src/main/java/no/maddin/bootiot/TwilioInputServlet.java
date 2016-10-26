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

@WebServlet(urlPatterns = {
        "/twilioinput",
        "/twilioinput/*"
})
@Slf4j
public class TwilioInputServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();

        resp.setContentType("text/xml");
        writer.append("<Response/>");

        String paramString = req.getParameterMap()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                .collect(java.util.stream.Collectors.joining(", "));

        log.info("RequestParameters: " + paramString);
    }
}
