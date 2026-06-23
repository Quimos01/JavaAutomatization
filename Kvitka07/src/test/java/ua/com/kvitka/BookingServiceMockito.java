package ua.com.kvitka;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceMockito {
    @Mock
    BookingRepository repository;
    @Mock
    NotificationService notificationService;
    @Mock
    RoomService roomService;
    @InjectMocks
    BookingService bookingService;

    // 1. Testing 3 scenarios
    @Test
    void testRegularUserBooking() {
        long userId = 59;
        int roomId = 10;
        double expectedPrice = 1500.00;

        when(roomService.isRoomAvailable(roomId)).thenReturn(true);
        when(roomService.getBasePrice(roomId)).thenReturn(expectedPrice);

        Booking mockBooking = new Booking(111L, userId, roomId, expectedPrice, "CONFIRMED", List.of("WiFi", "TV", "Breakfast"));
        when(repository.save(any(Booking.class))).thenReturn(mockBooking);

        Booking resultBooking = bookingService.createBooking(userId, roomId, false);

        assertEquals("CONFIRMED", resultBooking.getStatus());
        assertEquals(expectedPrice, resultBooking.getPrice());
    }

    @Test
    void testVipUserBooking() {
        long userId = 16;
        int roomId = 13;
        double fullPrice = 2000.00;
        double expectedPrice = fullPrice * 0.8;

        when(roomService.isRoomAvailable(roomId)).thenReturn(true);
        when(roomService.getBasePrice(roomId)).thenReturn(fullPrice);

        Booking mockVipBooking = new Booking(111L, userId, roomId, expectedPrice, "CONFIRMED", List.of("WiFi", "TV", "Breakfast"));
        when(repository.save(any(Booking.class))).thenReturn(mockVipBooking);

        Booking resultVipBooking = bookingService.createBooking(userId, roomId, true);

        assertEquals("CONFIRMED", resultVipBooking.getStatus());
        assertEquals(expectedPrice, resultVipBooking.getPrice());
    }

    @Test
    void testRejectedBooking() {
        long userId = 12;
        int roomId = 4;
        double expectedPrice = 0;

        when(roomService.isRoomAvailable(roomId)).thenReturn(false);

        Booking resultBooking = bookingService.createBooking(userId, roomId, false);

        assertEquals("REJECTED", resultBooking.getStatus());
        assertEquals(expectedPrice, resultBooking.getPrice());
    }

    // 2. Testing with verify(), never(), times(n)
    @Test
    void testCancelBooking_verify() {
        Long bookingId = 34L;
        long userId = 1L;
        Booking mockBooking = new Booking(bookingId, userId, 7, 1000.00, "CONFIRMED", List.of("WiFi", "TV"));
        when(repository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        bookingService.cancelBooking(bookingId);

        assertEquals("CANCELLED", mockBooking.getStatus());
        verify(repository).save(mockBooking);
    }

    @Test
    void testCancelBooking_times() {
        Long bookingId = 34L;
        long userId = 1L;
        Booking mockBooking = new Booking(bookingId, userId, 7, 1000.00, "CONFIRMED", List.of("WiFi", "TV"));
        when(repository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        bookingService.cancelBooking(bookingId);

        verify(notificationService, times(1)).sendCancellation(userId, "Your booking is cancelled");
    }

    @Test
    void testCancelBooking_never() {
        Long bookingId = 99L;
        when(repository.findById(bookingId)).thenReturn(Optional.empty());

        bookingService.cancelBooking(bookingId);

        verify(notificationService, never()).sendConfirmation(anyLong(), anyString());
        verify(notificationService, never()).sendCancellation(anyLong(), anyString());
        verify(repository, never()).save(any(Booking.class));
    }

    // 3. AssertJ SoftAssertions tests
    @Test
    void testBookingData() {
        Booking booking = new Booking(12L, 43, 7, 5000.00, "CONFIRMED", List.of("WiFi", "TV"));

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(booking.getId())
                .as("Booking ID check")
                .isEqualTo(12L);

        softAssertions.assertThat(booking.getUserId())
                .as("User ID check")
                .isEqualTo(43);

        softAssertions.assertThat(booking.getRoomId())
                .as("Room ID check")
                .isEqualTo(7);

        softAssertions.assertThat(booking.getPrice())
                .as("Room price check")
                .isEqualTo(5000.0);

        softAssertions.assertThat(booking.getStatus())
                .as("Booking status check")
                .isEqualTo("CONFIRMED");

        softAssertions.assertAll();
    }

    // 4. AssertJ lists tests
    @Test
    void testUserBookingsList() {
        long userId = 1;

        Booking booking1 = new Booking(12L, 43, 7, 2000.00, "CONFIRMED", List.of("WiFi", "TV"));
        Booking booking2 = new Booking(12L, 43, 7, 5000.00, "CANCELLED", List.of("WiFi", "Breakfast", "TV", "Spa"));

        when(repository.findByUserId(userId)).thenReturn(List.of(booking1, booking2));

        List<Booking> userBookings = bookingService.getUserBookings(userId);

        assertThat(userBookings)
                .as("Booking list should not be empty")
                .isNotEmpty();

        assertThat(userBookings)
                .as("All booking statuses check")
                .extracting(Booking::getStatus)
                .containsExactlyInAnyOrder("CANCELLED", "CONFIRMED");

        List<String> firstBookingAmenities = userBookings.getFirst().getAmenities();
        assertThat(firstBookingAmenities)
                .as("Amenities list check")
                .contains("WiFi")
                .doesNotContain("Pool")
                .containsExactlyInAnyOrder("TV", "WiFi");
    }

    // 5. Mutant tests (PIT)
    @Test
    void weakVipBookingTest() {
        long userId = 99;
        int roomId = 10;
        double basePrice = 1000.00;

        when(roomService.isRoomAvailable(roomId)).thenReturn(true);
        when(roomService.getBasePrice(roomId)).thenReturn(basePrice);

        Booking mockBooking = new Booking(1L, userId, roomId, 800.00, "CONFIRMED", List.of("WiFi"));
        when(repository.save(any(Booking.class))).thenReturn(mockBooking);

        Booking result = bookingService.createBooking(userId, roomId, true);

        assertEquals("CONFIRMED", result.getStatus());
    }

    @Test
    void strongVipBookingTest() {
        long userId = 99;
        int roomId = 10;
        double basePrice = 100.0;

        when(roomService.isRoomAvailable(roomId)).thenReturn(true);
        when(roomService.getBasePrice(roomId)).thenReturn(basePrice);

        when(repository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking result = bookingService.createBooking(userId, roomId, true);

        assertEquals("CONFIRMED", result.getStatus());

        assertEquals(80.0, result.getPrice(), "VIP clients have 20% discount");
    }
    }
}
