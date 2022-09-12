package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class PostRepository {

  private final AtomicLong lastId;
  private final Map<Long, Post> posts;

  public PostRepository() {
    posts = new ConcurrentHashMap<>();
    lastId = new AtomicLong();
  }

  public List<Post> all() {
    return posts.values().stream().sorted(Comparator.comparingLong(Post::getId)).collect(Collectors.toList());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) throws NotFoundException {
    if (post.getId() == 0) post.setId(lastId.incrementAndGet());
    else if (!posts.containsKey(post.getId())) throw new NotFoundException();
    posts.put(post.getId(), post);
    return post;
  }

  public Optional<Post> removeById(long id) {
    return Optional.ofNullable(posts.remove(id));
  }
}
