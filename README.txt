This is a reference DDD project, implementing a simplified Blackjack game engine.

General user experience description:

It's a simplified 2-player Blackjack game. Users only need a browser. At the beginning they must register, so an account of X chips will be created for each. Then they can join to tables, whenever two players sit at the same table a game begins. 
One player will be the dealer. At the beginning of the game their bets are automatically deducted from the balance, and at the end of the game the winner will be credited. On the page the players can see a leaderboard.
For the rules of the game, check org.home.blackjack.core.domain.game.Game.

General deployment description:

The application has two artifacts (jars). They should be started up by "java -jar xxxx.jar" then the users only need a browser.

General technical description:

The stack is Spring, Camel, Cometd, REST, Hazelcast. The architecture is Hexagonal, the project's aim is to provide numerous examples for different types of Ports and Adapters, as well as DDD patterns and concepts.
It exposes REST and Cometd endpoints to interact with and provides in-memory (a simple map-based and a Hazelcast based) persistence. It has a minimalistic UI in browser.
Components:
* Blackjack Core - implements the game, interacts with Blackjack Wallet
* Blackjack Wallet - managing players' chips
* Blackjack Utils - marker interfaces, abstract classes and interfaces for HA and DDD patterns (aggregate root, value object, ...), locking


Hexagonal Architecture concepts/patterns the project show examples are:
* Layering following HA principles
* Drive/Driving Ports/Adapters - marker interfaces are used to make the building blocks more visible
* Leveraging HA in testing - fake adapters

The DDD concepts/patterns the project show examples are:

Aggregate design:
* Transactional consistency requirements = True Invariants. Enforced inside aggregates: see org.home.blackjack.core.domain.core.game.Game
* Eventual consistency requirements - consistency rules among multiple aggregate instances. Examples:
    1. A player's win number in PlayerRecord aggregate must equal the number of Game instances in which she won 
    2. The sum of wins for all the PlayerRecord instances must equal the number of Game instances
* Entities: e.g. org.home.blackjack.core.domain.game.Deck, org.home.blackjack.core.domain.game.Player
* aggregate roots: Game,Player
* aggregates can only reference to each other by id. See Game -> Player

Value Objects: e.g. Card, PlayerId, GameId (all entity ids are value objects)
Entities: e.g. Player under Game module
* id generation: 

Domain Services:
* "Internal" services - implemented fully in the Domain: Dealer, Cashier
* Representing external dependencies (in front of an ACL): WalletService

Infrastructure Services:
* e.g. the assemblers serializing/deserializing Domain objects, IDGenerationStrategy

Domain events:
* inner domain events - events consumed by the Domain: e.g. TableIsFullEvent
* external domain events - events translated to messages leaving the Bounded Context 


Structure

* Bounded Contexts: 
    ** Blackjack Core - implements the game
    ** BlackJack Wallet - provides functionality to manage the players' chips. Exposes a REST interface.
    
* Subdomains: there is an ideal one-to-one mapping between subdomains and Bounded Contexts.

* Modules - in Blackjack Core
    ** Game: contains the Game aggregate 
    ** Player: contains the Player aggregate
    ** their only common dependency is PlayerId (in shared package)

Package structure (layers of onion from inside out)
* util - general functionality not belonging to any layer in particular
* domain
* app
* infrastructure

Visibility scopes are deliberately restricted to package level wherever possible to encourage loose coupling and encapsulation on package level.




The project extensively uses marker interfaces for Hexagonal Architecture/DDD building blocks/patterns/concepts to make the intentions clearer. 
They are under the *.util.marker package. Other way could have been to use annotations.
