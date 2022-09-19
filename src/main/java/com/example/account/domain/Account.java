package com.example.account.domain;

import com.example.account.exception.AccountException;
import com.example.account.type.AccountStatus;
import com.example.account.type.ErrorCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더로 객체생성해주기 위해선 위의 두 constructor 필요
@Entity // 일반적인 테이블이 아닌 Entity, 자바 객체가 아닌 설정이라고 보기
@EntityListeners(AuditingEntityListener.class) // Listener을 사용하기 위해선 application 전체 설정에 넣어주어야함
public class Account {
    @Id
    @GeneratedValue
    private Long id; // ID라는 키를 Account 테이블의 PK로 사용할 것

    @ManyToOne // N:1 구조
    private AccountUser accountUser;
    private String accountNumber;

    @Enumerated(EnumType.STRING) // 이넘은 실제로 0,1,2로 저장되기 때문에 string으로 실제 문자열 저장
    private AccountStatus accountStatus;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void useBalance(Long amount){
        if (amount > balance) {
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }
        balance -= amount;
    }
}
