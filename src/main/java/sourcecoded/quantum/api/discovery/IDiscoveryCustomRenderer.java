package sourcecoded.quantum.api.discovery;

/**
 * A class implemented by DiscoveryItems/Categories to denote a custom
 * renderer to be used in the Anomolical Journal's GUI
 */
public interface IDiscoveryCustomRenderer {

    /**
     * Custom render context for the icon
     */
    public static int CUSTOM_ICON = 1;

    /**
     * Custom render context for the platform
     * behind the icon
     */
    public static int CUSTOM_PLATFORM = 2;

    /**
     * Render the discovery item. Context is passed from #getRenderContext();
     */
    public void render(int offsetX, int offsetY, int mouseX, int mouseZ, int context);

    /**
     * Get the render context for the discovery item. Return the static variables
     * of this interface prefixed with CUSTOM_. These values can be added
     * together.
     */
    public int getRenderContext();

}
