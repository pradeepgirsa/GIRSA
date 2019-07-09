package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.BarraAssetInfo;

import java.util.List;

@Repository
public interface BarraAssetInfoRepository extends JpaRepository<BarraAssetInfo, Long> {

    BarraAssetInfo findByAssetIdAndFundName(String assetId, String fundName);

    List<BarraAssetInfo> findByNetIndicatorIsTrue();

    BarraAssetInfo findByNetIndicatorIsTrueAndFundName(String fundName);

}
