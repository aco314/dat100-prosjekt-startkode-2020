package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static final int MARGIN = 50;		// margin on the sides 
	private static final int MAXBARHEIGHT = 500; // assume no height above 500 meters

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}

	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + 3 * N, 2 * MARGIN + MAXBARHEIGHT);
		
		// top margin + height of drawing area
		showHeightProfile(MARGIN + MAXBARHEIGHT); 
	}
	
	public void showHeightProfile(int ybase) {

		int scale = Integer.valueOf(getText("Skaleringstid"));
		
		// ybase indicates the position on the y-axis where the columns should start
		
		for (int i = 0; i < gpspoints.length; i++) {
			
			// Del av ekstraoppgave 6b:
			if (i != gpspoints.length-1) {
				int timediff = gpspoints[i+1].getTime() - gpspoints[i].getTime();
				timediff = timediff/scale;
				pause(timediff * 1000); // Ganger med 1000 for å gjøre om fra s til ms
			}
			// ----------------------------------
			
			int elev = (int)gpspoints[i].getElevation();
			int x = MARGIN + 3*i;
			int y2 = ybase;
			
			if (elev > 0) {
				y2 -= elev;
			}
			
			drawLine(x, ybase, x, y2);
			
			
		}
		
	}

}


