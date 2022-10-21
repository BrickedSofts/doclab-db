package com.doclab.db;

import com.doclab.api.doclab;
import com.doclab.api.doclabService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.doclab.api", "com.doclab.db"})
public class SeedDB {

    @Autowired
    private doclabService service;

    private static class doclabListEntry {
        public String url;
    }

    private static class doclabListResponse {
        public List<SeedDB.doclabListEntry> results;
    }

    private static class doclabImage {
        public String front_default;
        public String back_default;
    }

    private static class doclabEntry {
        public String name;
        public SeedDB.doclabImage sprites;
    }

    public static void main(String[] args) {
        SpringApplication.run(SeedDB.class, args);
    }

    @Bean
    InitializingBean seed() {
        return () -> {
            RestTemplate restTemplate = new RestTemplate();
            doclabListResponse plr = restTemplate.getForObject("https://pokeapi.co/api/v2/doclab?limit=10", doclabListResponse.class);

            plr.results.forEach(p -> {
                doclabEntry entry = restTemplate.getForObject(p.url, doclabEntry.class);

                String name = entry.name;
                String imagefront = getBase64(entry.sprites.front_default);
                String imageback = getBase64(entry.sprites.back_default);

                doclab doclab = new doclab(name, imagefront, imageback, 0);
                service.create(doclab);
            });
        };
    }

    private String getBase64(String url) {
        try {
            URL imageUrl = new URL(url);
            URLConnection con = imageUrl.openConnection();
            InputStream is = con.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return String.format("data:image/png;base64,%s", Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (Exception e) {
            return null;
        }
    }

}
