package za.co.global.persistence.fileupload.barra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.barra.DSU5_GIRREP4;
import za.co.global.domain.fileupload.client.NumberOfAccounts;

@Repository
public interface DSU5_GIRREP4Repository extends JpaRepository<NumberOfAccounts, Long> {

//    DSU5_GIRREP4 findByAssetId(String assetId);
}
