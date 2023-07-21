package jussinet.boobank.release.repository;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "amount", "date" })
public interface TransactionApiData {
    public UUID getId();
    public Float getAmount();
    public Date getDate();
}
