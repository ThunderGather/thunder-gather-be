package com.team.thundergather.domain.post.dataAccess.repository;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
