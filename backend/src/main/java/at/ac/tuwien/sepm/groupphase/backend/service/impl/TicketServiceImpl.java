package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatRowService;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    private HallPlanSeatService seatService;
    private SeatRowService rowService;
    private EventService eventService;

    public TicketServiceImpl(HallPlanSeatService seatService, SeatRowService rowService, EventService eventService) {
        this.seatService = seatService;
        this.rowService = rowService;
        this.eventService = eventService;
    }

    @Override
    public TicketDto ticketDtoFromTicket(Ticket ticket) {
        HallPlanSeatDto hallPlanSeatDto = seatService.getSeatById(ticket.getSeatId());
        SeatRowDto rowDto = rowService.getSeatRowById(hallPlanSeatDto.getSeatrowId());
        EventDetailDto eventDetailDto = eventService.getEventById(1L); //TODO: get correct Event

        SeatDto seatDto = new SeatDto(hallPlanSeatDto, rowDto);
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketNr(ticket.getTicketNr());
        ticketDto.setSeat(seatDto);
        ticketDto.setEvent(eventDetailDto);
        return ticketDto;
    }
}
