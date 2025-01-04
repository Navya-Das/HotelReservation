package com.example.hotelreservationapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hotelreservationapp.entity.Hotel;
import com.example.hotelreservationapp.entity.Room;
import com.example.hotelreservationapp.service.HotelService;
import com.example.hotelreservationapp.service.RoomService;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    // List all hotels
    @GetMapping
    public String getAllHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "hotel-list";
    }

    // Show add hotel form
    @GetMapping("/new")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hotel-form";
    }

    // Add a new hotel
    @PostMapping
    public String addHotel(Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "hotel-form";
        }
        try {
            hotelService.saveOrUpdateHotel(hotel);
            redirectAttributes.addFlashAttribute("successMessage", "Hotel added successfully");
            return "redirect:/hotels";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", "Enter unique data.");
            return "hotel-form";
        }
    }

    // Show edit hotel form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel id: " + id));
        model.addAttribute("hotel", hotel);
        return "hotel-form";
    }

    // Update hotel
    @PostMapping("/edit/{id}")
    public String updateHotel(@PathVariable Long id, Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "hotel-form";
        }
        hotel.setId(id);
        try {
            hotelService.saveOrUpdateHotel(hotel);
            redirectAttributes.addFlashAttribute("successMessage", "Hotel updated successfully");
            return "redirect:/hotels";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", "Enter unique data.");
            return "hotel-form";
        }
    }

    // Delete hotel
    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelService.deleteHotel(id);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel deleted successfully");
        return "redirect:/hotels";
    }

    @GetMapping("/{hotelId}/rooms")
    public String getAllRoomsByHotel(@PathVariable Long hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel id: " + hotelId));
        List<Room> rooms = hotel.getRooms();
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    // View specific room details for a specific hotel
    @GetMapping("/rooms/{roomId}")
    public String getRoomByHotelAndRoomId(@PathVariable Long hotelId, @PathVariable Long roomId, Model model) {
        Room room = roomService.getRoomById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + roomId));

        // Ensure room belongs to the hotel
        if (!room.getHotel().getId().equals(hotelId)) {
            throw new IllegalArgumentException("Room does not belong to the specified hotel.");
        }

        model.addAttribute("room", room);
        return "room-details";
    }
 // Fetch a hotel by ID
    @GetMapping("/{id}")
    public String getHotelById(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel id: " + id));
        model.addAttribute("hotel", hotel);
        return "hotel-details"; // Ensure this template exists
    }
}

