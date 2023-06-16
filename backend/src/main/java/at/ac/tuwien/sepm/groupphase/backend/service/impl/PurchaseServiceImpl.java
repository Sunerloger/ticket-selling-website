package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Purchase;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.PurchaseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PurchaseService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private PurchaseRepository repository;
    private HallPlanSeatService seatService;
    private TicketService ticketService;
    private CartService cartService;
    private ReservationService reservationService;
    private CustomUserDetailService customUserDetailService;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository repository, HallPlanSeatService seatService, TicketService ticketService, CartService cartService, ReservationService reservationService, CustomUserDetailService customerUserDetailService) {
        this.repository = repository;
        this.seatService = seatService;
        this.cartService = cartService;
        this.ticketService = ticketService;
        this.reservationService = reservationService;
        this.customUserDetailService = customerUserDetailService;
    }

    @Override
    public PurchaseDto getPurchaseByPurchaseNr(Long purchaseNr, Long userId) {
        Purchase purchase = repository.findPurchasesByPurchaseNr(purchaseNr);
        //TODO: check if purchase belong to user cart

        List<Ticket> ticketList = purchase.getTicketList();
        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketDtoList.add(ticketService.ticketDtoFromTicket(ticket));
        }
        return new PurchaseDto(ticketDtoList, purchase);
    }

    @Override
    @Transactional
    public void deletePurchase(Long purchaseNr, Long userId) {
        Purchase purchase = repository.findPurchasesByPurchaseNr(purchaseNr);

        if (purchase == null) {
            return;
        }

        if (!purchase.getUserId().equals(userId)) {
            return;
        }

        for (Ticket ticket : purchase.getTicketList()) {
            seatService.freePurchasedSeat(ticket.getSeatId());
        }
        purchase.setCanceled(true);
        repository.save(purchase);
    }


    @Override
    public List<PurchaseDto> getPurchasesOfUser(Long id) {

        List<Purchase> purchaseList = repository.findPurchasesByUserIdOrderByPurchaseNrDesc(id);
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();

        //TODO: check if purchase belong to user cart

        for (Purchase purchase : purchaseList) {
            if (purchase.getTicketList().isEmpty()) {
                LOGGER.error("purchase doesnt have tickets");
                continue;
            }

            List<Ticket> ticketList = purchase.getTicketList();
            List<TicketDto> ticketDtoList = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                ticketDtoList.add(ticketService.ticketDtoFromTicket(ticket));
            }

            purchaseDtoList.add(new PurchaseDto(ticketDtoList, purchase));
        }
        return purchaseDtoList;
    }

    @Override
    public void purchaseCartOfUser(Long userId, PurchaseCreationDto purchaseCreationDto) {
        //TODO: verify request
        //TODO: check if items belong to user cart

        List<Ticket> ticketList = new ArrayList<>();

        if (purchaseCreationDto.getSeats() == null) {
            return;
        }
        for (SeatDto seatDto : purchaseCreationDto.getSeats()) {
            if (!cartService.itemBelongsToUserCart(seatDto.getId(), userId)) {
                continue;
            }

            if (seatService.purchaseReservedSeat(seatDto.getId())) {
                ticketList.add(new Ticket(seatDto.getId()));
                cartService.deleteItem(seatDto.getId(), userId, false);
            }
        }

        Purchase purchase = new Purchase();
        purchase.setDate(LocalDate.now());
        purchase.setUserId(userId);

        if (!purchaseCreationDto.getUseUserAddress()) {
            ApplicationUser user = customUserDetailService.getUserById(userId);
            purchase.setBillAddress(user.getAddress());
            purchase.setBillAreaCode(user.getAreaCode());
            purchase.setBillCityName(user.getCityName());
        } else {
            purchase.setBillAddress(purchaseCreationDto.getAddress());
            purchase.setBillAreaCode(purchaseCreationDto.getAreaCode());
            purchase.setBillCityName(purchaseCreationDto.getCity());
        }
        purchase.setTicketList(ticketList);
        if (!purchase.getTicketList().isEmpty()) {
            repository.save(purchase);
        }
    }

    @Override
    public void purchaseReservationOfUser(Long reservationNr, PurchaseCreationDto purchaseCreationDto, Long userId) {
        //TODO: verify request
        //TODO: check if items belong to reservation

        List<Ticket> ticketList = new ArrayList<>();

        if (purchaseCreationDto.getSeats() == null) {
            return;
            //TODO: some kind of error
        }
        for (SeatDto seatDto : purchaseCreationDto.getSeats()) {
            if (seatService.purchaseReservedSeat(seatDto.getId())) {
                ticketList.add(new Ticket(seatDto.getId()));
            }
        }

        reservationService.deleteReservation(reservationNr, userId);

        Purchase purchase = new Purchase();
        purchase.setDate(LocalDate.now());
        purchase.setUserId(userId);

        if (!purchaseCreationDto.getUseUserAddress()) {
            ApplicationUser user = customUserDetailService.getUserById(userId);
            purchase.setBillAddress(user.getAddress());
            purchase.setBillAreaCode(user.getAreaCode());
            purchase.setBillCityName(user.getCityName());
        } else {
            purchase.setBillAddress(purchaseCreationDto.getAddress());
            purchase.setBillAreaCode(purchaseCreationDto.getAreaCode());
            purchase.setBillCityName(purchaseCreationDto.getCity());
        }
        purchase.setTicketList(ticketList);
        repository.save(purchase);
    }

}
