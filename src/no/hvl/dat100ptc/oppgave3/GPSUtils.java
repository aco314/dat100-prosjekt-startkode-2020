package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import java.util.Locale;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max = da[0];
		
		for (double d: da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min = da[0];

		for (double d: da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] lats = new double[gpspoints.length];
		
		for (int i = 0; i < lats.length; i++) {
			lats[i] = gpspoints[i].getLatitude();
		}
		
		return lats;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] lons = new double[gpspoints.length];
		
		for (int i = 0; i < lons.length; i++) {
			lons[i] = gpspoints[i].getLongitude();
		}
		
		return lons;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double lat1 = toRadians(gpspoint1.getLatitude());
		double lon1 = toRadians(gpspoint1.getLongitude());
		double lat2 = toRadians(gpspoint2.getLatitude());
		double lon2 = toRadians(gpspoint2.getLongitude());

		double dLat = lat2 - lat1;
		double dLon = lon2 - lon1;
		
		double a = pow(sin(dLat/2), 2) + cos(lat1) * cos(lat2) * pow(sin(dLon/2), 2);
		double c = 2 * atan2(sqrt(a), sqrt(1-a));
		double d = R*c;
		
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs = gpspoint2.getTime() - gpspoint1.getTime();
		double distance = distance(gpspoint1, gpspoint2);

		return 3.6 * distance / secs;
	}

	public static String formatTime(int secs) {

		int hrs = secs / 3600;
		int min = secs % 3600 / 60;
		int sec = secs % 60;
		
		// %02d betyr "formatter tallet med 2 siffer, og sett inn 0 i begynnelsen om det trengs"
		return String.format("  %02d:%02d:%02d", hrs, min, sec);

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		// Må spesifisere "Locale.US", ellers vil den bruke komma istedenfor punktum til å vise desimaltall
		// Formatteringen sier at det skal være 10 tegn foran desimalplassen, og vil sette inn mellomrom om nødvendig.
		String result = String.format(Locale.US, "%"+TEXTWIDTH+".2f", d);
		
		return result;
	}
}
