package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;
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
    private HallPlanService hallPlanService;
    private SeatRowService seatRowService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, EventService eventService, HallPlanSeatService seatService, HallPlanService hallPlanService, SeatRowService seatRowService) {
        this.cartRepository = cartRepository;
        this.eventService = eventService;
        this.seatService = seatService;
        this.hallPlanService = hallPlanService;
        this.seatRowService = seatRowService;
    }

    @Override
    public void addItem(HallPlanSeatDto seat, Long userID) throws NotFoundException {
        HallPlanSeatDto requestedSeat = seatService.getSeatById(seat.getId());

        if (requestedSeat == null){
            //TODO: add corresponding response
            throw new NotFoundException();
        }

        if (requestedSeat.getStatus() != HallPlanSeatStatus.FREE) {
            //TODO: add corresponding response
            //TODO: Use correct Exception
            throw new NotFoundException();
        }

        Cart cart = new Cart(userID , seat.getId());
        cartRepository.save(cart);
    }

    @Override
    public void addItemList(List<HallPlanSeatDto> seatDtoList, Long userID) throws NotFoundException{
        if (seatDtoList.isEmpty()){
            //TODO: add corresponding response
            //TODO: Use correct Exception
            throw new NotFoundException();
        }

        for(int i = 0; i < seatDtoList.size(); i++) {
            if (seatDtoList.get(i) == null){
                //TODO: add corresponding response
                throw new NotFoundException();
            }
            if (seatDtoList.get(i).getStatus() != HallPlanSeatStatus.FREE) {
                //TODO: add corresponding response
                //TODO: Use correct Exception
                throw new NotFoundException();
            }
        }

        for(int i = 0; i < seatDtoList.size(); i++) {
            Cart cart = new Cart(userID , seatDtoList.get(i).getId());
            cartRepository.save(cart);
        }
    }

    @Override
    public List<CartItemDto> getItems(Long userID){
        List<Cart> cartItemList = cartRepository.findByUserId(1L);

        List<CartItemDto> itemList = new ArrayList<>();

        for(int i = 0; i < cartItemList.size(); i++) {
            EventDetailDto event = eventService.getEventById(1L); //TODO:get correct Event

            HallPlanSeatDto seat = seatService.getSeatById(cartItemList.get(i).getSeatId());
            //TODO:get row by Id

            CartItemDto newitem = new CartItemDto();
            newitem.setEvent(event);
            newitem.setSeat(seat);

            itemList.add(newitem);
        }

        return itemList;
    }

    @Override
    @Transactional
    public void deleteItem(Long itemID, Long userID) throws NotFoundException{
        Cart cart = cartRepository.findCartBySeatId(itemID);
        if (cart == null){
            throw new NotFoundException();
        }
        if (!cart.getUserId().equals(userID)){
            throw new NotFoundException();
        }
        cartRepository.deleteCartBySeatId(itemID);
    }


}
