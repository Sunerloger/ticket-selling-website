package at.ac.tuwien.sepm.groupphase.backend.service;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;

public interface CartService {


    public void addItem(HallPlanSeatDto seat, Long id) throws NotFoundException;

    void addItemList(List<HallPlanSeatDto> seatDtoList, Long userId) throws NotFoundException;

    public List<CartItemDto> getItems(Long userID);

    void deleteItem(Long itemID, Long userID) throws NotFoundException;
}
