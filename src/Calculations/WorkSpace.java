package Calculations;

import java.util.ArrayList;

public class WorkSpace {
	private static ArrayList<TaxPayer> allTaxPayers = new ArrayList<TaxPayer>();
	private static String ReceiptCategories[] = { "Entertainment", "Basic",
			"Travel", "Health", "Other" };

	public void addTaxPayer(String personInformation[]) {
		TaxPayer person = new TaxPayer(personInformation);
		person.calculateTax();
		person.calculateActualTax();
		allTaxPayers.add(person);
	}

	public TaxPayer getTaxPayer(String name) {
		for (TaxPayer t : allTaxPayers) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	public void removeTaxPayer(TaxPayer activePerson) {
		allTaxPayers.remove(activePerson);
	}

	public TaxPayer getTaxPayer(int position) {
		return allTaxPayers.get(position);
	}

	public int getTaxPayerCount() {
		return allTaxPayers.size();
	}

	public int getTaxPayerReceipts(TaxPayer person, int i) {
		return person.getReceiptCount(ReceiptCategories[i]);
	}

	public String getTaxPayerName(int i) {
		return getTaxPayer(i).getName();
	}
}
