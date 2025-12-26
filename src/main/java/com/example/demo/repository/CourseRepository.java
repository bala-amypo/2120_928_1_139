public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByUniversityIdAndCourseCode(Long id, String code);
    List<Course> findByUniversityIdAndActiveTrue(Long id);
}
