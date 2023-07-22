package jussinet.boobank.release.repository.queryinterfaces;

import java.util.*;

public interface TransactionData {
    public UUID getId();
    public Float getAmount();
    public Date getDate();
    public String getMessage();
    public Float getCumulativeSum();
}
