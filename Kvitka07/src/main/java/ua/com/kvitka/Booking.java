package ua.com.kvitka;

import java.util.List;
import java.util.Optional;

public class Booking {
    private Long id;
    private long userId;
    private int roomId;
    private double price;
    private String status;
    private List<String> amenities;

    public Booking(Long id, long userId, int roomId, double price, String status, List<String> amenities) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.price = price;
        this.status = status;
        this.amenities = amenities;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
}

// Dependencies for mocking
interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(Long id);
    List<Booking> findByUserId(long userId);
}

interface NotificationService {
    void sendConfirmation(long userId, String message);
    void sendRejection(long userId, String message);
    void sendCancellation(long userId, String message);
}

interface RoomService {
    boolean isRoomAvailable(int roomId);
    double getBasePrice(int roomId);
}