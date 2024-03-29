package io.github.geniot.elex.controllers;

import io.github.geniot.elex.DictionariesPool;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WavController {
    Logger logger = LoggerFactory.getLogger(WavController.class);

    @Autowired
    DictionariesPool dictionariesPool;

    @RequestMapping(value = "/wav", method = RequestMethod.GET)
    public void handle(HttpServletResponse response,
                       @RequestParam int id,
                       @RequestParam String link
    ) {
        try {
            long t1 = System.currentTimeMillis();
            byte[] resourceBytes = dictionariesPool.getResource(id, link);

            response.setContentType("audio/mpeg");
            response.setHeader("Content-Length", String.valueOf(resourceBytes.length));
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.getOutputStream().write(resourceBytes);
            response.getOutputStream().flush();

            long t2 = System.currentTimeMillis();
            logger.info((t2 - t1) + " ms " + link);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
