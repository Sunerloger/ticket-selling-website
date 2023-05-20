package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventTest implements TestData {
    @Autowired
    EventRepository eventRepository;
    @Test
    public void testSaveEvent() {
        Event event = new Event();
        // set event properties here

        Event savedEvent = eventRepository.save(event);

        assertNotNull(savedEvent.getId());
        // assert that the saved event has all the properties set correctly
    }

    @Test
    public void testDeleteEventById() {
        Event event = new Event();
        // set event properties here

        Event savedEvent = eventRepository.save(event);
        Long eventId = savedEvent.getId();

        eventRepository.deleteById(eventId);

        Optional<Event> deletedEvent = eventRepository.findById(eventId);
        assertTrue(deletedEvent.isEmpty());
    }

    @Test
    public void testGetAllEvents() {
        Event event1 = new Event();
        // set event1 properties here

        Event event2 = new Event();
        // set event2 properties here

        eventRepository.save(event1);
        eventRepository.save(event2);

        List<Event> events = eventRepository.findAll();

        assertNotNull(events);
        assertEquals(2, events.size());
    }

    @Test
    public void testGetEventById() {
        Event event = new Event();
        // set event properties here

        Event savedEvent = eventRepository.save(event);
        Long eventId = savedEvent.getId();

        Optional<Event> retrievedEvent = eventRepository.findById(eventId);

        assertNotNull(retrievedEvent);
        assertTrue(retrievedEvent.isPresent());
        assertEquals(event.getTitle(), retrievedEvent.get().getTitle());
        // assert other properties here
    }
}
