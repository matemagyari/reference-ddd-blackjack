This is a reference DDD project, implementing a simplified Blackjack game engine.
The architecture is Hexagonal.

It exposes http and websocket endpoints to interact with and provides in-memory (a simple map-based and a Hazelcast based) persistence.
The http endpoints are implemented with REST and also with servlets.

It will have a simple UI in browser. The players can join the game, will be randomly matched to other players. There will also be a leaderboard showing the win records. 

For the rules of the game, check org.home.blackjack.domain.game.GameImpl.

The DDD concepts/patterns the project show examples are:

Aggregate design:
- Transactional consistency requirements = True Invariants. Enforced inside aggregates: see org.home.blackjack.domain.game.GameImpl
- Eventual consistency requirements - consistency rules amoung multiple aggregate instances
    1. A player's win number in PlayerRecord aggregate must equal the number of Game instances in which she won 
    2. The sum of wins for all the PlayerRecord instances must equal the number of Game instances
- Entities: e.g. Deck, PlayerHand
- aggregates reference each other by id. E.g. Game -> PlayerRecord

Package structure
util - general functionality not belonging to any layer in particular

Visibility scopes are deliberately restricted to package level, wherever possible to encourage loose coupling and encapsulation on package level.


There are two Subdomains/internal Bounded Contexts, Game and Player. They are integrated by Shared Kernel. 

The project extensively uses marker interfaces for Hexagonal Architecture/DDD building blocks/patterns/concepts to make the intentions clearer. 
They are under the *.util.marker package. Other way could have been to use annotations.
