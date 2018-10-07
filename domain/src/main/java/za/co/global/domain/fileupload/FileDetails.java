package za.co.global.domain.fileupload;

import za.co.global.domain.client.Client;
import za.co.global.domain.product.Product;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "file_details")
public class FileDetails {

    private static final long serialVersionUID = 5510456840222177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name="received_date")
    private Date receivedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_file_id")
    private FileDetails parentFileDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public FileDetails getParentFileDetails() {
        return parentFileDetails;
    }

    public void setParentFileDetails(FileDetails parentFileDetails) {
        this.parentFileDetails = parentFileDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileDetails that = (FileDetails) o;

        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (fileExtension != null ? !fileExtension.equals(that.fileExtension) : that.fileExtension != null)
            return false;
        if (receivedDate != null ? !receivedDate.equals(that.receivedDate) : that.receivedDate != null) return false;
        return parentFileDetails != null ? parentFileDetails.equals(that.parentFileDetails) : that.parentFileDetails == null;
    }

    @Override
    public int hashCode() {
        int result = client != null ? client.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (fileExtension != null ? fileExtension.hashCode() : 0);
        result = 31 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
        result = 31 * result + (parentFileDetails != null ? parentFileDetails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileDetails{" +
                "id=" + id +
                ", client=" + client +
                ", product=" + product +
                ", filePath='" + filePath + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", receivedDate=" + receivedDate +
                ", parentFileDetails=" + parentFileDetails +
                '}';
    }
}
