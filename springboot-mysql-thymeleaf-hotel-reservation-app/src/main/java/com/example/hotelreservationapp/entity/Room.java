package com.example.hotelreservationapp.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Room", indexes = {
		@Index(name = "idx_room_id_roomnumber_unq", columnList = "id, roomNumber, pricePerNight, capacity", unique = true)
}, uniqueConstraints = {
		@UniqueConstraint(name = "uc_room_id_roomnumber", columnNames = {"id", "roomNumber", "pricePerNight", "capacity", "hotel_id"})
})
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(unique  = true)
	private int roomNumber;

	private double pricePerNight;

	private int capacity;
	
	@ManyToOne
    @JoinColumn(name = "hotel_id")
	private Hotel hotel;
	
	public Room() {
		
	}

	public Room(int roomNumber, double pricePerNight, int capacity) {
		super();
		this.roomNumber = roomNumber;
		this.pricePerNight = pricePerNight;
		this.capacity = capacity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}

