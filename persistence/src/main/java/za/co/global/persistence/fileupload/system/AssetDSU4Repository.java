package za.co.global.persistence.fileupload.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.system.AssetDSU4;

import java.util.List;

@Repository
public interface AssetDSU4Repository extends JpaRepository<AssetDSU4, Long> {

    AssetDSU4 findByAssetId(String assetId);

    List<AssetDSU4> findFirst10ByNetIndicatorIsFalseAndInstSubTypeOrderByEffWeightDesc(String instSubType);

}
