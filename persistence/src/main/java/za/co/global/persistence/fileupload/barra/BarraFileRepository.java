package za.co.global.persistence.fileupload.barra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.barra.BarraFile;
import za.co.global.domain.fileupload.client.InstitutionalDetails;

@Repository
public interface BarraFileRepository extends JpaRepository<BarraFile, Long> {
}
