package com.example.hotelreservationapp.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Hotel", indexes = {
		@Index(name = "idx_hotel_id_name_city_address_unq", columnList = "id, name, city, address", unique = true)
}, uniqueConstraints = {
		@UniqueConstraint(name = "uc_hotel_id_name_city_address", columnNames = {"id", "name", "city", "address"})
})
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@Column(unique = true)
	private String city;

	@Column(unique = true)
	private String address;

	@Column(unique = true)
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<Room> rooms;

	public Hotel() {}

	public Hotel(String name, String city, String address) {
		super();
		this.name = name;
		this.city = city;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}
