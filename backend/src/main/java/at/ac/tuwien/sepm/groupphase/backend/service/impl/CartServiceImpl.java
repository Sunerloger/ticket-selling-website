package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanSeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private EventService eventService;
    private HallPlanSeatService seatService;
    private HallPlanService hallPlanService;
    private SeatRowService seatRowService;
    private HallPlanSeatRepository seatRepository;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, EventService eventService, HallPlanSeatService seatService, HallPlanService hallPlanService, SeatRowService seatRowService, HallPlanSeatRepository seatRepository) {
        this.cartRepository = cartRepository;
        this.eventService = eventService;
        this.seatService = seatService;
        this.hallPlanService = hallPlanService;
        this.seatRowService = seatRowService;
        this.seatRepository = seatRepository;
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
            if (seatDtoList.get(i) == null || seatDtoList.get(i).getId() == null){
                //TODO: add corresponding response (unprocessable entity)
                throw new NotFoundException();
            }

            int test = tryReserveSeat(seatDtoList.get(i).getId());
            if (test == 1){
                Cart cart = new Cart(userID , seatDtoList.get(i).getId());
                cartRepository.save(cart);
            } else if (test == 0){
                //seat was not FREE and thus was not added to the cart
            } else {
                throw new NotFoundException();
            }

        }
    }
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    private int tryReserveSeat(Long id){
        Optional<HallPlanSeat> optSeatToReserve = seatRepository.getSeatById(id);
        if (optSeatToReserve.isEmpty()){
            return -1;
        }
        HallPlanSeat seatToReserve = optSeatToReserve.get();
        if (seatToReserve.getStatus().equals(HallPlanSeatStatus.FREE)){
            seatToReserve.setStatus(HallPlanSeatStatus.RESERVED);
            seatRepository.saveAndFlush(seatToReserve);
            return 1;
        }
        return 0;
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
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteItem(Long itemID, Long userID) {
        Cart cart = cartRepository.findCartBySeatId(itemID);
        if (cart == null){
            return;
        }
        if (!cart.getUserId().equals(userID)){
            return;
        }
        Optional<HallPlanSeat> optSeatToRemove = seatRepository.getSeatById(itemID);
        if (optSeatToRemove.isEmpty()){
            //TODO: Add some kind of database error (reserved seat is not in database)
        }
        HallPlanSeat seatToRemove = optSeatToRemove.get();
        if (!seatToRemove.getStatus().equals(HallPlanSeatStatus.RESERVED)){
            //TODO: Add some kind of database error (reserved seat was in fact not reserved)
        }
        seatToRemove.setStatus(HallPlanSeatStatus.FREE);
        seatRepository.saveAndFlush(seatToRemove);
        cartRepository.deleteCartBySeatId(itemID);
    }


}
