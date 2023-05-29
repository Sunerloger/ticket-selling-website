package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;

public class PurchaseCreationDto {
    private boolean useUserAddress;
    private String address;
    private Long areaCode;
    private String city;
    private List<SeatDto> seats;

    public PurchaseCreationDto() {

    }

    public PurchaseCreationDto(boolean useUserAddress, String address, Long areaCode, String city, List<SeatDto> seatDtoList) {
        this.useUserAddress = useUserAddress;
        this.address = address;
        this.areaCode = areaCode;
        this.city = city;
        this.seats = seatDtoList;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> ticketList) {
        this.seats = ticketList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isUseUserAddress() {
        return useUserAddress;
    }

    public void setUseUserAddress(boolean useUserAddress) {
        this.useUserAddress = useUserAddress;
    }
}
