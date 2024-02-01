package az.unitech.msaccount.repository;

import az.unitech.msaccount.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Optional<Account> findByAccountNumber(String accountId);

    List<Account> findAccountsByUserIdAndActive(Long userId, boolean active);

}
