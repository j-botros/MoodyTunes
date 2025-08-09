package com.moodytunes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodytunes.spotify.SpotifyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CallbackController {
    @GetMapping("/callback")
    public String getMethodName(@RequestParam String code, HttpSession session) {
        String accessToken = SpotifyService.exchangeCodeForToken(code);
        session.setAttribute("access_token", accessToken);

        return "redirect:/create-playlist";
    }
}