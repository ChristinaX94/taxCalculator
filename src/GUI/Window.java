package GUI;

import InData.*;
import OutData.BarChart;
import OutData.FileEditor;
import OutData.Log;
import OutData.OutDataFactory;
import OutData.PieChart;
import Calculations.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class Window extends JFrame
{
	private JTabbedPane tabbedPane;
	private boolean imageFlag=false;
	private WorkSpace workspace = new WorkSpace();
	private static ArrayList<JPanel> shownTabs = new ArrayList<JPanel>();
	private static ArrayList<TaxPayer> shownPeople = new ArrayList<TaxPayer>();
	private static ArrayList<TaxPayer> passivePeople = new ArrayList<TaxPayer>();
	private static HashMap<TaxPayer,String> allOpenedFiles = new HashMap<TaxPayer,String>();
	private JPanel activePanel = null;
	private TaxPayer activePerson = null;
	private String activeFile = null;
	
	Window(){super("Tax Calculator");}
	
	public void makeGui() throws IOException{
		setSize(500,700);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		addAllButtons();
		addImage();
		addTabbedPane();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void addAllButtons() throws IOException
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		JMenuItem buttonCloseTab = new JMenuItem("Close Tab");
		menu.add(buttonCloseTab);
		createCloseTabAction(buttonCloseTab);
		JMenuItem buttonCloseApplication = new JMenuItem("Close Application");
		menu.add(buttonCloseApplication);
		closeApplication(buttonCloseApplication);
		JMenu taxPayers = new JMenu("Tax Payers");
		menuBar.add(taxPayers);
		JMenuItem buttonAddTaxpayer = new JMenuItem("Add TaxPayer");
		taxPayers.add(buttonAddTaxpayer);
		addPerson(buttonAddTaxpayer);
		JMenuItem buttonLoadTaxpayer = new JMenuItem("Load TaxPayer");
		taxPayers.add(buttonLoadTaxpayer);
		loadPerson(buttonLoadTaxpayer);
		JMenuItem buttonDeleteTaxpayer = new JMenuItem("Delete TaxPayer");
		taxPayers.add(buttonDeleteTaxpayer);
		deletePerson(buttonDeleteTaxpayer);
		JMenu receipts = new JMenu("Receipts");
		menuBar.add(receipts);
		JMenuItem buttonAddReceipt = new JMenuItem("Add Receipt");
		receipts.add(buttonAddReceipt);
		addReceipt(buttonAddReceipt);
		JMenuItem buttonDeleteReceipt = new JMenuItem("Delete Receipt");
		receipts.add(buttonDeleteReceipt);
		deleteReceipt(buttonDeleteReceipt);
		JMenu charts = new JMenu("Charts");
		menuBar.add(charts);
		JMenuItem buttonShowBarchart = new JMenuItem("Show BarChart");
		charts.add(buttonShowBarchart);
		createBarChart(buttonShowBarchart);
		JMenuItem buttonShowPiechart = new JMenuItem("Show PieChart");
		charts.add(buttonShowPiechart);
		createPieChart(buttonShowPiechart);
		JMenu log = new JMenu("Create Log");
		menuBar.add(log);
		JMenuItem txtButton = new JMenuItem("TXT Format");
		log.add(txtButton);
		createTxtLog(txtButton);
		JMenuItem xmlButton = new JMenuItem("XML Format");
		log.add(xmlButton);
		createXmlLog(xmlButton);
	}
	
	public void addImage(){
		imageFlag=true;
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0,0));
		BufferedImage myPicture = null;
		try {myPicture = ImageIO.read(new File("tax.png"));} 
		catch (IOException e){e.printStackTrace();}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		panel.add(picLabel);
		getContentPane().add(panel);
	}
	
	public void addTabbedPane(){
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabPlacement(tabbedPane.BOTTOM);
		getContentPane().add(tabbedPane);
	}
	
	public void createCloseTabAction(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(tabbedPane.getTabCount() != 0){
					int input = JOptionPane.showOptionDialog(null, "Are you sure you want to close this tab?",
							"Close "+activePerson.getName()+ " tab", JOptionPane.OK_CANCEL_OPTION, 1, null, null, null);
					if(input == 0){deleteTab(-1);}
				}
			}
		});
	}
	
	public void defineActives(){
		if(tabbedPane.getTabCount()==0){
			activePanel = null;
			activePerson = null;
			activeFile = null;
			return;
		}
		activePanel = shownTabs.get(tabbedPane.getSelectedIndex());
		activePerson = workspace.getTaxPayer(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
		activeFile = allOpenedFiles.get(activePerson);	
	}
	
	public void deleteTab(int i){
		if(tabbedPane.getTabCount() != 0 && i==-1){tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());}
		if(i!=-1){tabbedPane.removeTabAt(i);}
		if(tabbedPane.getTabCount() == 0){
			getContentPane().remove(0);
			addImage();
			addTabbedPane();
		}
		shownPeople.remove(activePerson);
	}
	
	public void closeApplication(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int input = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?",
						"Close Application", JOptionPane.OK_CANCEL_OPTION, 1, null, null, null);
				if(input == 0){System.exit(0);}
			}
		});
	}
	
	public void addPerson(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = chooseFile();
				if(chooser.showOpenDialog(new JPanel(new GridLayout(1,1))) != JFileChooser.APPROVE_OPTION){return;}
				if(checkIfAvailableAndShow(chooser)==true){return;}
				try {addToLists(chooser);}
				catch (IOException e1) {e1.printStackTrace();}
			}
		});
	}
	
	public JFileChooser chooseFile()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files","txt");
		chooser.setFileFilter(filter);
		return chooser;
	}
	
	public boolean checkIfAvailableAndShow(JFileChooser chooser){
		for (Map.Entry<TaxPayer, String> entry : allOpenedFiles.entrySet()) {
			if(checkinList(entry.getValue(),chooser) && shownPeople.contains(entry.getKey())){
				tabbedPane.setSelectedIndex(shownPeople.indexOf(entry.getKey()));
				return true;
			}
			if(checkinList(entry.getValue(),chooser) && passivePeople.contains(entry.getKey())){
				return makeActive(entry.getKey());
			}
		}
		return false;
	}
	
	public boolean checkinList(String value, JFileChooser chooser){
		if(value.equals(chooser.getSelectedFile().getPath()) && tabbedPane.getTabCount() != 0){
			return true;
		}
		return false;
	}
	
	public boolean makeActive(TaxPayer person){
		activePerson = person;
		activePanel = showInformation();
		addTab(tabbedPane.getTabCount());
		return true;
	}
	
	public JPanel showInformation(){
		JPanel panelCanvas = new JPanel();
		JPanel panelInformation = new JPanel();
		panelCanvas.add(panelInformation, BorderLayout.CENTER);
		panelInformation.setLayout(new BoxLayout(panelInformation, BoxLayout.Y_AXIS));
		panelInformation.add(setATitle("TAXPAYER"));
		panelInformation.add(showPerson());
		panelInformation.add(setATitle("RECEIPTS"));
		for(int i=0; i < activePerson.getReceiptCount(); i++){
			panelInformation.add(showReceipt(i));
		}
		return panelCanvas;
	}
	
	public JLabel setATitle(String title){
		JLabel text = new JLabel("<html><br>-----"+title+"-----<br><br></html>"); 
		text.setFont(new Font("Times New Roman", Font.BOLD, 20));
		return text;
	}
	
	public JLabel showPerson(){
		JLabel text = new JLabel("<html>NAME: "+ activePerson.getName()+
				"<br>AFM: "+ activePerson.getAFM()+"<br>STATUS: "+ activePerson.getType()+
				"<br>INCOME: "+ activePerson.getIncome()+"<br>BASIC TAX: "+ activePerson.getBasicTax()+
				"<br>ACTUAL TAX: "+ activePerson.getTax()+"<br><br></html>",SwingConstants.LEFT);
		text.setFont(new Font("Times New Roman", Font.BOLD, 16));
		return text;
	}
	
	public JLabel showReceipt(int i){
		JLabel text = new JLabel("<html>RECEIPT ID: "+activePerson.getReceiptCode(i)+
				"<br>DATE: "+activePerson.getReceiptDate(i)+ "<br>KIND: "+activePerson.getReceiptKind(i)+
				"<br>AMOUNT: "+activePerson.getReceiptAmount(i)+"<br>COMPANY: "+activePerson.getReceiptCompany(i)+
				"<br>COUNTRY: "+activePerson.getReceiptCountry(i)+"<br>CITY: "+activePerson.getReceiptCity(i)+
				"<br>STREET: "+activePerson.getReceiptStreet(i)+"<br>STREET NUMBER: "+activePerson.getReceiptNumber(i)+
				"<br><br></html>", SwingConstants.LEFT);			
		text.setFont(new Font("Times New Roman", Font.BOLD, 16));
		return text;
	}
	
	public void addTab(int position){
		if(imageFlag==true){
			getContentPane().remove(0);
			imageFlag=false;
		}
		JScrollPane scrollPane = new JScrollPane(activePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tabbedPane.insertTab(activePerson.getName(), null, scrollPane, null,position);
		tabbedPane.setSelectedIndex(position);
	}
	
	public void addToLists(JFileChooser chooser) throws IOException{
		activeFile = chooser.getSelectedFile().getPath();
		if (extractInformation(new FileTypeIdentifier(chooser.getSelectedFile().getPath())) == false){
			JOptionPane.showMessageDialog(null, "Please correct the file or choose a new Taxpayer.",
					"CONFLICTING IDs!", 2, null);
			return;
		}
		allOpenedFiles.put(activePerson,activeFile);
		activePanel = showInformation();
		shownTabs.add(activePanel);
		addTab(tabbedPane.getTabCount());
	}
	
	public boolean extractInformation(FileTypeIdentifier unknownFile) throws IOException{
		FileParser file = unknownFile.recogniseFile();
		file.readFile(); 
		makeTaxPayer(file.getPersonInformation());
		return checkIfValid(file.getReceiptAndCompanyList());
	}
	
	public boolean checkIfValid(HashMap<String[],String[]> receipts){
		for (Entry<String[],String[]> entry  : receipts.entrySet()) {
			if(activePerson.checkReceipt(entry.getKey(),entry.getValue()) == false){
				workspace.removeTaxPayer(activePerson);
				shownPeople.remove(activePerson);
				passivePeople.remove(activePerson);
				allOpenedFiles.remove(activeFile);
				defineActives();
				return false;
			}
		}
		return true;
	}
	
	public void makeTaxPayer(String[] information){
		workspace.addTaxPayer(information);
		activePerson = workspace.getTaxPayer(information[0]);
		shownPeople.add(workspace.getTaxPayer(information[0]));
		passivePeople.add(workspace.getTaxPayer(information[0]));
	}
	
	public void loadPerson(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Object selection = findAndSelectPerson("Load");
				if(selection == null){return;}
			    findPersonInList(selection);
			}
		});
	}
	
	public Object findAndSelectPerson(String action){
		String[] names = new String[workspace.getTaxPayerCount()];
		for(int i=0;i<workspace.getTaxPayerCount();i++){
			names[i] = workspace.getTaxPayerName(i);
		}
		Object selected = JOptionPane.showInputDialog(null,"Choose an existing Tax Payer", action +" Tax Payer",
				JOptionPane.DEFAULT_OPTION, null, names, "0");
		return selected;
	}
	
	public void findPersonInList(Object selection){
		for(int i=0;i<workspace.getTaxPayerCount();i++){
    		if(i<tabbedPane.getTabCount() && selection.toString().equals(tabbedPane.getTitleAt(i))){
				tabbedPane.setSelectedIndex(i);
				return;
			}
			if(i>=tabbedPane.getTabCount() && passivePeople.contains(workspace.getTaxPayer(selection.toString()))){
				makeActive(workspace.getTaxPayer(selection.toString()));
				return;
			}
		}
	}
	
	public void deletePerson(JMenuItem button) throws IOException{
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Object selection = findAndSelectPerson("Delete");
				if (selection == null){ return;}
			    for(int i=0;i<workspace.getTaxPayerCount();i++){
		    		if(i<tabbedPane.getTabCount() && selection.toString().equals(tabbedPane.getTitleAt(i))){deleteTab(i);}
					if(removeFromLists(selection,i)==true){return;}
				}
			}
		});
	}
	
	public boolean removeFromLists(Object selection,int i){
		if(workspace.getTaxPayer(selection.toString()).equals(passivePeople.get(i))
				&& shownPeople.contains(workspace.getTaxPayer(selection.toString()))){
			shownPeople.remove(workspace.getTaxPayer(selection.toString()));
			shownTabs.remove(i);
		}
		setActive(selection,i);
		return checkIfActuallyInLists(selection,i);
	}
	
	public void setActive(Object selection,int i){
		if(workspace.getTaxPayer(selection.toString()).equals(passivePeople.get(i))
				&& workspace.getTaxPayer(selection.toString()).equals(activePerson) && shownPeople.size()>0){
			activePerson = shownPeople.get(0);
			activePanel = shownTabs.get(0);
		}
		if(workspace.getTaxPayer(selection.toString()).equals(passivePeople.get(i))
				&& workspace.getTaxPayer(selection.toString()).equals(activePerson) && shownPeople.size()<=0){
			activePerson = null;
			activePanel = null;
		}
	}
	
	public boolean checkIfActuallyInLists(Object selection,int i)
	{
		if(workspace.getTaxPayer(selection.toString()).equals(passivePeople.get(i))){
			passivePeople.remove(i);
			allOpenedFiles.remove(workspace.getTaxPayer(selection.toString()));
			workspace.removeTaxPayer(workspace.getTaxPayer(selection.toString()));
			return true;
		}
		return false;
	}
	
	String[] receipt = new String[4];
	String[] company = new String[5];
	JTextField[] information = new JTextField[9];
	
	public void addReceipt(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(activePerson == null || JOptionPane.showOptionDialog(null, showMessage(),"New Receipt for " +
				activePerson.getName(), JOptionPane.OK_CANCEL_OPTION, 1, null, null, null)!= 0 ){return;}
				fillArray();
				if(checkIfValid()){
					updateFileAdd();
					updateScreen();
				}
			}
		});
	}
	
	public JPanel showMessage(){
		String[] Labels={"Receipt ID : ","Date :","Kind :",
				"Amount :","Company :","Country :","City :","Street :","Street Number :"};
		JPanel newReceipt = new JPanel(new GridLayout(9,1));
		for(int i =0;i<9;i++){
			JLabel newLabel = new JLabel(Labels[i]);
			newReceipt.add(newLabel);
			information[i]=new JTextField(10);
			newReceipt.add(information[i]);
		}
		return newReceipt;
	}
	
	public void fillArray()
	{
		for(int i=0;i<4;i++){
			receipt[i]=information[i].getText();
			company[i]=information[i+4].getText();
		}
		company[4] = information[8].getText();	
	}
	
	public boolean checkIfValid(){
		try{
			if (activePerson.checkReceipt(receipt,company) == false){
				JOptionPane.showMessageDialog(null,"Please retype the receipt.", "CONFLICTING IDs!", 2, null);
				return false;
			}
		}
		catch (Exception exceptionFlag){
			JOptionPane.showMessageDialog(null,"Please retype the receipt.", "CONFLICTING AMOUNT!", 2, null);
			return false;
		}
		return true;
	}
	
	public void updateFileAdd(){
		OutDataFactory unknownFile = new OutDataFactory();
		try {
			FileEditor file = unknownFile.openFile(activeFile);
			file.addReceipt(receipt,company);
		}
		catch (IOException e1){
			e1.printStackTrace();
		}
	}
	
	public void updateScreen(){
		for(int i=0;i<shownTabs.size();i++){
			if(activePanel.equals(shownTabs.get(i))){
				int position = tabbedPane.getSelectedIndex();
				activePanel = showInformation();
				shownTabs.remove(i);
				shownTabs.add(i, activePanel);
				tabbedPane.remove(tabbedPane.getSelectedIndex());
				addTab(position);
				return;
			}
		}
	}
	
	public void deleteReceipt(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(activePerson!=null){
					Object selected = showRemoveList();
					if(selected==null){return;}
					updateFileRemove(selected);
					updateScreen();
				}
			}
		});
	}
	
	public Object showRemoveList(){
		String[] ids = new String[activePerson.getReceiptCount()];
		for(int i=0;i<activePerson.getReceiptCount();i++){
			ids[i] = activePerson.getReceipt(i).getCode();
		}
		Object selected = JOptionPane.showInputDialog(null,
				"Choose a Receipt", "Delete a(n) "+activePerson.getName()+" receipt",
				JOptionPane.DEFAULT_OPTION, null, ids, "0");
		return selected;
	}
	
	public void updateFileRemove(Object selected){
		activePerson.removeReceipt(selected.toString());
		OutDataFactory unknownFile = new OutDataFactory();
		try {
			FileEditor file = unknownFile.openFile(activeFile);
			file.deleteReceipt(selected.toString(),activeFile);
		}
		catch (IOException e1){e1.printStackTrace();}
	}
	
	public void createBarChart(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(activePerson == null){return;}
				BarChart chart = new BarChart(activePerson);
				chart.pack( );
			    RefineryUtilities.centerFrameOnScreen( chart );
				chart.setVisible( true );   
			}
		});
	}
	
	public void createPieChart(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(activePerson == null){return;}
				PieChart chart = new PieChart(activePerson); 
				chart.pack( );
				RefineryUtilities.centerFrameOnScreen( chart );
				chart.setVisible( true );
			}
		});
	}
	
	public void createTxtLog(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(activePerson != null){makeFile("txt");}
			}
		});
	}
	
	public void makeFile(String type){
		OutDataFactory factory = new OutDataFactory();
		try {
			Log file = factory.prepareFile(type);
			file.setPersonInformation(activePerson.getBasicInformation());
			file.setReceiptInformation(activePerson.getReceiptInformation());
			file.createLog(activeFile.substring(0, activeFile.length()-8));
		}
		catch (IOException e1) {e1.printStackTrace();}
	}
	
	public void createXmlLog(JMenuItem button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				defineActives();
				if(activePerson != null){makeFile("xml");}
			}
		});
	}
}