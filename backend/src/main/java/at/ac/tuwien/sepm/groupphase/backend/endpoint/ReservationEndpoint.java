package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
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
@RequestMapping(value = "/api/v1/reservation")
public class ReservationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ReservationService service;
    private PurchaseService purchaseService;
    private CustomUserDetailService userService;

    @Autowired
    public ReservationEndpoint(ReservationService reservationService, PurchaseService purchaseService, CustomUserDetailService userService) {
        this.service = reservationService;
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Gets a list of all Reservations from that user", security = @SecurityRequirement(name = "apiKey"))
    public List<ReservationDto> getReservations(@RequestHeader("Authorization") String token) {
        LOGGER.info("GET /api/v1/reservation");

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            return null;
        }

        return service.getReservationsOfUser(userId);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{reservationNr}")
    @Operation(summary = "Gets a list of all Reservations from that user", security = @SecurityRequirement(name = "apiKey"))
    public ReservationDto getReservation(@PathVariable Long reservationNr, @RequestHeader("Authorization") String token) {
        LOGGER.info("GET /api/v1/reservation/{}", reservationNr);

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            return null;
        }

        ReservationDto reservation = service.getReservationOfUser(reservationNr, userId);
        if (reservation == null) {
            //something response;
            LOGGER.warn("something went wrong");
            return null;
        }

        return reservation;
    }

    @Secured("ROLE_USER")
    @PostMapping
    @Operation(summary = "Create a new Reservation with the given seats", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> newReservation(@RequestBody List<SeatDto> seatDtoList, @RequestHeader("Authorization") String token) {
        LOGGER.info("Post /api/v1/reservation");

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            return null;
        }

        try {
            service.addReservation(seatDtoList, userId);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{reservationNr}")
    @Operation(summary = "Removes a Reservation by reservationnumber", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationNr, @RequestHeader("Authorization") String token) {
        LOGGER.info("Delete /api/v1/reservation/{}", reservationNr);

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            return null;
        }

        this.service.deleteReservation(reservationNr, userId);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_USER")
    @PostMapping("/{reservationNr}/purchase")
    @Operation(summary = "Purchases a given amount of tickets of a Reservation", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> buyReservation(@RequestBody PurchaseCreationDto purchaseCreationDto, @PathVariable Long reservationNr, @RequestHeader("Authorization") String token) {
        LOGGER.info("Post /api/v1/cart/purchase");

        Long userId = userService.getUserIdFromToken(token);
        if (userId == null) {
            return null;
        }

        purchaseService.purchaseReservationOfUser(reservationNr, purchaseCreationDto, userId);
        return ResponseEntity.ok().build();
    }


}
