package org.example.controller;

import com.google.gson.Gson;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;


public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  public final Gson gson;

  public PostController(PostService service, Gson gson) {
    this.service = service;
    this.gson = gson;
  }

  public void get(long id, HttpServletResponse response) throws IOException {
    // TODO: deserialize request & serialize response
    response.setContentType(APPLICATION_JSON);
    String data;
    try {
      if (id == 0) {
        data = gson.toJson(service.all());
      } else {
        data = gson.toJson(service.getById(id));
      }
      response.getWriter().print(data);
    } catch (NotFoundException e) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var post = gson.fromJson(body, Post.class);
    try {
      final var data = service.save(post);
      response.getWriter().print(gson.toJson(data));
    } catch (NotFoundException e) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    // TODO: deserialize request & serialize response
    try {
      service.removeById(id);
    } catch (NotFoundException e) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}
