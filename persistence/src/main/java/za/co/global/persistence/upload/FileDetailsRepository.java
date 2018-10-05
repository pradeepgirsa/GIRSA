package za.co.global.persistence.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.FileDetails;

@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetails, Long> {
}
