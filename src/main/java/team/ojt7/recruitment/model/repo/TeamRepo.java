package team.ojt7.recruitment.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.ojt7.recruitment.model.entity.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team,Long> {
	@Query("SELECT t FROM Team t WHERE (name LIKE :keyword OR t.department.name LIKE :keyword) AND (t.isDeleted = false AND t.department.isDeleted = false)")
	List<Team> search(@Param("keyword") String keyword);

	@Modifying
	@Query(value = "UPDATE team SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id") Long id);
}
