package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.BarraAssetInfo;

@Repository
public interface BarraAssetInfoRepository extends JpaRepository<BarraAssetInfo, Long> {

    BarraAssetInfo findByAssetId(String assetId);

    BarraAssetInfo findByNetIndicatorIsTrue();

}
