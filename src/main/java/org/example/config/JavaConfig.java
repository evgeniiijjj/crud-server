package org.example.config;

import com.google.gson.Gson;
import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {

    @Bean
    public PostController controller(PostService service, Gson gson) {
        return new PostController(service, gson);
    }

    @Bean
    public PostService service(PostRepository repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepository repository() {
        return new PostRepository();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
