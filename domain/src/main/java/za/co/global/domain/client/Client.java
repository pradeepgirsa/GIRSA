package za.co.global.domain.client;

import org.hibernate.annotations.NaturalId;
import za.co.global.domain.EntityStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 5500456840911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_name", unique = true, nullable = false)
    @NaturalId
    private String clientName;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "manco_code")
    private String mancoCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMancoCode() {
        return mancoCode;
    }

    public void setMancoCode(String mancoCode) {
        this.mancoCode = mancoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (clientName != null ? !clientName.equals(client.clientName) : client.clientName != null) return false;
        if (status != client.status) return false;
        if (description != null ? !description.equals(client.description) : client.description != null) return false;
        return mancoCode != null ? mancoCode.equals(client.mancoCode) : client.mancoCode == null;
    }

    @Override
    public int hashCode() {
        int result = clientName != null ? clientName.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mancoCode != null ? mancoCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", mancoCode='" + mancoCode + '\'' +
                '}';
    }
}
