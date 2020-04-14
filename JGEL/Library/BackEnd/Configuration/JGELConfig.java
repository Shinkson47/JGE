package backend.configuration;

/**
 * Defines the default types and data points of a configuration.
 * 
 * @author gordie
 *
 */
public interface JGELConfig {
	
	//Error Management
	public static final int ErrorMargin = 5;
	public static final boolean AllowEIS = true;
	public static final boolean AllowErrNotif = true;
	public static final boolean AllowCascadeDetection = true;
	public static final long MillisTollerance = 100;
	public static final int CascadeTollerance = 3;
	
	//Window Manager
	public static final int DefaultResolutionX = 400;
	public static final int DefaultResolutionY = 400;
	
}
