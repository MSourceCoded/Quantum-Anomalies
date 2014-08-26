package sourcecoded.quantum.api.sceptre;

import java.util.ArrayList;

public class SceptreFocusRegistry {

    private static ArrayList<ISceptreFocus> foci = new ArrayList<ISceptreFocus>();

    public static void registerFocus(ISceptreFocus focus) {
        foci.add(focus);
    }

    public static ISceptreFocus getFocus(String identifier) {
        for (ISceptreFocus aFoci : foci) if (aFoci.getFocusIdentifier().equals(identifier)) return aFoci;

        return null;
    }

    public static ISceptreFocus getNextFocus(ISceptreFocus focus) {
        if (foci.size() == 0) return null;

        int index = foci.indexOf(focus);
        if (focus == null) return foci.get(0);

        if (index == -1) return null;

        index++;

        if (index == foci.size()) return null;
        return foci.get(index);
    }

}
