package perfectpitch.properties;

import alde.commons.properties.Property;
import alde.commons.properties.PropertyFileManager;

public abstract class Properties {

	private static PropertyFileManager propertyFile;

	static {
		propertyFile = new PropertyFileManager("perfect_pitch.properties");
	}

	//@formatter:off
	
	public static final Property SHOW_SPLASHSCREEN_ON_STARTUP = new Property("SHOW_SPLASHSCREEN_ON_STARTUP", "Display splash screen on start", Property.TRUE, propertyFile);
	//@formatter:on

}