package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSeatDto;
import jakarta.transaction.Transactional;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSeatBulkDto;
import jakarta.xml.bind.ValidationException;

import java.util.List;

public interface HallPlanSeatService {

    /**
     * Adds a seat to a seat row.
     *
     * @param seatDto specifies the hallPlan and SeatRow to where the seat should be inserted
     * @return the inserted seatDto
     */
    HallPlanSeatDto addSeat(HallPlanSeatDto seatDto);

    /**
     * Retrieves a seat by its ID.
     *
     * @param seatId the ID of the seat to retrieve
     * @return the seatDto associated with the given ID
     */
    HallPlanSeatDto getSeatById(Long seatId);

    /**
     * Updates a seat with new information.
     *
     * @param seatDto the seatDto containing the updated information
     * @return the updated seatDto
     */
    HallPlanSeatDto updateSeat(HallPlanSeatDto seatDto) throws ValidationException;

    /**
     * Deletes a seat by its ID.
     *
     * @param seatId the ID of the seat to delete
     */
    void deleteSeat(Long seatId);

    boolean doesSeatExist(Long seatId);

    boolean purchaseReservedSeat(Long seatId);

    boolean tryReserveSeat(Long seatId);

    @Transactional
    boolean cancelReservation(Long seatId);

    @Transactional
    boolean freePurchasedSeat(Long seatId);

    /**
     * Adds multiple seats to the hall plan.
     *
     * @param bulkDto the bulkDto containing the seats to be added
     * @return a list of inserted seatDtos
     */
    List<HallPlanSeatDto> bulkAddSeats(HallPlanSeatBulkDto bulkDto);

    /**
     * Updates multiple seats with new information.
     *
     * @param bulkDto the bulkDto containing the seats to be updated
     * @return a list of updated seatDtos
     */
    List<HallPlanSeatDto> bulkUpdateSeats(HallPlanSeatBulkDto bulkDto);

}
