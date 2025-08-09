package com.moodytunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodytunes.spotify.SpotifyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CallbackController {
    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/callback")
    public String getMethodName(@RequestParam String code, HttpSession session) {
        String accessToken = spotifyService.exchangeCodeForToken(code);
        session.setAttribute("access_token", accessToken);

        System.out.println("redirecting to create-playlist!");
        return "redirect:/create-playlist";
    }
}