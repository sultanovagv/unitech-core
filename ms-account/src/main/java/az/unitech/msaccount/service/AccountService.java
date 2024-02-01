package az.unitech.msaccount.service;

import az.unitech.msaccount.entity.Account;
import az.unitech.msaccount.exception.AccountNotExistException;
import az.unitech.msaccount.exception.InvalidTransferException;
import az.unitech.msaccount.exception.OverDraftException;
import az.unitech.msaccount.model.TransferRequest;
import az.unitech.msaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public List<Account> getActiveAccounts(Long userId) {
        return accountRepository.findAccountsByUserIdAndActive(userId, Boolean.TRUE);
    }

    @Transactional
    public void transferAmount(TransferRequest transfer) {

        if (transfer.getAccountFromId().equals(transfer.getAccountToId())) {
            throw new InvalidTransferException("Cannot transfer money to the same account.");
        }

        var accountFrom = accountRepository.findByAccountNumber(transfer.getAccountFromId())
                .orElseThrow(() -> new AccountNotExistException("Account with id: " + transfer.getAccountFromId() + " does not exist."));

        var accountTo = accountRepository.findByAccountNumber(transfer.getAccountToId())
                .orElseThrow(() -> new AccountNotExistException("Account with id: " + transfer.getAccountToId() + " does not exist."));

        BigDecimal transferAmount = transfer.getAmount();

        if (accountFrom.getBalance().compareTo(transferAmount) < 0) {
            throw new OverDraftException("Insufficient balance in account " + accountFrom.getAccountNumber());
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(transferAmount));
        accountTo.setBalance(accountTo.getBalance().add(transferAmount));
    }

}
