package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.AssetDSU3;

import java.util.List;

@Repository
public interface AssetDSU3Repository extends JpaRepository<AssetDSU3, Long> {

    AssetDSU3 findByAssetIdAndFundName(String assetId, String fundName);

    AssetDSU3 findByNetIndicatorIsTrueAndFundName(String fundName);

    List<AssetDSU3> findFirst10ByNetIndicatorIsFalseAndInstSubTypeAndFundNameOrderByEffWeightDesc(String instSubType, String fundName);

    List<AssetDSU3> findFirst10ByNetIndicatorIsFalseAndFundNameOrderByEffWeightDesc(String fundName);

    @Query("SELECT icbIndustry, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false and fundName=?1 GROUP BY icbIndustry")
    List<Object[]> findEffWeightSumByFundNameGroupByIcbIndustry(String fundName);

    @Query("SELECT localMarket, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false and fundName=?1 GROUP BY localMarket")
    List<Object[]> findEffWeightSumByFundNameGroupByLocalMarket(String fundName);

    @Query("SELECT icbSuperSector, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false and fundName=?1 GROUP BY icbSuperSector")
    List<Object[]> findEffWeightSumByFundNameGroupByIcbSuperSector(String fundName);

    @Query(value = "SELECT sarbClassification, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false and fundName=?1 GROUP BY sarbClassification")
    List<Object[]> findEffWeightSumByFundNameGroupBySARBClassification(String fundName);

    @Query("SELECT DISTINCT(fundName) FROM AssetDSU3 WHERE netIndicator=false")
    List<String> findDistinctFundNameAndNetIndicatorFalse();

}
