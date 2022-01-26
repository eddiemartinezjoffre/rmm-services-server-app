package com.ninja.rmm.model;

import javax.persistence.Column;
import java.math.BigDecimal;

public class Cost {
    @Column(name = "total")
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
