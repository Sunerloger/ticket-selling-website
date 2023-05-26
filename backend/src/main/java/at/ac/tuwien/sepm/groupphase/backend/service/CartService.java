package at.ac.tuwien.sepm.groupphase.backend.service;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;

public interface CartService {
    void addItemList(List<SeatDto> seatDtoList, Long userId) throws NotFoundException;
    public List<CartItemDto> getItems(Long userID);
    void deleteItem(Long itemID, Long userID);
}
