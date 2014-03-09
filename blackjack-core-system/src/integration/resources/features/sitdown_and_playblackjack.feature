Feature: players sit down and play. One wins

Scenario: players sit down and play. One wins

Given player '1' is registered
And player '2' is registered

Given a prepared deck with cards in order
| cards |
| 2c    |
| 2s    |
| 7d    |
| Jh    |
| 5d    |
| 3h    |
| 4d    |
| 2h    |

And there is an empty table with id '12'

Then players can see tables in lobby
| tableId | players |
| 12      |         |


When player '1' sits to table '12'

Then players can see tables in lobby
| tableId | players |
| 12      | 1       |

When player '2' sits to table '12'

Then players can see tables in lobby
| tableId | players |
| 12      | 1,2     |

Then game started on table '12'

And player '1' is debited with '500'
And player '2' is debited with '500'

And player '2' has been dealt '2c' at table '12'
And player '1' has been dealt '2s' at table '12'
And player '2' has been dealt '7d' at table '12'
And player '1' has been dealt 'Jh' at table '12'

When player '2' hits at table '12'
Then player '2' has been dealt '5d' at table '12'

When player '1' hits at table '12'
Then player '1' has been dealt '3h' at table '12'

When player '2' hits at table '12'
Then player '2' has been dealt '4d' at table '12'

When player '1' hits at table '12'
Then player '1' has been dealt '2h' at table '12'

When player '2' stands at table '12'
Then players can see player '2' stands at table '12'
When player '1' stands at table '12'
Then players can see player '1' stands at table '12'


Then player '2' won at table '12'
And player '2' is credited with '1000'



