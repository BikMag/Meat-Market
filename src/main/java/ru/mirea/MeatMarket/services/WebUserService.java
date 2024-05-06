package ru.mirea.MeatMarket.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.MeatMarket.entities.WebUser;
import ru.mirea.MeatMarket.repositories.WebUserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WebUserService implements UserDetailsService {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final WebUserRepo users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching user {}...", username);

        WebUser user = users.getByUsername(username);

        if (user == null) {
            log.info("User {} not found", username);

            throw new UsernameNotFoundException("User not found");
        }

        return new User(user.getUsername(), user.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        ));
    }

    public void saveUser(WebUser webUser) {
        log.info("Saving user {}...", webUser.getUsername());

        WebUser user = new WebUser();

        user.setUsername(webUser.getUsername());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setEmail(webUser.getEmail());

        users.save(user);
    }

    public WebUser getUserByName(String username) {
        log.info("Getting user with username {}...", username);
        return users.getByUsername(username);
    }
}
