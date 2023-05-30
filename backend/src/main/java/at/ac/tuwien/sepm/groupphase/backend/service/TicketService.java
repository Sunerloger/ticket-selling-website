package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

public interface TicketService {
    TicketDto ticketDtoFromTicket(Ticket ticket);
}
