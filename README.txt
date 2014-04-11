This is a reference DDD project, implementing a simplified Blackjack game engine.

General user experience description:

It's a simplified 2-player Blackjack game. Users only need a browser. At the beginning they must register, so an account
of X chips will be created for each. Then they can join to tables and whenever two players sit at the same table a game 
begins. One player will be the dealer. At the beginning of the game their bets are automatically deducted from their 
balance, and at the end of the game the winner will be credited. After a game the players are removed from the table. 
On the page the players can see a leader board. For  the rules of the game, check 
org.home.blackjack.core.domain.game.Game.

General deployment description:

The application has two artifacts (jars), Blackjack Core and Blackjack Wallet. 
Start up 
	* Blackjack Core: mvn exec:java
	* Blackjack Wallet: mvn jetty:run 
    	* the users only need a browser and open file:///.../blackjack/blackjack-core-system/src/main/webapp/index.html

General technical description:

The stack is Spring, Camel, Cometd, REST, Hazelcast. The architecture is Hexagonal and Event-Driven, the project's aim
is to provide numerous examples for different types of Ports and Adapters, as well as DDD patterns and concepts. It 
exposes REST and Cometd endpoints to interact with and provides in-memory (a simple map-based and a Hazelcast based) 
persistence. It has a minimalistic UI in browser.
Components:
* Blackjack Messaging Client - library defining the messages (commands&queries) the client can send to Core, and the
                               event messages Core sends to Client
* Blackjack Core - implements the game, interacts with Blackjack Wallet
* Blackjack Wallet - managing players' chips
* Blackjack Utils - marker interfaces, abstract classes and interfaces for HA and DDD patterns 
                    (aggregate root, value object, ...), locking, event handling mechanism
* Blackjack UI - UI code, currently in Blackjack Core's src/main/webapp. Uses REST and CometD via javascript to 
                 interact with Core and Wallet               


Hexagonal Architecture concepts/patterns the project show examples are:
* Onion Layering following HA principles
* Drive/Driving Ports/Adapters - marker interfaces are used to make the building blocks more visible
* Leveraging HA in testing - fake adapters

The DDD concepts/patterns the project show examples are:

Aggregate design:
* Transactional consistency requirements = True Invariants. Enforced inside aggregates: see 
  org.home.blackjack.core.domain.core.game.Game
  Trivial example: player's cards and the remaining deck should always add up 52 different cards, with no overlaps 
* Eventual consistency requirements - consistency rules among multiple aggregate instances. Examples:
    1. A player's win number in PlayerRecord aggregate must equal the number of Game instances in which she won 
    2. The sum of wins for all the PlayerRecord instances must equal the number of Game instances
    3. Entry fee is deducted from the player's balance when the game starts. Likewise, his win is paid out after the
       game.
* aggregate roots: e.g. Game, Player
* aggregates can only reference to each other by id. See Game -> Player

Value Objects: e.g. Card, PlayerId, GameId (all entity ids are value objects)
Entities: e.g. org.home.blackjack.core.domain.game.Deck, org.home.blackjack.core.domain.game.Player. Their ids are only 
unique inside the aggregate.

Id generation: currently using UUIDs. Some applications might require using the DB generating the ids. 

Domain Services:
* "Internal" services - implemented fully in the Domain: Dealer, Cashier, LeaderboardUpdater
* Representing external dependencies (in front of an ACL): WalletService

Infrastructure Services:
* e.g. the assemblers serializing/deserializing Domain objects, IDGenerationStrategy

ACLs:
* ACLs are implemented by Adapters. Adapters contain the technology-specific logic (e.g CometD) and the assemblers that 
  translate data between the layers. Driven Adapters are implemented by Camel routes and JAX-RS, Driving Adapters by 
  various technologies (Mongo, Hazelcast, ...).
* 3 ACLs in Blackjack Core
  ** Between Client->Application: all the commands/queries coming from clients pass through this ACL. Json messages are
     transformed to Message objects defined in Blackjack Messaging Client library, then the Message objects are 
     transformed to Command/Query DTOs the Application Services consume 
  ** Between Application->Client: all the domain events that are meant for the client are transformed to Messages 
     (defined in Blackjack Messaging Client library), then to JSON
  ** Between Domain<->persistence: the domain objects are serialized to json before saved in the DB. Gson provides tools
     to define custom (de)serializers so different versions of data can be managed. See an example in 
     GameGsonProvider.java

Domain events:
* inner domain events - events consumed by the Domain: e.g. TableIsFullEvent
* external domain events - events translated to messages to be sent out of the Bounded Context 
* events are dispatched by aggregate roots, sometimes by domain services. 
* Sometimes a domain event is translated to multiple messages to send out.  E.g. PlayerCardsDealtEvent contains the card
  the player's been dealt. The fact that the deal has happened needs to get propagated to all parties around the table, 
  but the card will be shown only the particular player. So the PlayerCardsDealtEvent is split into two events in the 
  app layer (in an event handler)
* the Domain Events are put on a lightweight event bus. The event bus instance belongs to the thread and it buffers the 
  events. After every "transaction" (we call transaction when the state of an aggregate had changed and it's been 
  persisted) the event bus is "flushed" (done by Camel, check the route builder). Without buffering if an exception 
  occurs after an event is dispatched and the aggregate doesn't get persisted, there would be no way to "call back" the
  events. Sothey only get released from the buffer once the "transaction is closed"

Application layer responsibilities:
* providing a facade to the client to interact with the app (public methods of an app service are Driven Ports)
  = implement Use Cases. Each application service implements one Use Case, or a group of multiple smaller, related ones
* event handling - see domain events
* bootstrapping
* routing Domain Events either back to the Domain or out of the application. Sometimes a Domain Event must be translated
  to multiple messages. The application service splits the event into multiple events


Structure

* Bounded Contexts - separate deployable units: 
    ** Blackjack Core - implements the game
    ** BlackJack Wallet - provides functionality to manage the players' chips. Exposes a REST interface.
    
* Subdomains: there is an ideal one-to-one mapping between subdomains and Bounded Contexts.

* Modules - in Blackjack Core. Modules are organized around aggregates
    ** Game: contains the Game aggregate 
    ** Player: contains the Player aggregate
    ** Table: contains the Table aggregate
    ** their common dependencies (PlayerId, TableId) are in the shared package

Package structure (layers of onion from inside out)
* util - general functionality not belonging to any layer in particular
* domain - contains the logic for the given subdomain (Core or Wallet). Composed of Modules (a larger package with its subpackages). Aggregates reside in their own Module.
* app - application layer. main subpackages:
    ** service - package for all application services. These are the facades exposed to the client. ALL calls from the client goes through this package.
    ** eventhandlers - package for eventhandlers. See Domain Events for further details.
* infrastructure - predominantly ACL implementations

Visibility scopes are deliberately restricted to package level wherever possible to encourage loose coupling and encapsulation on package level.


Client

The client integrated with the server via CometD and HTTP. 
The general client flow
1. call an http://0.0.0.0:9090/blackjack/register/{playerName}.The {playerName} is an arbitrary string. The response will be the generated player id.
2. subscribe to /leaderboard channel
2. subscribe to /player/{playerId}/query/response
3. publish a request to /query/request. The body of the request is the player id.
4. the response will come on /player/{playerId}/query/response channel, containing the tables
5. subscribe to /player/{playerId}/table/{tableId}
6. to sit down publish a message to /command/table/sit channel. The body is {}
7. events about the game arrive on /player/{playerId}/table/{tableId} channel
 

Architectural notes

Hexagonal (Ports and Adapters) Architecture
The interaction points with the application are organized around Ports and Adapters. 
See http://alistair.cockburn.us/Hexagonal+architecture

Event-Driven Architecture
Domain events are published by Aggregate Roots, sometimes by Domain Services. The events are consumed by event handlers
(classes extending org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber), which send them out or interact
with the domain, lending a declarative-like programmatic style to the app. For example the rule
'When a table is full Then start a new game' is implemented by:
1. the Table aggregate root dispatches a TableIsFullEvent when it's full
2. TableIsFullEventEventHandler will consume the event and create a new Game instance tied to the table
Each event handler is invoked on a separate thread.

Event-publishing mechanism

The published events get buffered on the internal event bus and flushed at the end of the "transaction". The reason is 
that if an event is dispatched then an exception happens down the line we can't rollback, since we cannot "call back"
the event. Imagine the situation when we do not buffer, but send out the event to the client immediately, then when we 
try to save the updated aggregate to the DB, it fails (network/versioning/contention/... problem). Now the client is
in an inconsistent state, since she was told a state-update has happened, but it has not.
Therefore we buffer the events and flush the event bus only after everything is done. It is some very simple form of
distributed transaction handling. This requires some "ThreadLocal magic" in LightweightEventBus, so concurrent threads 
don't flush or clear each others' buffers. Whenever a requests arrives we reset the bus instance of the thread (so if
the thread was being used before and for some reason the bus hasn't got flushed, we get rid of those pending events), 
then after the update finished we flush it.

Command&Query separation
* the clients can send either commands, or queries. The commands change the state of the application and usually events 
  are sent out as a consequence. The queries never change the state and the answers are sent out asynchronously, 
  like events. The Application Services consume Commands or Queries 
  (instances of org.home.blackjack.core.app.dto.Command/Query)

Acceptance Testing
* the acceptance tests are implemented using Cucumber. All tests could be run on 2 different levels. One test client simulates the user calling directly the application 
services, the other sends commands/queries via CometD. The first verifies notifications by attaching fake adapters to the app's Driving Ports (FakeExternalEventPublisher,
in-memory repository implementations), the second actually subscribes to CometD channels. See TestAgent, AppLevelTestAgent and MessagingTestAgent for details.

Stuff missing:

* the Wallet component is very lean, no error handling, no complete anti-corruption layers. It's purpose is to demonstrate how can two remote Bounded Contexts interact
* CometD - very simplistic implementation, no error handling, no security. Currently all clients could subscribe to each others private channels
* do not refresh the browser because you'll lose your session

The project extensively uses marker interfaces for Hexagonal Architecture/DDD building blocks/patterns/concepts to make the intentions clearer. 
They are under the *.util.marker package. Other way could have been to use annotations.
