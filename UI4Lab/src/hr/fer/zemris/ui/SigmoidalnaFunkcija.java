package hr.fer.zemris.ui;

public class SigmoidalnaFunkcija extends PrijenosnaFunkcija {
	double a;

	public SigmoidalnaFunkcija() {
		this(1.0);
	}

	public SigmoidalnaFunkcija(double a) {
		this.a = a;
	}

	@Override
	public double compute(double x) {
		double eClan = Math.exp((-1) * a * x);
		double result = 1.0 / (1.0 + eClan);
		return result;
	}
}
