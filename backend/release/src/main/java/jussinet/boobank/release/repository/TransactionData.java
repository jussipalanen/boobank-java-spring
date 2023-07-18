package jussinet.boobank.release.repository;

import java.util.*;

public interface TransactionData {
    public UUID getId();
    public Float getAmount();
    public Date getDate();
}
