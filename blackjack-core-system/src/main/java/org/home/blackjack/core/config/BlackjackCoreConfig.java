package org.home.blackjack.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({BlackjackCoreInfLevelConfig.class, BlackjackCoreAppLevelConfig.class})
@ImportResource("classpath:META-INF/applicationContext-blackjack-core-scan.xml")
public class BlackjackCoreConfig {
}
