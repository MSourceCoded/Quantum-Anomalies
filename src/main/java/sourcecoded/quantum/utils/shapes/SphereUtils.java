//	Sphere Generator

package sourcecoded.quantum.utils.shapes;

import java.util.Hashtable;

/**
 * Credit: TheOddCraft
 */
public class SphereUtils {
	public static void generate(int x0, int y0, int z0, int radius, SphereCallback callback) {
		sSphereGenerator.pregenerate(x0, y0, z0, callback, false);
		CircleUtils.generate(x0, z0, radius, sSphereGenerator);
	}
	
	public static void generateFilled(int x0, int y0, int z0, int radius, SphereCallback callback) {
		sSphereGenerator.pregenerate(x0, y0, z0, callback, true);
		CircleUtils.generate(x0, z0, radius, sSphereGenerator);
	}
	
	private static class SphereGenerator implements CircleCallback {
		public void pregenerate(int x0, int y0, int z0, SphereCallback callback, boolean filled) {
			mX0 = x0;
			mY0 = y0;
			mZ0 = z0;
			mCallback = callback;
			mTouched.clear();
			mFilled = filled;
		}

		public void call(int x1, int z1) {
			mTemp = z1;
			CircleUtils.generate(0, mY0, x1 - mX0, new CircleCallback() {
                public void call(int x2, int y2) {
                    updateBlock(mX0 + x2, y2, mTemp);
                }
            });
			
			mTemp = x1;
			if(mFilled) {
				CircleUtils.generateFilled(0, mY0, z1 - mZ0, new CircleCallback() {
                    public void call(int x2, int y2) {
                        updateBlock(mTemp, y2, mZ0 + x2);
                    }
                });
			} else {
				CircleUtils.generate(0, mY0, z1 - mZ0, new CircleCallback() {
                    public void call(int x2, int y2) {
                        updateBlock(mTemp, y2, mZ0 + x2);
                    }
                });
			}
		}
		
		private void updateBlock(int x, int y, int z) {
			String key = x + " " + y + " " + " " + z;
			if(mTouched.containsKey(key)) {
				return;
			}
			
			mTouched.put(key, true);
			
			mCallback.call(x, y, z);
		}
		
		private Hashtable<String, Boolean> mTouched = new Hashtable<String, Boolean>();
		private SphereCallback mCallback;
		private int mX0;
		private int mY0;
		private int mZ0;
		private int mTemp;
		private boolean mFilled;
	}

	private static SphereGenerator sSphereGenerator = new SphereGenerator();
}
