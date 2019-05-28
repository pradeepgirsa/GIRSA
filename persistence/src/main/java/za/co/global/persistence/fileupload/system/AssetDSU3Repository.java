package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.AssetDSU3;

import java.util.List;

@Repository
public interface AssetDSU3Repository extends JpaRepository<AssetDSU3, Long> {

    AssetDSU3 findByAssetId(String assetId);

    List<AssetDSU3> findFirst10ByInstSubTypeOrderByEffWeightDesc(String instSubType);

    List<AssetDSU3> findFirst10OrderByEffWeightDesc();

    @Query("SELECT icbIndustry, SUM(effWeight) FROM AssetDSU3 GROUP BY icbIndustry")
    List<Object> findEffWeightGroupByIcbIndustry();

    @Query("SELECT localMarket, SUM(effWeight) FROM AssetDSU3 GROUP BY localMarket")
    List<Object> findEffWeightGroupByLocalMarket();

    @Query("SELECT icbSuperSector, SUM(effWeight) FROM AssetDSU3 GROUP BY icbSuperSector")
    List<Object> findEffWeightGroupByIcbSuperSector();


}
