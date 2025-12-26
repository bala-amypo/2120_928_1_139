public interface CourseContentTopicRepository extends JpaRepository<CourseContentTopic, Long> {
    List<CourseContentTopic> findByCourseId(Long id);
}
