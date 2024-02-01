package az.unitech.msaccount.model;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull
    private String accountFromId;

    @NotNull
    private String accountToId;

    @NotNull
    private BigDecimal amount;

}
