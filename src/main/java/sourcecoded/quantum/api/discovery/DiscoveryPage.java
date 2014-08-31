package sourcecoded.quantum.api.discovery;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.api.injection.IInjectorRecipe;

public class DiscoveryPage {

    /**
     * Page types for the page.
     */
    public static enum PageContext {
        /**
         * A simple text page
         */
        TEXT,

        /**
         * A simple image page
         */
        IMAGE,

        /**
         * Simple, vanilla crafting
         */
        CRAFTING,

        /**
         * Injection crafting
         */
        INJECTION
    }

    /**
     * The type of page.
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public PageContext type = PageContext.TEXT;

    /**
     * The title for the page.
     * Null if no title.
     *
     * Used by all types
     */
    public String title = null;

    /**
     * The text on the page, used
     * for PageContext.TEXT types.
     *
     * PageContext.IMAGE uses
     * this as a caption
     */
    public String text = null;

    /**
     * The image on the page, used
     * for PageContext.IMAGE types
     */
    public ResourceLocation image = null;

    /**
     * The recipe for the page.
     *
     * IRecipe for PageContext.CRAFTING
     * IInjectionRecipe for PageContext.INJECTION
     */
    public Object recipe = null;

    /**
     * Create a new Text discovery page
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, String text) {
        this.title = title;
        this.text = text;

        this.type = PageContext.TEXT;
    }

    /**
     * Create a new Image discovery page
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, ResourceLocation image, String caption) {
        this.title = title;
        this.image = image;
        this.text = caption;

        this.type = PageContext.IMAGE;
    }

    /**
     * Create a new Recipe discovery page
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, IRecipe crafting) {
        this.title = title;
        this.recipe = crafting;

        this.type = PageContext.CRAFTING;
    }

    /**
     * Create a new Injector discovery page
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, IInjectorRecipe injection) {
        this.title = title;
        this.recipe = injection;

        this.type = PageContext.INJECTION;
    }

    /**
     * Localize the text of this object
     */
    public String localizeText() {
        if (this.text != null)
            return StatCollector.translateToLocal(this.text);
        return null;
    }

    /**
     * Localize the title of this object
     */
    public String localizeTitle() {
        if (this.title != null)
            return StatCollector.translateToLocal(this.title);
        return null;
    }

}