This is a reference DDD project, implementing a simplified Blackjack game engine.
The architecture is Hexagonal.

It exposes http endpoints to interact with and provides in-memory (a simple map-based and a Hazelcast based) persistence.
The http endpoints are implemented with REST and also with servlets.

It will have a simple UI in browser. The players can join the game, will be randomly matched to other players. There will also be a leaderboard showing the win records. 

For the rules of the game, check org.home.blackjack.domain.game.Game.

The DDD concepts/patterns the project show examples are:

Transactional consistency requirements: See them in Game class
Eventual consistency requirements:
1. Leaderboard's values must correspond to the aggregated results of the individual Game results