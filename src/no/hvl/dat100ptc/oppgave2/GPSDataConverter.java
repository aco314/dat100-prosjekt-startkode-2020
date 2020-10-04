package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		String klokkeslett = timestr.substring(TIME_STARTINDEX, 19);
		
		String timeString = klokkeslett.substring(0, 2);
		String minuttString = klokkeslett.substring(3, 5);
		String sekundString = klokkeslett.substring(6, 8);
		
		int hr = Integer.valueOf(timeString) * 3600;
		int min = Integer.valueOf(minuttString) * 60;
		int sec = Integer.valueOf(sekundString);
		
		return hr + min + sec;
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		int time = toSeconds(timeStr);
		double lat = Double.valueOf(latitudeStr);
		double lon = Double.valueOf(longitudeStr);
		double elev = Double.valueOf(elevationStr);

		GPSPoint gpspoint = new GPSPoint(time, lat, lon, elev);
		return gpspoint;
	}
	
}
