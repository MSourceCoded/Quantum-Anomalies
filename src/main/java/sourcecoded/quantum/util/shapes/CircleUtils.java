package sourcecoded.quantum.util.shapes;

/**
 * Credit: TheOddCraft
 */
public class CircleUtils {
	public static void generate(int x0, int z0, int radius, CircleCallback callback)
	{
		int error = -radius;
		int x = radius;
		int z = 0;
		
		while (x >= z)
		{
			generateBlocks(x0, z0, x, z, callback);
			if (x != z)
			{
				generateBlocks(x0, z0, z, x, callback);
			}
		 
			int lastZ = z;
			
			error += z;
			++z;
			error += z;
		 
			if (error >= 0)
			{				
				error -= x;
				--x;
		      	error -= x;
			}
		}
	}

	public static void generateFilled(int x0, int z0, int radius, CircleCallback callback)
	{
		int error = -radius;
		int x = radius;
		int z = 0;
		
		while (x >= z)
		{
			generateLines(x0, z0, x, z, callback);		 
			if (x != z)
			{
				generateLines(x0, z0, z, x, callback);
			}
			
			error += z;
			++z;
			error += z;
		 
			if (error >= 0)
			{
				
				
				error -= x;
				--x;
		      	error -= x;
			}
		}
	}
	
	private static void generateBlocks(int x0, int z0, int x, int z, CircleCallback callback)
	{
	    callback.call(x0 + x, z0 + z);
		if (x != 0) 
		{
			callback.call(x0 - x, z0 + z);
		}
		
		if (z != 0)
		{
			callback.call(x0 + x, z0 - z);
		}
		
		if (x != 0 && z != 0) 
		{
			callback.call(x0 - x, z0 - z);
		}
	}
	
	private static void generateLines(int x0, int z0, int x, int z, CircleCallback callback)
	{
		line(x0 - x, z0 + z, x0 + x, callback);
		if(x!= 0 && z != 0)
		{
			line(x0 - x, z0 - z, x0 + x, callback);
		}
	}
	
	private static void line(int x0, int z0, int x1, CircleCallback callback)
    {
        for (int x = x0; x <= x1; ++x)
        {
            callback.call(x, z0);
        }
    }
}
