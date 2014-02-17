package org.home.blackjack.infrastructure.game.persistence;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameId;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

public class GameRepositoryImpl implements GameRepository, DrivingAdapter<GameRepository> {
    
    private GameStore gameStore;

    public Game find(GameId gameId) {
        //TODO
        return null;
    }

    public void update(Game game) {
      //TODO
    }

}
