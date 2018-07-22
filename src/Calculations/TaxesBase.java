package Calculations;

import java.util.HashMap;

public class TaxesBase {
	private static String Categories[] = { "Single", "HeadOfHousehold",
			"MarriedFillingJointly", "MarriedFillingSeperately" };
	private static double TaxCategory[][] = new double[5][3];
	private static double TaxLimits[] = new double[4];
	private static HashMap<String, double[][]> DataBase = new HashMap<String, double[][]>();
	private static HashMap<String, double[]> DataBaseLimits = new HashMap<String, double[]>();

	public TaxesBase() {
		createDataBaseAndLimits();
	}

	public void createDataBaseAndLimits() {
		for (int i = 0; i < 4; i++) {
			setCategory(i);
			DataBase.put(Categories[i], TaxCategory);
			DataBaseLimits.put(Categories[i], TaxLimits);
		}
	}

	public void setCategory(int category) {
		if (category == 0) {
			createSingle();
		}
		if (category == 1) {
			createHeadOfHouseHold();
		}
		if (category == 2) {
			createMarriedFillingJointly();
		}
		if (category == 3) {
			createMarriedFillingSeperately();
		}
	}

	public void createSingle() {
		createSingleTaxes();
		createSingleLimits();
	}

	public void createHeadOfHouseHold() {
		createHeadOfHouseHoldTaxes();
		createHeadOfHouseHoldLimits();
	}

	public void createMarriedFillingJointly() {
		createMarriedFillingJointlyTaxes();
		createMarriedFillingJointlyLimits();
	}

	public void createMarriedFillingSeperately() {
		createMarriedFillingSeperatelyTaxes();
		createMarriedFillingSeperatelyLimits();
	}

	// In case there is a problem with initialization
	// TaxCategory[0][0] = TaxCategory[0][2] = 0;
	public void createSingleTaxes() {
		TaxCategory[0][1] = 5.35;
		TaxCategory[1][0] = 1320.38;
		TaxCategory[1][1] = 7.05;
		TaxCategory[1][2] = 24680.0;
		TaxCategory[2][0] = 5296.58;
		TaxCategory[2][1] = TaxCategory[3][1] = 7.85;
		TaxCategory[2][2] = 81080;
		TaxCategory[3][0] = 5996.80;
		TaxCategory[3][2] = 90000;
		TaxCategory[4][0] = 10906.19;
		TaxCategory[4][1] = 9.85;
		TaxCategory[4][2] = 152540.0;
	}

	public void createSingleLimits() {
		TaxLimits[0] = 24680.0;
		TaxLimits[1] = 81080.0;
		TaxLimits[2] = 90000.0;
		TaxLimits[3] = 152540.0;
	}

	// TaxCategory[0][0] = TaxCategory[0][2] = 0;
	// TaxCategory[0][1] = 5.35;
	// TaxCategory[4][1] = 9.85;
	public void createHeadOfHouseHoldTaxes() {
		TaxCategory[1][0] = 1625.87;
		TaxCategory[1][1] = TaxCategory[2][1] = 7.05;
		TaxCategory[1][2] = 30390.0;
		TaxCategory[2][0] = 5828.38;
		TaxCategory[2][2] = 90000.0;
		TaxCategory[3][0] = 8092.13;
		TaxCategory[3][1] = 7.85;
		TaxCategory[3][2] = 122110.0;
		TaxCategory[4][0] = 14472.61;
		TaxCategory[4][2] = 203390.0;
	}

	public void createHeadOfHouseHoldLimits() {
		TaxLimits[0] = 30390.0;
		TaxLimits[1] = 90000.0;
		TaxLimits[2] = 122110.0;
		TaxLimits[3] = 203390.0;
	}

	// TaxCategory[0][0] = TaxCategory[0][2] = 0;
	// TaxCategory[0][1] = 5.35;
	// TaxCategory[4][1] = 9.85;
	public void createMarriedFillingJointlyTaxes() {
		TaxCategory[1][0] = 1930.28;
		TaxCategory[1][1] = TaxCategory[2][1] = 7.05;
		TaxCategory[1][2] = 36080.0;
		TaxCategory[2][0] = 5731.64;
		TaxCategory[2][2] = 90000.0;
		TaxCategory[3][0] = 9492.82;
		TaxCategory[3][1] = 7.85;
		TaxCategory[3][2] = 143350.0;
		TaxCategory[4][0] = 18197.69;
		TaxCategory[4][2] = 254240.0;
	}

	public void createMarriedFillingJointlyLimits() {
		TaxLimits[0] = 36080.0;
		TaxLimits[1] = 90000.0;
		TaxLimits[2] = 143350.0;
		TaxLimits[3] = 254240.0;
	}

	// TaxCategory[0][0] = TaxCategory[0][2] = 0;
	// TaxCategory[0][1] = 5.35;
	// TaxCategory[4][1] = 9.85;
	public void createMarriedFillingSeperatelyTaxes() {
		TaxCategory[1][0] = 965.14;
		TaxCategory[1][1] = 7.05;
		TaxCategory[1][2] = 18040.0;
		TaxCategory[2][0] = 4746.76;
		TaxCategory[2][1] = TaxCategory[3][1] = 7.85;
		TaxCategory[2][2] = 71680.0;
		TaxCategory[3][0] = 6184.88;
		TaxCategory[3][2] = 90000.0;
		TaxCategory[4][0] = 9098.80;
		TaxCategory[4][2] = 127120.0;
	}

	public void createMarriedFillingSeperatelyLimits() {
		TaxLimits[0] = 18040.0;
		TaxLimits[1] = 71680.0;
		TaxLimits[2] = 90000.0;
		TaxLimits[3] = 127120.0;
	}

	public double[] getValues(String type, double income) {
		for (int i = 0; i < 3; i++) {
			if (DataBaseLimits.get(type)[i] < income) {
				return DataBase.get(type)[i];
			}
		}
		return DataBase.get(type)[3];
	}
}
