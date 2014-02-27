Feature: players sit down and play. One wins

Scenario: players sit down and play. One wins

Given a prepared deck with cards in order
| cards |
| 7s    |
| Ad    |

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
And player '1' has been dealt '7s' at table '12'
And player '2' has been dealt 'Ad' at table '12'
And player '1' has been dealt 'Ad' at table '12'
And player '2' has been dealt 'Ad' at table '12'

When player '1' hits at table '12'
Then player '1' has been dealt '7s' at table '12'

When player '2' hits at table '12'
Then player '1' has been dealt '7s' at table '12'

Then player '1' won at table '12'



