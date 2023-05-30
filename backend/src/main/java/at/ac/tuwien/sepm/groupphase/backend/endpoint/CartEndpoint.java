package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.CustomUserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CartService service;
    private PurchaseService purchaseService;
    private CustomUserDetailService userService;

    @Autowired
    public CartEndpoint(CartService cartService, PurchaseService purchaseService, CustomUserDetailService userService) {
        this.service = cartService;
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get a list of all CartItems", security = @SecurityRequirement(name = "apiKey"))
    public List<CartItemDto> getCart(@RequestHeader("Authorization") String token) {
        LOGGER.info("GET /api/v1/cart");

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null){
            return null;
        }

        return service.getItems(userId);
    }

    @Secured("ROLE_USER")
    @PostMapping
    @Operation(summary = "Add a list of Seats to the cart", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> addToCart(@RequestBody List<SeatDto> seatDtoList, @RequestHeader("Authorization") String token) {
        LOGGER.info("Post /api/v1/cart");

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null){
            return ResponseEntity.badRequest().build();
        }

        try {
            service.addItemList(seatDtoList, userId);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{id}")
    @Operation(summary = "Removes a Seat from the cart", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        LOGGER.info("Delete /api/v1/cart/{}", id);

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null){
            return ResponseEntity.badRequest().build();
        }

        try {
            this.service.deleteItem(id, userId);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_USER")
    @PostMapping("/purchase")
    @Operation(summary = "Purchases the tickets in the cart", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> buyCart(@RequestBody PurchaseCreationDto purchaseCreationDto, @RequestHeader("Authorization") String token) {
        LOGGER.info("Post /api/v1/cart/purchase");

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null){
            return ResponseEntity.badRequest().build();
        }

        purchaseService.purchaseCartOfUser(userId, purchaseCreationDto);
        return ResponseEntity.ok().build();
    }

}
