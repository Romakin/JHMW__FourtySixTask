package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

  private ConcurrentSkipListSet<Post> repositoryArray = new ConcurrentSkipListSet<>(Comparator.comparing(o -> o.getId()));

  public List<Post> all() {
    Post[] posts = repositoryArray.toArray(new Post[]{});
    List<Post> postsList = new ArrayList<>();
    for (Post p : posts)
      if (!p.isRemoved())
        postsList.add(p);
    return postsList;
  }

  public Optional<Post> getById(long id) {
    Post fp = repositoryArray.ceiling(new Post(id));
    return Optional.ofNullable(fp != null && !fp.isRemoved() ? fp : null);
  }

  public Post save(Post post) {
    if (post.equals(new Post(0)))
      post.setId(repositoryArray.size());
    repositoryArray.add(post);
    return post;
  }

  public boolean removeById(long id) {
    Post fp = repositoryArray.ceiling(new Post(id));
    System.out.println("HERE");
    return fp != null ? fp.setRemoved(true) : false;
  }
}