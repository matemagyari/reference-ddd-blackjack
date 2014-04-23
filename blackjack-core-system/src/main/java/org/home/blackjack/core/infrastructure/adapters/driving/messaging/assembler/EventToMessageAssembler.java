package org.home.blackjack.core.infrastructure.adapters.driving.messaging.assembler;

import java.util.List;

import javax.inject.Named;

import org.home.blackjack.core.app.events.eventhandler.PublicPlayerCardDealtEvent;
import org.home.blackjack.core.app.service.query.TableListViewDTO;
import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.game.event.GameStartedEvent;
import org.home.blackjack.core.domain.game.event.InitialCardsDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerStandsEvent;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.table.event.PlayerIsSeatedEvent;
import org.home.blackjack.core.domain.table.event.TableSeatingChangedEvent;
import org.home.blackjack.core.infrastructure.adapters.driving.integration.camel.EventRouteBuilder;
import org.home.blackjack.messaging.event.CardDTOMessage;
import org.home.blackjack.messaging.event.GameFinishedEventMessage;
import org.home.blackjack.messaging.event.GameStartedEventMessage;
import org.home.blackjack.messaging.event.InitialCardsDealtEventMessage;
import org.home.blackjack.messaging.event.LeaderBoardChangedEventMessage;
import org.home.blackjack.messaging.event.LeaderBoardChangedEventMessage.LeaderBoardRecordMessage;
import org.home.blackjack.messaging.event.PlayerCardDealtEventMessage;
import org.home.blackjack.messaging.event.PlayerIsSeatedEventMessage;
import org.home.blackjack.messaging.event.PlayerStandsEventMessage;
import org.home.blackjack.messaging.event.PublicPlayerCardDealtEventMessage;
import org.home.blackjack.messaging.event.TableSeatingChangedEventMessage;
import org.home.blackjack.messaging.response.TablesResponseMessage;

import com.google.common.collect.Lists;

/**
 * Part of the ACL between application and the client, responsible for
 * transforming domain events to DTOs. Since the methods are mostly one-liners,
 * it made sense putting them in one class. We also leverage Camel's route
 * builder's ability to call the appropriate method on this object based on the
 * type of the argument. See @{@link EventRouteBuilder}.
 * 
 */
@Named
public class EventToMessageAssembler {

    public GameFinishedEventMessage assemble(GameFinishedEvent event) {
        return new GameFinishedEventMessage(event.getGameID().toString(), event.getTableID().toString(), event.getActingPlayer().toString(), event.getSequenceNumber());
    }

    public GameStartedEventMessage assemble(GameStartedEvent event) {
        return new GameStartedEventMessage(event.getGameID().toString(), event.getTableID().toString(), event.getActingPlayer().toString(), event.getSequenceNumber());
    }

    public InitialCardsDealtEventMessage assemble(InitialCardsDealtEvent event) {
        return new InitialCardsDealtEventMessage(event.getGameID().toString(), event.getTableID().toString(), event.getSequenceNumber());
    }

    public PlayerCardDealtEventMessage assemble(PlayerCardDealtEvent event) {
        Card card = event.getCard();
        CardDTOMessage cardDTO = new CardDTOMessage(card.rank.name(), card.suite.name());
        return new PlayerCardDealtEventMessage(event.getGameID().toString(), event.getTableID().toString(), event.getActingPlayer().toString(), cardDTO, event.getSequenceNumber());
    }

    public PublicPlayerCardDealtEventMessage assemble(PublicPlayerCardDealtEvent event) {
        return new PublicPlayerCardDealtEventMessage(event.getGameID().toString(), event.getTableID().toString(), event.getActingPlayer().toString(), event.getSequenceNumber());
    }

    public PlayerStandsEventMessage assemble(PlayerStandsEvent event) {
        return new PlayerStandsEventMessage(event.getGameID().toString(), event.getTableID().toString(), event.getActingPlayer().toString(), event.getSequenceNumber());
    }

    public PlayerIsSeatedEventMessage assemble(PlayerIsSeatedEvent event) {
        return new PlayerIsSeatedEventMessage(event.tableId().toString(), event.getPlayer().toString());
    }

    public TableSeatingChangedEventMessage assemble(TableSeatingChangedEvent event) {
        List<String> players = Lists.newArrayList();
        for (PlayerID player : event.getPlayers()) {
            players.add(player.toString());
        }
        return new TableSeatingChangedEventMessage(event.tableId().toString(), players);
    }

    public LeaderBoardChangedEventMessage assemble(LeaderBoardChangedEvent event) {
        List<LeaderBoardRecordMessage> records = Lists.newArrayList();
        for (LeaderBoardChangedEvent.LeaderBoardRecord record : event.getRecords()) {
            records.add(new LeaderBoardRecordMessage(record.playerName.getText(), record.winNumber));
        }
        return new LeaderBoardChangedEventMessage(records);
    }

    public TablesResponseMessage assemble(TableListViewDTO tableListViewDTO) {
        return new TablesResponseMessage(tableListViewDTO.getTablesWithPlayers());
    }
}
