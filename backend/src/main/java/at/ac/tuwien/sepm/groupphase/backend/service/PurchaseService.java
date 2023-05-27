package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {

    PurchaseDto getPurchaseByPurchaseNr(Long purchaseNr, Long userId);

    void deletePurchase(Long purchaseNr, Long userId);

    List<PurchaseDto> getPurchasesOfUser(Long userId);
    void purchaseCartOfUser(Long userID, PurchaseCreationDto purchaseCreationDto);

    void purchaseReservationOfUser(Long purchaseCreationDto, PurchaseCreationDto reservationNr, Long userId);
}
