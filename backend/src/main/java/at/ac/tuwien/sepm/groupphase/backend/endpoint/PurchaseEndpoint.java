package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.CustomUserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/purchase")
public class PurchaseEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        MethodHandles.lookup().lookupClass()
    );
    private final PurchaseService service;
    private final CustomUserDetailService userService;

    @Autowired
    public PurchaseEndpoint(
        PurchaseService service,
        CustomUserDetailService userService
    ) {
        this.service = service;
        this.userService = userService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(
        summary = "Gets a list of all Purchases from that user",
        security = @SecurityRequirement(name = "apiKey")
    )
    public ResponseEntity<Object> getPurchases(
        @RequestHeader("Authorization") String token
    ) {
        LOGGER.info("GET /api/v1/purchase");
        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            LOGGER.error("User with ROLE_USER could not be resolved");
            return ResponseEntity.internalServerError().body("Request could not be resolved!");
        }

        return ResponseEntity.ok(service.getPurchasesOfUser(userId));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{purchaseNr}")
    @Operation(
        summary = "Get a single Purchase by its purchaseNr",
        security = @SecurityRequirement(name = "apiKey")
    )
    public ResponseEntity<Object> getPurchaseByNr(
        @PathVariable Long purchaseNr,
        @RequestHeader("Authorization") String token
    ) {
        LOGGER.info("GET /api/v1/purchase/{}", purchaseNr);

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            LOGGER.error("User with ROLE_USER could not be resolved");
            return ResponseEntity.internalServerError().body("Request could not be resolved!");
        }
try{
    return ResponseEntity.ok(service.getPurchaseByPurchaseNr(purchaseNr, userId));
} catch (NotFoundException e){
    return ResponseEntity.noContent().build();
}

    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{purchaseNr}")
    @Operation(
        summary = "Removes a Purchase by purchaseNr",
        security = @SecurityRequirement(name = "apiKey")
    )
    public ResponseEntity<Object> cancelPurchase(
        @PathVariable Long purchaseNr,
        @RequestHeader("Authorization") String token
    ) {
        LOGGER.info("Delete /api/v1/purchase/{}", purchaseNr);

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            LOGGER.error("User with ROLE_USER could not be resolved");
            return ResponseEntity.internalServerError().body("Request could not be resolved!");
        }

        service.deletePurchase(purchaseNr, userId);
        return ResponseEntity.noContent().build();
    }
}
