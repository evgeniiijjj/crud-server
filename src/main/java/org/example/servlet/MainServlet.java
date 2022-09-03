package org.example.servlet;

import org.example.constants.Method;
import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      var path = req.getRequestURI();
      long id = 0;
      final var method = req.getMethod();
      if (path.matches("/api/posts/*\\d*")) {
        if (path.matches("/api/posts/\\d+")) {
          id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        }
        switch (Method.valueOf(method)) {
          case GET -> controller.get(id, resp);
          case POST -> controller.save(req.getReader(), resp);
          case DELETE -> controller.removeById(id, resp);
          default -> resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
        return;
      }
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

