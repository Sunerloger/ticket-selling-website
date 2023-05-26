package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {

    List<PurchaseDto> getPurchasesOfUser(Long userId);
    void purchaseCartOfUser(Long userID, PurchaseCreationDto purchaseCreationDto);
}
