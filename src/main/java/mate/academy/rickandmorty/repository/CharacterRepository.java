package mate.academy.rickandmorty.repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.rickandmorty.model.CharacterInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterInfo, Long>,
                                             JpaSpecificationExecutor<CharacterInfo> {

    Optional<CharacterInfo> findTopByOrderByIdDesc();

    default Long getLastId() {
        return findTopByOrderByIdDesc()
                .map(CharacterInfo::getId)
                .orElseThrow(() -> new NoSuchElementException("Database is empty"));
    }

    Page<CharacterInfo> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
