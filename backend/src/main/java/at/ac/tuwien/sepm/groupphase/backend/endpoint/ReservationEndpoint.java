package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
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
@RequestMapping(value = "/api/v1/reservation")
public class ReservationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ReservationService service;

    @Autowired
    public ReservationEndpoint(ReservationService reservationService) {
        this.service = reservationService;
    }

    @GetMapping
    @Operation(summary = "Gets a list of all Reservations from that user", security = @SecurityRequirement(name = "apiKey"))
    public List<ReservationDto> getReservations() {
        LOGGER.info("GET /api/v1/reservation");

        //TODO: acquire UserID
        //TODO: use real UserID
        List<ReservationDto> itemList = service.getReservationsOfUser(1L);
        return itemList;
    }

    @PostMapping
    @Operation(summary = "Create a new Reservation with the given seats", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> newReservation(@RequestBody List<SeatDto> seatDtoList) {
        LOGGER.info("Post /api/v1/reservation");
        try {
            //TODO: acquire UserID
            //TODO: use real UserID
            service.addReservation(seatDtoList, 1L);
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
        this.service.deleteReservation(reservationNr, 1L);
        return ResponseEntity.noContent().build();
    }


}
