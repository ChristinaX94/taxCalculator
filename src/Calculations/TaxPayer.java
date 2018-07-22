package Calculations;

import java.util.ArrayList;
import java.util.Iterator;

public class TaxPayer {
	private String name;
	private String codeAFM;
	private String type;
	private double income;
	private double basicTax;
	private double tax;
	private TaxesBase base = new TaxesBase();
	private ArrayList<Receipt> receiptList = new ArrayList<Receipt>();
	private static int percentageCategories[] = { 20, 40, 60, 80 };
	private static double percentages[] = { 0.08, 0.04, -0.15, -0.3 };

	public TaxPayer(String[] personInformation) {
		this.name = personInformation[0];
		this.codeAFM = personInformation[1];
		this.type = personInformation[2];
		this.income = Double.parseDouble(personInformation[3]);
	}

	public void calculateTax() {
		basicTax = base.getValues(type, income)[0]
				+ base.getValues(type, income)[1] / 100
				* (income - base.getValues(type, income)[2]);
	}

	public boolean checkReceipt(String[] receiptInformation,
			String companyInformation[]) {
		for (Receipt r : receiptList) {
			if (Integer.parseInt(r.getCode()) == Integer
					.parseInt(receiptInformation[0])) {
				return false;
			}
		}
		addReceipt(receiptInformation, companyInformation);
		return true;
	}

	public void addReceipt(String[] receiptInformation,
			String companyInformation[]) {
		Receipt receipt = new Receipt(receiptInformation, companyInformation);
		for (Receipt r : receiptList) {
			if (Integer.parseInt(r.getCode()) > Integer
					.parseInt(receiptInformation[0])) {
				receiptList.add(receiptList.indexOf(r), receipt);
				return;
			}
		}
		receiptList.add(receiptList.size(), receipt);
		calculateActualTax();
	}

	public void calculateActualTax() {
		for (int i = 0; i < 4; i++) {
			if ((calculateReceiptCost() / income) * 100 < percentageCategories[i]) {
				tax = basicTax + basicTax * percentages[i];
				return;
			}
		}
	}

	public double calculateReceiptCost() {
		double totalCost = 0.0;
		for (Receipt r : receiptList) {
			totalCost += r.getValue();
		}
		return totalCost;
	}

	public void removeReceipt(String code) {
		Iterator<Receipt> i = receiptList.iterator();
		while (i.hasNext()) {
			if (i.next().getCode().equals(code)) {
				i.remove();
				calculateActualTax();
				return;
			}
		}
	}

	public Receipt getReceipt(String id) {
		for (Receipt r : receiptList) {
			if (r.getCode().equals(id)) {
				return r;
			}
		}
		return null;
	}

	public int getReceiptCount(String type) {
		int count = 0;
		for (Receipt r : receiptList) {
			if (r.getType().contains(type)) {
				count++;
			}
		}
		return count;
	}

	public String[] getBasicInformation() {
		String information[] = { getName(), getAFM(), getIncome() + "",
				getBasicTax() + "", getTaxIncrease() + "", getTax() + "",
				getReceiptCount() + "" };
		return information;
	}

	public int[] getReceiptInformation() {
		int logReceipts[] = { getReceiptCount("Entertainment"),
				getReceiptCount("Basic"), getReceiptCount("Travel"),
				getReceiptCount("Health"), getReceiptCount("Other") };
		return logReceipts;
	}

	public double getTax() {
		return tax;
	}

	public double getBasicTax() {
		return basicTax;
	}

	public double getTaxIncrease() {
		return tax - basicTax;
	}

	public String getName() {
		return name;
	}

	public String getAFM() {
		return codeAFM;
	}

	public String getType() {
		return type;
	}

	public double getIncome() {
		return income;
	}

	public Receipt getReceipt(int id) {
		return receiptList.get(id);
	}

	public String getReceiptCode(int i) {
		return getReceipt(i).getCode();
	}

	public String getReceiptDate(int i) {
		return getReceipt(i).getDate();
	}

	public String getReceiptKind(int i) {
		return getReceipt(i).getType();
	}

	public double getReceiptAmount(int i) {
		return getReceipt(i).getValue();
	}

	public String getReceiptCompany(int i) {
		return getReceipt(i).getCompanyName();
	}

	public String getReceiptCountry(int i) {
		return getReceipt(i).getCompanyCountry();
	}

	public String getReceiptCity(int i) {
		return getReceipt(i).getCompanyCity();
	}

	public String getReceiptStreet(int i) {
		return getReceipt(i).getCompanyStreet();
	}

	public String getReceiptNumber(int i) {
		return getReceipt(i).getCompanyNumber();
	}

	public int getReceiptCount() {
		return receiptList.size();
	}
}
