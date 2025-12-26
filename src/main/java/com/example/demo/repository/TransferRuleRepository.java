public interface TransferRuleRepository extends JpaRepository<TransferRule, Long> {
    List<TransferRule> findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(Long s, Long t);
}
