package sourcecoded.quantum.api.event.discovery;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * An event that, when triggered, should allow addon-mods
 * to register their Discoveries in the DiscoveryRegistry.
 *
 * This makes sure that discoveries are ordered correctly.
 *
 * @author SourceCoded
 */
public class DiscoveryRegistrationEvent extends Event {

    public DiscoveryRegistrationEvent() {}

}
