package law.raw;



import law.raw.composition.CompositionDTO;
import law.raw.composition.components.InstrumentTrack;
import law.raw.utility.PropertiesManager;
import law.raw.view.View;


public class CompAppDriver 
{
	public static PropertiesManager PROPERTIES_MANAGER = new PropertiesManager();
	public static String[] fileExtensions = {".compapp"};
	
    public static void main(String[] args)
    {
        View gui = new View();
    }
}
