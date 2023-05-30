package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseNr;

    private Long userId;
    private LocalDate purchaseDate;
    private String billAddress;
    private Long billAreaCode;
    private String billCityName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchaseNr")
    private List<Ticket> ticketList;

    public Long getPurchaseNr() {
        return purchaseNr;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return purchaseDate;
    }

    public void setDate(LocalDate date) {
        this.purchaseDate = date;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    public Long getBillAreaCode() {
        return billAreaCode;
    }

    public void setBillAreaCode(Long billAreaCode) {
        this.billAreaCode = billAreaCode;
    }

    public String getBillCityName() {
        return billCityName;
    }

    public void setBillCityName(String billCityName) {
        this.billCityName = billCityName;
    }
}
