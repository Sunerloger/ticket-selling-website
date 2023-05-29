package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseDto;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/purchase")
public class PurchaseEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PurchaseService service;

    @Autowired
    public PurchaseEndpoint(PurchaseService service) {
        this.service = service;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Gets a list of all Purchases from that user", security = @SecurityRequirement(name = "apiKey"))
    public List<PurchaseDto> getPurchases() {
        LOGGER.info("GET /api/v1/purchase");
        //TODO: acquire UserID
        //TODO: use real UserID
        return service.getPurchasesOfUser(1L);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{purchaseNr}")
    @Operation(summary = "Get a single Purchase by its purchaseNr", security = @SecurityRequirement(name = "apiKey"))
    public PurchaseDto getPurchaseByNr(@PathVariable Long purchaseNr) {
        LOGGER.info("GET /api/v1/purchase/{}", purchaseNr);
        //TODO: acquire UserID
        //TODO: use real UserID

        return service.getPurchaseByPurchaseNr(purchaseNr, 1L);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{purchaseNr}")
    @Operation(summary = "Removes a Purchase by purchaseNr", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> cancelPurchase(@PathVariable Long purchaseNr) {
        LOGGER.info("Delete /api/v1/purchase/{}", purchaseNr);
        //TODO: acquire UserID
        //TODO: use real UserID

        service.deletePurchase(purchaseNr, 1L);
        return ResponseEntity.noContent().build();
    }

}
