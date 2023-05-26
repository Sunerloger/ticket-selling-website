package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CartService service;
    private PurchaseService purchaseService;

    @Autowired
    public CartEndpoint(CartService cartService, PurchaseService purchaseService){
        this.service = cartService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    @Operation(summary = "Get a list of all CartItems", security = @SecurityRequirement(name = "apiKey"))
    public List<CartItemDto> getCart() {
        LOGGER.info("GET /api/v1/cart");

        //TODO: acquire UserID
        //TODO: use real UserID
        List<CartItemDto> itemList = service.getItems(1L);
        return itemList;
    }

    @PostMapping
    @Operation(summary = "Add a list of Seats to the cart", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> addToCart(@RequestBody List<SeatDto> seatDtoList) {
        LOGGER.info("Post /api/v1/cart");
        try {
        //TODO: acquire UserID
        //TODO: use real UserID
        service.addItemList(seatDtoList, 1L);
        } catch (NotFoundException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Removes a Seat from the cart", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id){
        LOGGER.info("Delete /api/v1/cart/{}", id);

        try {
            //TODO: use real UserID
            this.service.deleteItem(id, 1L);
        } catch (NotFoundException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/purchase")
    @Operation(summary = "Purchases the tickets in the cart", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> buyCart(@RequestBody PurchaseCreationDto purchaseCreationDto){
        LOGGER.info("Post /api/v1/cart/purchase");
        purchaseService.purchaseCartOfUser(1L, purchaseCreationDto);
        return ResponseEntity.ok().build();
    }

}
