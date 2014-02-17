package org.home.blackjack.domain.player;

import org.apache.commons.lang3.Validate;

/**
 * Value Object
 * @author mate.magyari
 *
 */
public class PlayerName {
    
    private final String text;

    public PlayerName(String text) {
        Validate.notBlank(text);
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

}
