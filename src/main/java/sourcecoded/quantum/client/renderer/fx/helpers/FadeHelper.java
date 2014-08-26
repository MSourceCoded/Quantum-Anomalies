package sourcecoded.quantum.client.renderer.fx.helpers;

public class FadeHelper {

    public float min, max, maxAge, timing;

    public float g1, g2;

    public FadeHelper(float min, float max, float maxAge, float timingClip) {
        this.min = min;
        this.max = max;
        this.maxAge = maxAge;
        this.timing = timingClip;

        g1 = (float)(max - min) / (float)(timing * maxAge);
        g2 = (float)(min - max) / (float)(timing * maxAge);
    }

    public float updateFade(float currentAge) {
        float currentPercentage = (float)currentAge / (float)maxAge;
        float inverseTiming = 1F - timing;

        if (currentPercentage >= timing && currentPercentage <= inverseTiming) return max;

        float clipped;
        if (currentPercentage < timing) {
            clipped = currentAge;
            return min + (g1 * clipped);
        } else if (currentPercentage > inverseTiming) {
            clipped = currentAge - (inverseTiming * maxAge);
            return max + (g2 * clipped);
        }

        else return min;
    }



}
