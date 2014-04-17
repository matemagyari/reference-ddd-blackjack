package org.home.blackjack.core.domain;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.cashier.Cashier;
import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.player.core.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.domain.idgeneration.IDGenerator;

/**
 * Domain Service
 * 
 */
@Named
public class RegisterService {

    @Resource
    private PlayerRepository playerRepository;
    @Resource
    private Cashier cashier;
    @Inject
    private IDGenerator<String> idGenerator;

    public PlayerID registerPlayer(PlayerName playerName) {
        PlayerID playerId = PlayerID.createFrom(idGenerator.generate());
        Player player = new Player(playerId, playerName);
        playerRepository.create(player);
        cashier.createAccount(player.getID());
        return player.getID();
    }
}
