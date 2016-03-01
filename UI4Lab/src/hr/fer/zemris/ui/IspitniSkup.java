package hr.fer.zemris.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IspitniSkup {
	private List<Double> x;
	private List<Double> fx;

	public IspitniSkup() {
		x = new ArrayList<Double>();
		fx = new ArrayList<Double>();
	}

	public static IspitniSkup ucitajIzFilea(Path p) throws IOException {
		IspitniSkup ispitni = new IspitniSkup();
		List<String> linije = Files.readAllLines(p);
		for (String line : linije) {
			String[] s = line.trim().split("\t");
			ispitni.x.add(Double.parseDouble(s[0]));
			ispitni.fx.add(Double.parseDouble(s[1]));
		}
		return ispitni;
	}

	public double getFx(int index) {
		return fx.get(index);
	}

	public double getX(int index) {
		return x.get(index);
	}

	public int getN() {
		return x.size();
	}
}
