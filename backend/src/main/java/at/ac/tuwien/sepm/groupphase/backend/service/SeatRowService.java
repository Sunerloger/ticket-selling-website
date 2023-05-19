package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import jakarta.xml.bind.ValidationException;

import java.util.List;

public interface SeatRowService {
    /**
     * Finds all seatrows in the system
     *
     * @return a List of seatRowDto objects representing all seat rows in the system
     */
    List<SeatRowDto> findAllSeatRows();

    /**
     * Retrieves all seat rows of a specific hall plan.
     *
     * @param id the ID of the hall plan
     * @return a list of SeatRowDtos belonging to the specified hall plan
     */
    List<SeatRowDto> findAllSeatRowsOfHallPlan(Long id);

    /**
     * Creates a new seat row.
     *
     * @param seatRowDto the SeatRowDto containing the information for the new seat row
     * @return the created SeatRowDto
     * @throws ValidationException if the seatRowDto is invalid
     */
    SeatRowDto createSeatRow(SeatRowDto seatRowDto) throws ValidationException;

    /**
     * Retrieves a seat row by its ID.
     *
     * @param id the ID of the seat row to retrieve
     * @return the SeatRowDto associated with the given ID
     */
    SeatRowDto getSeatRowById(Long id);

    /**
     * Updates a seat row with new information.
     *
     * @param seatRowDto the SeatRowDto containing the updated information
     * @return the updated SeatRowDto
     */
    SeatRowDto updateSeatRow(SeatRowDto seatRowDto);

    /**
     * Deletes a seat row by its ID.
     *
     * @param id the ID of the seat row to delete
     * @return true if the seat row was successfully deleted, false otherwise
     */
    boolean deleteSeatRowById(Long id);

}
