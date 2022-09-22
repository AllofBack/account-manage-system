package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.dto.AccountDto;
import com.example.account.exception.AccountException;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AccountUserRepository;
import com.example.account.type.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.account.type.AccountStatus.IN_USE;
import static com.example.account.type.ErrorCode.*;
import static com.example.account.type.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor // 생성자
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    /**
     * 사용자가 있는지 조회
     * 계좌 번호 생성하고
     * 계좌를 저장하고, 그 정보를 넘긴다.
     */
    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance){
        AccountUser accountUser = getAccountUser(userId);

        validateCreateAccount(accountUser); // 되도록 메서드 추출해서 깔끔하게 정리하기

        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc() // 가장 최근에 생성된 Account 가져오기
                .map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "") // 1 더하고 문자열로 변환
                .orElse("1000000000"); // 없을 경우 기본 첫 계좌번호

        return AccountDto.fromEntity(
                accountRepository.save(Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(IN_USE)
                        .accountNumber(newAccountNumber)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build()
        ));
    }

    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(MAX_ACCOUNT_PER_USER_10);
        }
    }

    @Transactional
    public Account getAccount(Long id){
        if (id < 0) {
            throw new RuntimeException("Minus");
        }
        return accountRepository.findById(id).get();
    }

    @Transactional
    public AccountDto deleteAccount(Long userId, String accountNumber) {
        AccountUser accountUser = getAccountUser(userId);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

        validateDeleteAccount(accountUser, account); // 되도록 메서드 추출해서 깔끔하게 정리하기

        account.setAccountStatus(AccountStatus.UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());

        accountRepository.save(account);

        return AccountDto.fromEntity(account);
    }

    private void validateDeleteAccount(AccountUser accountUser, Account account) {
        if (!Objects.equals(accountUser.getId(), account.getAccountUser().getId())){ // 계좌 소유주가 다름
            throw new AccountException(USER_ACCOUNT_UN_MATCH);
        }
        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) { // 계좌가 이미 해지된 상태
            throw new AccountException(ACCOUNT_ALREADY_UNREGISTERED);
        }
        if (account.getBalance() > 0) {
            throw new AccountException((BALANCE_NOT_EMPTY));
        }
    }
    @Transactional
    public List<AccountDto> getAccountByUserId(Long userId) {
        AccountUser accountUser = getAccountUser(userId);
        List<Account> accounts = accountRepository
                .findByAccountUser(accountUser);

        // account -> AccountDto로 받기
        return accounts.stream()
                // .map(account -> AccountDto.fromEntity(account)) 이렇게도 가능
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    private AccountUser getAccountUser(Long userId) {
        return accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
    }
}
