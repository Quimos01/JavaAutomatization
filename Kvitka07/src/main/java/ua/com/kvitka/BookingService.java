package ua.com.kvitka;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final RoomService roomService;

    public BookingService(BookingRepository bookingRepository,
                          NotificationService notificationService,
                          RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
        this.roomService = roomService;
    }

    // Creating bookings with discount logic
    public Booking createBooking(Long userId, int roomId, boolean isVip) {
        if (!roomService.isRoomAvailable(roomId)) {
            notificationService.sendRejection(userId, "Room is not available");
            return new Booking(null, userId, roomId, 0.0, "REJECTED", Collections.emptyList());
        }

        double price = roomService.getBasePrice(roomId);

        if (isVip) {
            price = price * 0.8;
        }

        Booking booking = new Booking(null, userId, roomId, price, "CONFIRMED", Arrays.asList("WiFi", "Breakfast"));
        Booking savedBooking = bookingRepository.save(booking);

        notificationService.sendConfirmation(userId, "Booking confirmed!");

        return savedBooking;
    }

    // for testing verify, times, never
    public void cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
            notificationService.sendCancellation(booking.getUserId(), "Your booking is cancelled");
        }
    }

    // List for AssertJ List assertions
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}