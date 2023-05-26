package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private EventService eventService;
    private HallPlanSeatService seatService;
    private SeatRowService seatRowService;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, EventService eventService, HallPlanSeatService seatService, SeatRowService seatRowService) {
        this.cartRepository = cartRepository;
        this.eventService = eventService;
        this.seatService = seatService;
        this.seatRowService = seatRowService;
    }

    @Override
    public void addItemList(List<SeatDto> seatDtoList, Long userID) throws NotFoundException{
        if (seatDtoList.isEmpty()){
            //TODO: add corresponding response
            //TODO: Use correct Exception
            throw new NotFoundException();
        }

        for (SeatDto seatDto: seatDtoList) {
            if (!seatService.doesSeatExist(seatDto.getId())){
                throw new NotFoundException();
            }
            if (seatService.tryReserveSeat(seatDto.getId())){
                Cart cart = new Cart(userID , seatDto.getId());
                cartRepository.save(cart);
            } else {
                //TODO: Prepare answer to show user not all items were added
            }
        }
    }

    @Override
    public List<CartItemDto> getItems(Long userID){
        List<CartItemDto> itemList = new ArrayList<>();

        List<Cart> cartItemList = cartRepository.findByUserId(1L);
        for (Cart cart: cartItemList){
            HallPlanSeatDto hallPlanSeatDto = seatService.getSeatById(cart.getSeatId());
            SeatRowDto rowDto = seatRowService.getSeatRowById(hallPlanSeatDto.getSeatrowId());
            EventDetailDto eventDto = eventService.getEventById(1L); //TODO:get correct Event
            SeatDto seatDto = new SeatDto(hallPlanSeatDto, rowDto);

            itemList.add(new CartItemDto(seatDto,eventDto));
        }

        return itemList;
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId, Long userID) {
        Cart cart = cartRepository.findCartBySeatId(itemId);
        if (cart == null){
            return;
        }
        if (!cart.getUserId().equals(userID)){
            return;
        }

        //TODO: change this to not use itemID aka SeatID but a different identifier to allow multiple items of the same id in the same cart.

        seatService.cancelReservation(itemId);
        cartRepository.deleteCartBySeatId(itemId);
    }


}
