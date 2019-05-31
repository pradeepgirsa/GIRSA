package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.AssetDSU3;

import java.util.List;

@Repository
public interface AssetDSU3Repository extends JpaRepository<AssetDSU3, Long> {

    AssetDSU3 findByAssetId(String assetId);

    List<AssetDSU3> findFirst10ByNetIndicatorIsFalseAndInstSubTypeOrderByEffWeightDesc(String instSubType);

    List<AssetDSU3> findFirst10ByNetIndicatorIsFalseOrderByEffWeightDesc();

    @Query("SELECT icbIndustry, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false GROUP BY icbIndustry")
    List<Object[]> findEffWeightSumGroupByIcbIndustry();

    @Query("SELECT localMarket, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false GROUP BY localMarket")
    List<Object[]> findEffWeightSumGroupByLocalMarket();

    @Query("SELECT icbSuperSector, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false GROUP BY icbSuperSector")
    List<Object[]> findEffWeightSumGroupByIcbSuperSector();

    @Query(value = "SELECT sarbClassification, SUM(effWeight) FROM AssetDSU3 WHERE netIndicator=false GROUP BY sarbClassification")
    List<Object[]> findEffWeightSumGroupBySARBClassification();

}
