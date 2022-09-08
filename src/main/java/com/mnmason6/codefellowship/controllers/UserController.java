package com.mnmason6.codefellowship.controllers;

import com.mnmason6.codefellowship.models.Post;
import com.mnmason6.codefellowship.models.SiteUser;
import com.mnmason6.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class UserController {
    @Autowired
    SiteUserRepository siteUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String getHomePage(Principal p, Model m){
        if (p != null){
            String username = p.getName();
            SiteUser siteUser = siteUserRepository.findByUsername(username);

            m.addAttribute("siteUser", siteUser);
        }
        return "index.html";
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id){
        if (p != null){
            String username = p.getName();
            SiteUser siteUser = siteUserRepository.findByUsername(username);

            m.addAttribute("username", username);
        }
        SiteUser dbUser = siteUserRepository.findById(id).orElseThrow();
        m.addAttribute("dbUserUsername", dbUser.getUsername());
        m.addAttribute("dbUserId", dbUser.getId());

        return "my-profile";
    }
    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m){
        if(p != null){
            String username = p.getName();
            SiteUser siteUser = siteUserRepository.findByUsername(username);
            m.addAttribute("siteUser", siteUser);
        }
        return "login";
    }
    @GetMapping("/signup")
    public String getSignupPage(Principal p, Model m) {
        if(p != null){
            String username = p.getName();
            SiteUser siteUser = siteUserRepository.findByUsername(username);
            m.addAttribute("siteUser", siteUser);
        }
        return "signup";
    }
    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m){
        if(p != null){
            String username = p.getName();
            SiteUser siteUser = siteUserRepository.findByUsername(username);
            m.addAttribute("siteUser", siteUser);
        }
        return "my-profile";
    }

    @PostMapping("/myprofile")
    public RedirectView addPost(Principal p, String body){
        String username = p.getName();
        SiteUser siteUser = siteUserRepository.findByUsername(username);
        Post newPost = new Post(body, LocalDateTime.now(), siteUser);
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password,
                                   String firstName, String lastName,
                                   int dateOfBirth, String bio) throws ServletException {
        String hashedPassword = passwordEncoder.encode(password);
        SiteUser newUser = new SiteUser(username, hashedPassword, firstName,
                lastName,
                dateOfBirth,bio);
        siteUserRepository.save(newUser);
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/");
    }
    @PostMapping("/users/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id,
                                     String username, String firstName,
                                     String lastName, int dateOfBirth, String bio,
                                     RedirectAttributes redir){
        if(p != null && p.getName().equals(username)){
            SiteUser newUser = siteUserRepository.findById(id).orElseThrow();
            newUser.setUsername(username);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setDateOfBirth(dateOfBirth);
            newUser.setBio(bio);
            siteUserRepository.save(newUser);
        } else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's " +
                    "info");
        }
        return new RedirectView("/users/" + id);
    }
}
