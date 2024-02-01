package az.unitech.msaccount.controller;

import az.unitech.msaccount.entity.Account;
import az.unitech.msaccount.model.TransferRequest;
import az.unitech.msaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{userId}")
    public List<Account> getAccounts(@PathVariable Long userId) {
        return accountService.getActiveAccounts(userId);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest request) {
        accountService.transferAmount(request);
        return ResponseEntity.ok().build();
    }

}
