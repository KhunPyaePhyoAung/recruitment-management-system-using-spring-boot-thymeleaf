package team.ojt7.recruitment.model.repo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.ojt7.recruitment.model.entity.RecruitmentResource;

@Repository
public interface RecruitmentResourceRepo extends JpaRepository<RecruitmentResource, Long> {

	RecruitmentResource findByCodeAndIsDeleted(String code, boolean isDeleted);
	
	@Query(value = "SELECT MAX(id) FROM recruitment_resource", nativeQuery = true)
	Long findMaxId();
	RecruitmentResource findByNameAndIsDeleted(String name, boolean isDeleted);
	@Query("SELECT r FROM RecruitmentResource r WHERE (name LIKE :keyword) AND (is_deleted = false)")
	List<RecruitmentResource> searchName(@Param("keyword") String keyword);
	List<RecruitmentResource> findAllByIsDeleted(boolean isDeleted);
	
	
	@Query("SELECT r FROM RecruitmentResource r WHERE (code LIKE :keyword OR name LIKE :keyword) AND (is_deleted = false)")
	List<RecruitmentResource> search(@Param("keyword") String keyword);
	
	@Query("SELECT r FROM RecruitmentResource r WHERE (code LIKE :keyword OR name LIKE :keyword) AND (:entityType is null OR entity_type = :entityType) AND (is_deleted = false)")
	List<RecruitmentResource> search(
			@Param("keyword") String keyword,
			@Param("entityType") String entityType
			);

	@Query(
		value = "SELECT r FROM RecruitmentResource r WHERE (code LIKE :keyword OR name LIKE :keyword) AND (:entityType is null OR entity_type = :entityType) AND (is_deleted = false)",
		countQuery = "SELECT COUNT(r) FROM RecruitmentResource r WHERE (code LIKE :keyword OR name LIKE :keyword) AND (:entityType is null OR entity_type = :entityType) AND (is_deleted = false)"
	)
	Page<RecruitmentResource> search(
		@Param("keyword")
		String keyword,
		@Param("entityType")
		String entityType,
		Pageable pageable
	);
	
	@Modifying
	@Query(value = "UPDATE recruitment_resource SET is_deleted = true WHERE id = :id", nativeQuery = true)
	void deleteById(@Param("id")Long id);
}
