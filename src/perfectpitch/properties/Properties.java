package perfectpitch.properties;

import alde.commons.properties.Property;
import alde.commons.properties.PropertyFileManager;

public abstract class Properties {

	static {
		propertyFile = new PropertyFileManager("perfect_pitch.perfectpitch.properties");
	}

	private static PropertyFileManager propertyFile;

	//@formatter:off
	
	public static final Property SHOW_SPLASHSCREEN_ON_STARTUP = new Property("SHOW_SPLASHSCREEN_ON_STARTUP", "Display splash screen on start", Property.TRUE, propertyFile);
	
	public static final Property OPEN_CONFIG_WINDOW_ON_STARTUP = new Property("OPEN_CONFIG_WINDOW", "Display 'open config window' on start", Property.TRUE, propertyFile);

	//@formatter:on

}