package za.co.global.domain.report;

import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.InstrumentData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "report_data")
public class ReportData implements Serializable {

    private static final long serialVersionUID = 7510456840911411232L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date")
    private Date reportDate;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "report_data_id", referencedColumnName = "ID", nullable = false, updatable = true)
    private List<InstrumentData> instrumentDataList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", nullable = false)
    private ReportStatus reportStatus = ReportStatus.REGISTERED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "ID", nullable = false)
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<InstrumentData> getInstrumentDataList() {
        return instrumentDataList;
    }

    public void setInstrumentDataList(List<InstrumentData> instrumentDataList) {
        this.instrumentDataList = instrumentDataList;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportData that = (ReportData) o;

        if (reportDate != null ? !reportDate.equals(that.reportDate) : that.reportDate != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (instrumentDataList != null ? !instrumentDataList.equals(that.instrumentDataList) : that.instrumentDataList != null) return false;
        if (reportStatus != that.reportStatus) return false;
        return client != null ? client.equals(that.client) : that.client == null;
    }

    @Override
    public int hashCode() {
        int result = reportDate != null ? reportDate.hashCode() : 0;
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (instrumentDataList != null ? instrumentDataList.hashCode() : 0);
        result = 31 * result + (reportStatus != null ? reportStatus.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "id=" + id +
                ", reportDate=" + reportDate +
                ", createdDate=" + createdDate +
                ", instrumentDataList=" + instrumentDataList +
                ", reportStatus=" + reportStatus +
                ", client=" + client +
                '}';
    }
}
