package me.buildup.foodrecommendation.controller;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.advice.exception.LoginFailException;
import me.buildup.foodrecommendation.config.JwtTokenProvider;
import me.buildup.foodrecommendation.domain.Account;
import me.buildup.foodrecommendation.domain.Role;
import me.buildup.foodrecommendation.dto.account.JoinReq;
import me.buildup.foodrecommendation.dto.account.JoinRes;
import me.buildup.foodrecommendation.dto.account.LoginRes;
import me.buildup.foodrecommendation.dto.account.LoginReq;
import me.buildup.foodrecommendation.service.AccountService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    @PostMapping("/join")
    public ResponseEntity<JoinRes> join(@RequestBody JoinReq joinReq) {
        Account account = Account.builder()
                .email(joinReq.getEmail())
                .password(passwordEncoder.encode(joinReq.getPassword()))
                .userName(joinReq.getUserName())
                .role(Role.USER)
                .build();

        Account savedAccount = accountService.saveAccount(account);
        JoinRes joinRes = JoinRes.builder()
                .userName(savedAccount.getUserName())
                .build();

        WebMvcLinkBuilder selfLinkBuilder = linkTo(methodOn(AccountController.class).join(joinReq));
        URI createUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createUri).body(joinRes);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) {
        Account account = accountService.findAccountByEmail(loginReq.getEmail());
        if (!passwordEncoder.matches(loginReq.getPassword(), account.getPassword())) {
            throw new LoginFailException();
        }

        String accessToken = jwtProvider.createToken(String.valueOf(account.getId()), account.getRole());
        LoginRes loginRes = LoginRes.builder()
                .accessToken(accessToken)
                .build();

        return ResponseEntity.ok().body(loginRes);
    }

//    @GetMapping("/test")
//    public String test() {
//        return "ok";
//    }
}
