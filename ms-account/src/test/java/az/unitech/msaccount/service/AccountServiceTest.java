package az.unitech.msaccount.service;

import az.unitech.msaccount.entity.Account;
import az.unitech.msaccount.exception.AccountNotExistException;
import az.unitech.msaccount.exception.InvalidTransferException;
import az.unitech.msaccount.exception.OverDraftException;
import az.unitech.msaccount.model.TransferRequest;
import az.unitech.msaccount.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private static final String ACCOUNT_FROM = "4169456523457632";
    private static final String ACCOUNT_TO = "4169456523457645";
    private static final BigDecimal AMOUNT1 = BigDecimal.valueOf(100);
    private static final BigDecimal AMOUNT2 = BigDecimal.valueOf(50);

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getActiveAccounts() {
        Long userId = 1L;
        when(accountRepository.findAccountsByUserIdAndActive(userId, Boolean.TRUE))
                .thenReturn(List.of(new Account()));

        List<Account> activeAccounts = accountService.getActiveAccounts(userId);

        assertNotNull(activeAccounts);
        assertFalse(activeAccounts.isEmpty());
    }

    @Test
    void transferAmount_ShouldBeSuccessful() {
        var request = new TransferRequest();
        request.setAccountFromId(ACCOUNT_FROM);
        request.setAccountToId(ACCOUNT_TO);
        request.setAmount(AMOUNT1);

        var accountFrom = new Account();
        accountFrom.setAccountNumber(ACCOUNT_FROM);
        accountFrom.setActive(true);
        accountFrom.setBalance(AMOUNT1);

        when(accountRepository.findByAccountNumber(request.getAccountFromId()))
                .thenReturn(Optional.of(accountFrom));

        var accountTo = new Account();
        accountTo.setAccountNumber(ACCOUNT_TO);
        accountTo.setActive(true);
        accountTo.setBalance(BigDecimal.valueOf(500));

        when(accountRepository.findByAccountNumber(request.getAccountToId()))
                .thenReturn(Optional.of(accountTo));

        accountService.transferAmount(request);
    }

    @Test
    void transferAmount_InvalidTransfer() {
        var request = new TransferRequest();
        request.setAccountFromId(ACCOUNT_FROM);
        request.setAccountToId(ACCOUNT_FROM);
        request.setAmount(AMOUNT2);

        assertThrows(InvalidTransferException.class, () -> accountService.transferAmount(request));
    }

    @Test
    void transferAmount_AccountNotExist() {
        var request = new TransferRequest();
        request.setAccountFromId(ACCOUNT_FROM);
        request.setAccountToId(ACCOUNT_TO);
        request.setAmount(AMOUNT2);

        when(accountRepository.findByAccountNumber(any()))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotExistException.class, () -> accountService.transferAmount(request));
    }

    @Test
    void transferAmount_InsufficientBalance() {
        var request = new TransferRequest();
        request.setAccountFromId(ACCOUNT_FROM);
        request.setAccountToId(ACCOUNT_TO);
        request.setAmount(BigDecimal.valueOf(150));

        var accountFrom = new Account();
        accountFrom.setAccountNumber(ACCOUNT_FROM);
        accountFrom.setBalance(AMOUNT1);

        var accountTo = new Account();
        accountTo.setAccountNumber(ACCOUNT_TO);
        accountTo.setBalance(AMOUNT1);

        when(accountRepository.findByAccountNumber(request.getAccountFromId()))
                .thenReturn(Optional.of(accountFrom));
        when(accountRepository.findByAccountNumber(request.getAccountToId()))
                .thenReturn(Optional.of(accountTo));

        assertThrows(OverDraftException.class, () -> accountService.transferAmount(request));
    }

}
