package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reservation")
public class ReservationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ReservationService service;
    private PurchaseService purchaseService;

    @Autowired
    public ReservationEndpoint(ReservationService reservationService, PurchaseService purchaseService) {
        this.service = reservationService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    @Operation(summary = "Gets a list of all Reservations from that user", security = @SecurityRequirement(name = "apiKey"))
    public List<ReservationDto> getReservations() {
        LOGGER.info("GET /api/v1/reservation");

        //TODO: acquire UserID
        //TODO: use real UserID
        Long userId = 1L;

        List<ReservationDto> itemList = service.getReservationsOfUser(userId);
        return itemList;
    }

    @GetMapping("/{reservationNr}")
    @Operation(summary = "Gets a list of all Reservations from that user", security = @SecurityRequirement(name = "apiKey"))
    public ReservationDto getReservation(@PathVariable Long reservationNr) {
        LOGGER.info("GET /api/v1/reservation/{}", reservationNr);

        //TODO: acquire UserID
        //TODO: use real UserID
        Long userId = 1L;

        ReservationDto reservation = service.getReservationOfUser(reservationNr, userId);
        if (reservation == null) {
            //something response;
            LOGGER.warn("something went wrong");
            return null;
        }
        return reservation;
    }

    @PostMapping
    @Operation(summary = "Create a new Reservation with the given seats", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> newReservation(@RequestBody List<SeatDto> seatDtoList) {
        LOGGER.info("Post /api/v1/reservation");
        try {
            //TODO: acquire UserID
            //TODO: use real UserID
            Long userId = 1L;

            service.addReservation(seatDtoList, userId);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reservationNr}")
    @Operation(summary = "Removes a Reservation by reservationnumber", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationNr) {
        LOGGER.info("Delete /api/v1/reservation/{}", reservationNr);

        //TODO: acquire UserID
        //TODO: use real UserID
        Long userId = 1L;

        this.service.deleteReservation(reservationNr, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reservationNr}/purchase")
    @Operation(summary = "Purchases a given amount of tickets of a Reservation", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> buyReservation(@RequestBody PurchaseCreationDto purchaseCreationDto, @PathVariable Long reservationNr) {
        LOGGER.info("Post /api/v1/cart/purchase");

        //TODO: acquire UserID
        //TODO: use real UserID
        Long userId = 1L;

        purchaseService.purchaseReservationOfUser(reservationNr, purchaseCreationDto, userId);
        return ResponseEntity.ok().build();
    }


}
