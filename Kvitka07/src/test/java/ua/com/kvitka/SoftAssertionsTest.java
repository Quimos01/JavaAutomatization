package ua.com.kvitka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoftAssertionsTest {
    @Mock
    BookingRepository repository;
    @Mock
    NotificationService notificationService;
    @Mock
    RoomService roomService;
    @InjectMocks
    BookingService bookingService;

    @Test
    void testCancelBooking() {
        Long bookingId = 34L;
        long userId = 1;
        Booking mockBooking = new Booking(bookingId, userId, 7, 1000.00, "CONFIRMED", List.of("WiFi", "TV", "Breakfast"));

        when(repository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        bookingService.cancelBooking(bookingId);

        assertEquals("CANCELLED", mockBooking);

        verify(repository, times(1)).save(mockBooking);

        verify(notificationService, times(1)).sendCancellation(userId, anyString());

        verify(notificationService, never()).sendConfirmation(anyLong(), anyString());
    }
}
