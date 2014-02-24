Scenario: players sit down and play. One wins

Given a prepared deck with cards in order
| cards |
| 7s    |
| Ad    |

Given there is a table with id '12'
And table '12' is empty

Then tables in lobby
| tableId | players |
| 12      |         |


When player '1' sits to table '12'

Then observer sees tables in lobby
| tableId | players |
| 12      | 1       |

When player '2' sits to table '12'

Then observer sees in lobby
| tableId | players |
| 12      | 1,2     |

Then game started on table '12'
And player '1' has been dealt '7s'
And player '2' has been dealt 'Ad'
And player '1' has been dealt 'Ad'
And player '2' has been dealt 'Ad'

When player '1' hits
Then player '1' has been dealt '7s'

When player '2' hits
Then player '1' has been dealt '7s'

Then player '1' won



