package no.hvl.dat100ptc.oppgave5;


import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
	
		double ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		
		return ystep;
	}

	public void showRouteMap(int ybase) {

		double[] longs = GPSUtils.getLongitudes(gpspoints);
		double[] lats = GPSUtils.getLatitudes(gpspoints);
		
		double[] londiff = new double[longs.length-1];
		double[] latdiff = new double[lats.length-1];
		
		for (int i = 0; i < longs.length-1; i++) {
			
			londiff[i] = xstep() * (longs[i+1] - longs[0]);
			latdiff[i] = ystep() * (lats[i+1] - lats[0]);
		}

		// Finner det vestligste og laveste punktet
		double xmin = GPSUtils.findMin(londiff);
		double ymin = GPSUtils.findMin(latdiff);
		setColor(0, 255, 0);
		
		for (int i = 0; i < londiff.length; i++) {
			
			// Grønn prikk på nåværende punkt:
			double x = londiff[i];
			double y = latdiff[i];

			x = x + MARGIN + Math.abs(xmin);
			y = ybase - (y - ymin);
			fillCircle((int)(x), (int)y, 3);
			
			// Strek mellom nåværende og forrige punkt:
			if (i >= 1) {
				double prevX = londiff[i-1];
				double prevY = latdiff[i-1];
				
				prevX = prevX + MARGIN + Math.abs(xmin);
				prevY = ybase - (prevY - ymin);
				drawLine((int)prevX, (int)prevY, (int)x, (int)y);
			}
			
			// Stor blå prikk på siste punkt:
			if (i == londiff.length-1) {
				setColor(0, 0, 255);
				fillCircle((int)(x), (int)y, 6);
			}
		}
		
	}

	public void showStatistics() {

		//int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		String totalTime = GPSUtils.formatTime(gpscomputer.totalTime());
		String totalDistance = GPSUtils.formatDouble(gpscomputer.totalDistance()/1000);
		String totalElev = GPSUtils.formatDouble(gpscomputer.totalElevation());
		String maxSpeed = GPSUtils.formatDouble(gpscomputer.maxSpeed());
		String avgSpeed = GPSUtils.formatDouble(gpscomputer.averageSpeed());
		String energy = GPSUtils.formatDouble(gpscomputer.totalKcal(80));
		
		drawString("Total Time     :" + totalTime + " ", MARGIN, 20);
		drawString("Total distance :" + totalDistance + " km ", MARGIN, 40);
		drawString("Total elevation:" + totalElev + " m ", MARGIN, 60);
		drawString("Max speed      :" + maxSpeed + " km/t ", MARGIN, 80);
		drawString("Average speed  :" + avgSpeed + " km/t ", MARGIN, 100);
		drawString("Energy         :" + energy + " kcal ", MARGIN, 120);
		
	}
}


