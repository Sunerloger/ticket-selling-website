package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CartService service;
    private final HallPlanSeatService seatService;

    @Autowired
    public CartEndpoint(CartService cartService, HallPlanSeatService seatService){
        this.service = cartService;
        this.seatService = seatService;
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
    public void addToCart(@RequestBody HallPlanSeatDto seat) {
        LOGGER.info("Post /api/v1/cart");
        HallPlanSeatDto requestedSeat = seatService.getSeatById(seat.getId());

        if (requestedSeat == null){
            return;
        }
        if (!requestedSeat.getStatus().equals("FREE")){
            return;
        }

    }





}
