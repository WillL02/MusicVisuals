package ie.tudublin;

import C21423244.Monitor;
import C21318233.Heathens;
import C21402094.*;
import example.CubeVisual;
import example.Test;

public class Main 
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Monitor());		
	}

	// For testing Heathens visualizer
	public void freaks()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new freaks());		
	}


	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();
	}
}