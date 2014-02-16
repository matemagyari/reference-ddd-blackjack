package org.home.blackjack.domain;

import org.home.blackjack.domain.event.GameFinishedEvent;

/**
 * This should be refactored, since we must lock the DB for the update. It means
 * we have to go through the app layer. So the Listener in the Domain must catch
 * the event, then pass it to an App service. But it can't be done directly,
 * since Domain cn't call App. Maybe Domain Interface + App implementation?
 * 
 * @author Mate
 * 
 */
public class GameFinishedEventListener {

	private PlayerRecordRepository playerRecordRepository;

	public void receive(GameFinishedEvent event) {
		PlayerRecord playerRecord = playerRecordRepository.find(event.getWinner());
		playerRecord.recordWin();
		playerRecordRepository.update(playerRecord);
	}

}
