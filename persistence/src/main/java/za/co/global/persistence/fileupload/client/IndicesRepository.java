package za.co.global.persistence.fileupload.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.client.Indices;

@Repository
public interface IndicesRepository extends JpaRepository<Indices, Long> {

    Indices findBySecurityAndType(String security, String type);
}
