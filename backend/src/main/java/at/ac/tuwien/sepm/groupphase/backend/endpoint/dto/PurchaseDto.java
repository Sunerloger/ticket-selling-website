package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Purchase;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class PurchaseDto {
    private Long purchaseNr;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
    private String billAddress;
    private Long billAreaCode;
    private String billCityName;
    private List<TicketDto> ticketList;
    private Boolean canceled;

    public PurchaseDto() {
    }

    public PurchaseDto(List<TicketDto> ticketList, Purchase purchase) {
        this.purchaseNr = purchase.getPurchaseNr();
        this.purchaseDate = purchase.getDate();
        this.billAddress = purchase.getBillAddress();
        this.billAreaCode = purchase.getBillAreaCode();
        this.billCityName = purchase.getBillCityName();
        this.ticketList = ticketList;
        this.canceled = purchase.isCanceled();
    }


    public Long getPurchaseNr() {
        return purchaseNr;
    }

    public void setPurchaseNr(Long purchaseNr) {
        this.purchaseNr = purchaseNr;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public List<TicketDto> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketDto> ticketList) {
        this.ticketList = ticketList;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }
}
