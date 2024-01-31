package net.therap.scholarsphere.repository;

import net.therap.scholarsphere.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.likedByUsers " +
            "WHERE c.paper.id = :paperId ORDER BY " +
            "CASE WHEN :sort = 'top' THEN SIZE(c.likedByUsers) END DESC, " +
            "CASE WHEN :sort = 'newest' THEN c.createdOn END DESC, " +
            "CASE WHEN :sort = 'oldest' THEN c.createdOn END ASC, c.id")
    List<Comment> findAllComments(@Param("paperId") long paperId, @Param("sort") String sort);
}
