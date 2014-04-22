package org.home.blackjack.core.domain.table;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.exception.DomainException;

@SuppressWarnings("serial")
public class TableNotFoundException extends DomainException {

    public TableNotFoundException(TableID tableID) {
        super(tableID + " not found");
    }

}
