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

import com.example.hotelreservationapp.entity.Room;
import com.example.hotelreservationapp.service.RoomService;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms
    @GetMapping
    public String getAllRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "room-list";
    }

    // Get a room by ID
    @GetMapping("/{id}")
    public String getRoomById(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + id));
        model.addAttribute("room", room);
        return "room-details";
    }

    // Add a new room
    @GetMapping("/new")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "room-form";
    }

    @PostMapping
    public String addRoom(Room room, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "room-form";
        }
        try {
            roomService.saveOrUpdateRoom(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room added successfully");
            return "redirect:/rooms";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", "Please enter unique details.");
            return "room-form";
        }
    }
}
