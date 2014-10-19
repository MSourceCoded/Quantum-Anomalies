package sourcecoded.quantum.api.discovery;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;

/**
 * A page of data for a DiscoveryItem
 *
 * @author SourceCoded
 */
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
         * Arrangement table crafting
         */
        ARRANGEMENT,

        /**
         * Injection crafting
         */
        INJECTION,

        /**
         * Vacuum catalyst crafting
         */
        VACUUM
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

    public int width, height;

    /**
     * The recipe for the page.
     *
     * IRecipe for PageContext.CRAFTING
     * IArrangementRecipe for PageContext.ARRANGEMENT
     * IInjectionRecipe for PageContext.INJECTION
     * IVacuumRecipe for PageContext.VACUUM
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
     *
     * Please note that the width/height are of the ResourceLocation
     * itself, not the desired size to render.
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, ResourceLocation image, String caption, int width, int height) {
        this.title = title;
        this.image = image;
        this.text = caption;

        this.type = PageContext.IMAGE;

        this.width = width;
        this.height = height;
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
     * Create a new Arrangement discovery page
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, IArrangementRecipe arrangement) {
        this.title = title;
        this.recipe = arrangement;

        this.type = PageContext.ARRANGEMENT;
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
     * Create a new Vacuum discovery page
     * @see sourcecoded.quantum.api.discovery.DiscoveryPage.PageContext
     */
    public DiscoveryPage(String title, IVacuumRecipe vacuum) {
        this.title = title;
        this.recipe = vacuum;

        this.type = PageContext.VACUUM;
    }

    /**
     * Localize the text of this object
     */
    public String localizeText() {
        if (this.text != null)
            return LocalizationUtils.translateLocalWithColours(this.text, this.text);
        return null;
    }

    /**
     * Localize the title of this object
     */
    public String localizeTitle() {
        if (this.title != null)
            return LocalizationUtils.translateLocalWithColours(this.title, this.title);
        return null;
    }

}