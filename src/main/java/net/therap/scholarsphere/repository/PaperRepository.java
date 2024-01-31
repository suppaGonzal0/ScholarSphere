package net.therap.scholarsphere.repository;

import net.therap.scholarsphere.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {

    @Query(value = "SELECT COUNT(*) FROM saved WHERE user_id = :userId AND paper_id = :paperId", nativeQuery = true)
    int countSavedPapers(@Param("userId") Long userId, @Param("paperId") Long paperId);

    @Query(value = "SELECT p.* FROM paper p JOIN saved s ON p.id = s.paper_id WHERE s.user_id = :userId", nativeQuery = true)
    List<Paper> findSavedPapersByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM saved WHERE user_id = :userId AND paper_id = :paperId", nativeQuery = true)
    void unsavePaper(@Param("userId") Long userId, @Param("paperId") Long paperId);

    @Modifying
    @Query(value = "INSERT INTO saved (user_id, paper_id) VALUES (:userId, :paperId)", nativeQuery = true)
    void savePaper(@Param("userId") Long userId, @Param("paperId") Long paperId);

    @Query("SELECT p FROM Paper p WHERE p.isApproved = true ORDER BY " +
            "CASE WHEN :sort = 'publish_date' THEN p.publicationDate END DESC, " +
            "CASE WHEN :sort = 'rating' AND p.totalRated > 0 THEN (p.totalRating/p.totalRated) ELSE 0 END DESC, " +
            "CASE WHEN :sort = 'downloads' THEN p.downloadCount END DESC")
    List<Paper> findAllSorted(@Param("sort") String sort);

    List<Paper> findAllByIsApprovedFalse();

    List<Paper> findByTitleContainingIgnoreCase(String title);

    List<Paper> findByConferenceTitleContainingIgnoreCase(String conferenceTitle);

    List<Paper> findByTagsNameContainingIgnoreCase(String tag);

    List<Paper> findByAuthorsEmailContainingIgnoreCaseAndIsApprovedTrue(String email);

    List<Paper> findByConferenceIdAndIsApprovedTrue(long id);
}
