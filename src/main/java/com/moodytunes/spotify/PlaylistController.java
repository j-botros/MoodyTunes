package com.moodytunes.spotify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlaylistController {
    @GetMapping("/create-playlist")
    public String showForm() {
        return "playlist-form";
    }

    @PostMapping("/create-playlist")
    public String buildPlaylist(@RequestParam String name, @RequestParam String desc, @RequestParam String location, HttpSession session) {
        final String accessToken = (String) session.getAttribute("access_token");
        
        SpotifyService.handlePlaylistRedirect(accessToken, location, name, desc);
        
        return "redirect:/";
    }
}