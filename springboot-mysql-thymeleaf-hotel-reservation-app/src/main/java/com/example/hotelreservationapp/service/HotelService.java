package com.example.hotelreservationapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.hotelreservationapp.dao.HotelRepository;
import com.example.hotelreservationapp.entity.Hotel;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	public Optional<Hotel> getHotelById(Long id) {
		return hotelRepository.findById(id);
	}

	public boolean saveOrUpdateHotel(Hotel hotel) {
		try {
			hotelRepository.save(hotel);
			return true;
		} catch (DataIntegrityViolationException e) {
			return false;
		}
	}

	public void deleteHotel(Long id) {
		hotelRepository.deleteById(id);
	}

	public List<Hotel> getHotelsByCity(String city) {
		return hotelRepository.findByCity(city);
	}
}

