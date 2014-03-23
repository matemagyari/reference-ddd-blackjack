This is a reference DDD project, implementing a simplified Blackjack game engine.

General user experience description:

It's a simplified 2-player Blackjack game. Users only need a browser. At the beginning they must register, so an account of X chips will be created for each. Then they can join to tables, whenever two players sit at the same table a game begins. 
One player will be the dealer. At the beginning of the game their bets are automatically deducted from their balance, and at the end of the game the winner will be credited. On the page the players can see a leader board.
For the rules of the game, check org.home.blackjack.core.domain.game.Game.

General deployment description:

The application has two artifacts (jars). They should be started up by "java -jar xxxx.jar", then the users only need a browser.

General technical description:

The stack is Spring, Camel, Cometd, REST, Hazelcast. The architecture is Hexagonal, the project's aim is to provide numerous examples for different types of Ports and Adapters, as well as DDD patterns and concepts.
It exposes REST and Cometd endpoints to interact with and provides in-memory (a simple map-based and a Hazelcast based) persistence. It has a minimalistic UI in browser.
Components:
* Blackjack Core - implements the game, interacts with Blackjack Wallet
* Blackjack Wallet - managing players' chips
* Blackjack Utils - marker interfaces, abstract classes and interfaces for HA and DDD patterns (aggregate root, value object, ...), locking


Hexagonal Architecture concepts/patterns the project show examples are:
* Onion Layering following HA principles
* Drive/Driving Ports/Adapters - marker interfaces are used to make the building blocks more visible
* Leveraging HA in testing - fake adapters

The DDD concepts/patterns the project show examples are:

Aggregate design:
* Transactional consistency requirements = True Invariants. Enforced inside aggregates: see org.home.blackjack.core.domain.core.game.Game
  Trivial example: player's cards and the remaining deck should always add up 52 different cards, with no overlaps 
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
* external domain events - events translated to messages to be sent out of the Bounded Context 
* events are dispatched by aggregate roots, sometimes by domain services. Sometimes a domain is translated to multiple messages. 
E.g. PlayerCardsDealtEvent contains the card the player's been dealt.  The fact that the deal has happened needs to get propagated to all parties around the table, 
but the card will be shown only the particular player. So the PlayerCardsDealtEvent is split into two events in the app layer (in an event handler)

Application layer responsibilites:
* provide a facade to the client to interact with the app


Structure

* Bounded Contexts - separate deployable units: 
    ** Blackjack Core - implements the game
    ** BlackJack Wallet - provides functionality to manage the players' chips. Exposes a REST interface.
    
* Subdomains: there is an ideal one-to-one mapping between subdomains and Bounded Contexts.

* Modules - in Blackjack Core
    ** Game: contains the Game aggregate 
    ** Player: contains the Player aggregate
    ** their only common dependency is PlayerId (in shared package)

Package structure (layers of onion from inside out)
* util - general functionality not belonging to any layer in particular
* domain - contains the logic for the given subdomain (Core or Wallet). Composed of Modules (a larger package with its subpackages). Aggregates reside in their own Module.
* app - application layer. main subpackages:
    ** service - package for all application services. These are the facades exposed to the client. ALL calls from the client goes through this package.
    ** eventhandlers - package for eventhandlers. See Domain Events for further details.
* infrastructure - predominantly ACL implementations

Visibility scopes are deliberately restricted to package level wherever possible to encourage loose coupling and encapsulation on package level.

Command&Query separation
* the clients can send either commands, or queries. The commands change the state of the application and usually events are sent out. The queries never change the state
and the answers are sent out asynchronously, like events

Acceptance Testing
* the acceptance tests are implemented using Cucumber. All tests could be run on different levels. One test client simulates the user calling directly the application 
services, the other sends commands/queries via CometD. The first verifies notifications by attaching fake adapters to the app's Driving Ports (FakeExternalEventPublisher),
the second actually subscribes to CometD channels. See TestAgent, AppLevelTestAgent and MessagingTestAgent for details.

Stuff missing:

* the Wallet component is very lean, no error handling, no complete anti-corruption layers. It's purpose is to demonstrate how can two remote Bounded Contexts interact
* CometD - very simplistic implementation, no error handling, no security. Currently all clients could subscribe to each others private channels

The project extensively uses marker interfaces for Hexagonal Architecture/DDD building blocks/patterns/concepts to make the intentions clearer. 
They are under the *.util.marker package. Other way could have been to use annotations.
