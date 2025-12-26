public interface TransferEvaluationResultRepository extends JpaRepository<TransferEvaluationResult, Long> {
    List<TransferEvaluationResult> findBySourceCourseId(Long id);
}
