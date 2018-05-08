package com.checkit.backend.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by Gleb Dovzhenko on 01.05.2018.
 */
public class JsonUtils {

    public static void buildJsonResponse(HttpServletResponse response, int status, Object body) throws IOException {
        response.setStatus(status);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().append(new ObjectMapper().writeValueAsString(body));
        response.getWriter().flush();
    }
}
