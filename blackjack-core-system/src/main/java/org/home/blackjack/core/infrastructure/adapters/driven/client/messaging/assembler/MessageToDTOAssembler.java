package org.home.blackjack.core.infrastructure.adapters.driven.client.messaging.assembler;

import javax.inject.Named;

import org.home.blackjack.core.app.service.game.GameCommand;
import org.home.blackjack.core.app.service.query.TablesQuery;
import org.home.blackjack.core.app.service.registration.RegistrationCommand;
import org.home.blackjack.core.app.service.seating.SeatingCommand;
import org.home.blackjack.messaging.command.GameCommandMessage;
import org.home.blackjack.messaging.command.RegistrationCommandMessage;
import org.home.blackjack.messaging.command.SeatingCommandMessage;
import org.home.blackjack.messaging.query.TablesQueryMessage;

/**
 * Transforms the messages coming from the client to Commands/Queries
 */
@Named
public class MessageToDTOAssembler {
    
    public SeatingCommand assemble(SeatingCommandMessage message) {
        return new SeatingCommand(message.playerId, message.tableId);
    }
    
    public GameCommand assemble(GameCommandMessage message) {
        return new GameCommand(message.playerId, message.gameId, message.action);
    }
    
    public RegistrationCommand assemble(RegistrationCommandMessage message) {
        return new RegistrationCommand(message.name);
    }
    
    public TablesQuery assemble(TablesQueryMessage message) {
        return new TablesQuery(message.playerId);
    }
}
