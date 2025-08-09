package com.moodytunes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodytunes.spotify.SpotifyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlaylistController {
    @GetMapping("/create-playlist")
    public String showForm(HttpSession session) {
        System.out.println("GET /create-playlist called!");
        System.out.println("Session ID: " + session.getId());
        System.out.println("Access token in session: " + session.getAttribute("access_token"));
        return "playlist-form";
    }

    @PostMapping("/create-playlist")
    public String buildPlaylist(@RequestParam String name, @RequestParam String desc, @RequestParam String location, HttpSession session) {
        System.out.println("POST /create-playlist called!");
        final String accessToken = (String) session.getAttribute("access_token");
        
        SpotifyService.handlePlaylistRedirect(accessToken, location, name, desc);
        
        return "redirect:/";
    }
}