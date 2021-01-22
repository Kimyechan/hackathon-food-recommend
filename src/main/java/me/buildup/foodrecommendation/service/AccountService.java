package me.buildup.foodrecommendation.service;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.domain.Account;
import me.buildup.foodrecommendation.domain.Role;
import me.buildup.foodrecommendation.advice.exception.LoginFailException;
import me.buildup.foodrecommendation.advice.exception.UserNotFoundException;
import me.buildup.foodrecommendation.repo.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(Long.valueOf(username)).orElseThrow(UserNotFoundException::new);
        return new User(account.getEmail(), account.getPassword(), authorities(account.getRole()));
    }

    private Collection<? extends GrantedAuthority> authorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(LoginFailException::new);
    }
}
