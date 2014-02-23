This is a reference DDD project, implementing a simplified Blackjack game engine.
The architecture is Hexagonal.

It exposes http and websocket endpoints to interact with and provides in-memory (a simple map-based and a Hazelcast based) persistence.
The http endpoints are implemented with REST and also with servlets.

It will have a simple UI in browser. The players can join the game, will be randomly matched to other players. There will also be a leaderboard showing the win records. 

For the rules of the game, check org.home.blackjack.domain.game.Game.

The DDD concepts/patterns the project show examples are:

Aggregate design:
* Transactional consistency requirements = True Invariants. Enforced inside aggregates: see org.home.blackjack.domain.game.Game
* Eventual consistency requirements - consistency rules among multiple aggregate instances
    1. A player's win number in PlayerRecord aggregate must equal the number of Game instances in which she won 
    2. The sum of wins for all the PlayerRecord instances must equal the number of Game instances
* Entities: e.g. org.home.blackjack.domain.game.Deck, org.home.blackjack.domain.game.Player
* aggregate roots: Game,Player
* aggregates can only reference to each other by id. See Game -> Player

Value Objects: e.g. Card, PlayerId, GameId (all entity ids are value objects)
Entities: e.g. Player under Game module
* id generation: 
Domain Services:
* "Internal" services: GameFactory, DeckFactory
* Representing external dependencies (in front of an ACL):

Infrastructure Services:
* e.g. the assemblers serializing/deserializing Domain objects, IDGenerationStrategy

Domain events:


Structure - should go to Parent
* Bounded Contexts: the Blackjack Core is the local Bounded Context, communicating with the BlackJack Tournament remote Bounded Context
* Subdomains: there is an ideal one-to-one mapping between subdomains and Bounded Contexts. the Blackjack Core BC implements

* Modules
    ** Game: contains the Game aggregate 
    ** Player: contains the Player aggregate
    ** their only common dependency is PlayerId (in shared package)

Package structure (layers of onion from inside out)
util - general functionality not belonging to any layer in particular
domain
app
infrastructure

Visibility scopes are deliberately restricted to package level, wherever possible to encourage loose coupling and encapsulation on package level.




The project extensively uses marker interfaces for Hexagonal Architecture/DDD building blocks/patterns/concepts to make the intentions clearer. 
They are under the *.util.marker package. Other way could have been to use annotations.
