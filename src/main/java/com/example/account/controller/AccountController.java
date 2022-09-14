package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.CreateAccount;
import com.example.account.service.AccountService;
import com.example.account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
// 외부에서는 컨트롤러로 접속
// 컨트롤러는 서비스로 접속
// 서비스는 레파지토리로 접속하는 순차적인 과정 설정
public class AccountController {
    private final AccountService accountService;
    // private final RedisTestService redisTestService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount( // response로 리턴 받기
            @RequestBody @Valid CreateAccount.Request request // @Valid : requestBody로 들어온 객체 검증
    ) {
        // request를 통해 바디로 들어온 객체 정보를 통해 accountService.createAccount 실행
        return CreateAccount.Response.from(accountService.createAccount(
                request.getUserId(),
                request.getInitialBalance()
            )
        );
    }

}
