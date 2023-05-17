package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public void addItem(HallPlanSeatDto seat) throws NotFoundException {
        HallPlanSeatDto requestedSeat = seatService.getSeatById(seat.getId());

        if (requestedSeat == null){
            throw new NotFoundException();
            //TODO: add corresponding response
        }

        if (requestedSeat.getStatus() != HallPlanSeatStatus.FREE) {
            //TODO: add corresponding response
            throw new NotFoundException();
        }

        //TODO: Add real user id
        Cart cart = new Cart(1L , seat.getId());
        cartRepository.save(cart);
    }

    @Override
    public List<CartItemDto> getItems(Long userID){
        //List<Cart> cartItemList = cartRepository.findByUserId(1L);

        //TODO: get List of SeatIDs from CartRepository
        //TODO: Map info of each Seat into CartItemDto
        //TODO: return list of DTOs
        List<CartItemDto> itemList = new ArrayList<>();
        EventDetailDto event = new EventDetailDto();
        event.setId(1L);
        event.setAddress("Wiedner Hauptstraße 12");
        event.setCategory("SEPM");
        event.setCityname("Vienna");
        event.setDescription("aasdasd");
        event.setDuration(LocalTime.of(1,30));
        event.setStartTime(LocalTime.of(12,00));
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(LocalDate.of(2020,12,12));
        event.setDate(dateList);
        event.setTitle("Spassiges SEPM");

        HallPlanSectionDto section = new HallPlanSectionDto();
        section.setId(1L);
        section.setName("funny section");
        section.setColor("red");
        section.setPrice(12L);

        HallPlanSeatDto seat = new HallPlanSeatDto();
        seat.setId(1L);
        seat.setStatus(HallPlanSeatStatus.FREE);
        seat.setType(HallPlanSeatType.SEAT);
        seat.setCapacity(1L);
        seat.setSeatNr(12L);
        seat.setSeatrowId(1L);
        seat.setSection(section);

        CartItemDto newitem = new CartItemDto();
        newitem.setEvent(event);
        newitem.setSeat(seat);

        itemList.add(newitem);
        itemList.add(newitem);



        /*
        if (cartItemList.isEmpty()){
            return null;
        }

        List<CartItemDto> returnList = new ArrayList<>();

        for(int i = 0; i < cartItemList.size(); i++) {
            //get seat
            HallPlanSeatDto currentSeat = seatService.getSeatById(cartItemList.get(i).getSeatId());
            //get section
            HallPlanSectionDto currentSection = currentSeat.getSection();
            //get row
            seatRowService.getSeatRowById(currentSeat.getSeatrowId());
            //get hallplan (to get event)

            //get event

            //save everything in CartItemDto and add to list

        }

         */
        return itemList;
    }
}
