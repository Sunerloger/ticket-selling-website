package at.ac.tuwien.sepm.groupphase.backend.service;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;

import java.util.List;

public interface CartService {

    public void addItem(HallPlanSeatDto seat);
    public List<CartItemDto> getItems(Long userID);
}
