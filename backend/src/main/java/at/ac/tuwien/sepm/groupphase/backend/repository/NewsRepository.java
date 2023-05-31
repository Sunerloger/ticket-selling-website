package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Find all news entries specified by {@code pageable}.
     *
     * @param pageable specifies parameters and index of page
     * @return ordered page of news entries
     */
    @NonNull
    Page<News> findAll(@NonNull Pageable pageable);
}
