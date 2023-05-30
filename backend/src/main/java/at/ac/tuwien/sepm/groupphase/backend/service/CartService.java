package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;

public interface CartService {
    /**
     * Adds a List of items to the Cart of the given user
     *
     * @param seatDtoList the list of Seats to add to the cart
     * @param userId      the id of the user
     * @throws NotFoundException if one of the seats doesn't exist
     */
    void addItemList(List<SeatDto> seatDtoList, Long userId) throws NotFoundException;

    /**
     * Fetches a list of all the items in the given users cart
     *
     * @param userId the id of the user
     * @return a List of CartItemDtos of the given user
     */
    List<CartItemDto> getItems(Long userId);

    /**
     * removes a seat from the cart of the given user
     *
     * @param itemId the id of the to delete seat
     * @param userId the id of the user
     */
    void deleteItem(Long itemId, Long userId);
}
