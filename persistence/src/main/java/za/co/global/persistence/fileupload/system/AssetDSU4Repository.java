package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.AssetDSU4;

import java.util.List;

@Repository
public interface AssetDSU4Repository extends JpaRepository<AssetDSU4, Long> {

    AssetDSU4 findByAssetIdAndFundName(String assetId, String fundName);

    AssetDSU4 findByNetIndicatorIsTrueAndFundName(String fundName);

    @Query("SELECT DISTINCT(fundName) FROM AssetDSU4 WHERE netIndicator=false")
    List<String> findDistinctFundNameAndNetIndicatorFalse();

    List<AssetDSU4> findFirst10ByNetIndicatorIsFalseAndInstSubTypeAndFundNameOrderByEffWeightDesc(String instSubType, String fundName);

}
