package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;
import com.example.account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount( // response로 리턴 받기
                                                 @RequestBody @Valid DeleteAccount.Request request // @Valid : requestBody로 들어온 객체 검증
    ) {
        // request를 통해 바디로 들어온 객체 정보를 통해 accountService.createAccount 실행
        return DeleteAccount.Response.from(accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                )
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId
    ){
        return accountService.getAccountByUserId(userId)
                .stream().map(accountDto -> AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());
    }
}
