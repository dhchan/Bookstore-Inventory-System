//SWING IMPORTS
import java.awt.EventQueue;
import javax.swing.JFrame;

//import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//INVENTORY IMPORTS
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
//import javax.swing.table.DefaultTableModel;

//import java.awt.Color;
import java.awt.Font;

//SQL IMPORTS
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
public class SwingView {

	public static boolean end = false;
	private JFrame frame;
	private JTextArea textArea;
	private JLabel results;
	private JButton continue3;
	private JRadioButton addButton;
	private JRadioButton editButton;
	private JRadioButton deleteButton;
	private ButtonGroup aedGroup;
	private JTextField titleField;
	private JTextField numberField;
	private JLabel lblTitle;
	private JLabel lblNumber;
	private JButton continue2;
	private JRadioButton bookButton;
	private JRadioButton cdButton;
	private JRadioButton dvdButton;
	private ButtonGroup bcdGroup;
	private JLabel welcome;
	private JRadioButton loadButton;
	private JRadioButton newButton;
	private ButtonGroup nlGroup;
	private JButton continue1;
	private JButton continue4;
	
	static Statement statement;
	
	private String title;
	private String number;
	private String iSBN;
	private String INSERT_QUERY;
	private String UPDATE_QUERY;
	private String DELETE_QUERY;
	
	public static InventoryController bookController, cdController, dvdController;
	private JTextField iSBNField;
	private JLabel lblISBN;
	private JTable table;
	
	static String host = "jdbc:mysql://localhost:3306/test";
	static String username = "root";
	static String password = "root";
	private JScrollPane scrollPane;
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ENUMERATION use.
		enum Product
		{
		  BOOK, cdButton, DVD
		}

		//INTERFACE use.
		interface Persistence {

			public void saveProperties( Properties prop, File file );
			public void loadProperties( Properties prop, File file );
			
		}
		
		//Inventory class has four properties.
		static Properties books = new Properties();
		static Properties cds = new Properties();
		static Properties dvds = new Properties();
		static Properties generic = new Properties();
		
		//Links books properties to appropriate InventoryModel
		private static InventoryModel retrieveBook()
		{
		      InventoryModel bookList = new InventoryModel();
		      bookList.setPropertyName(books);
		      return bookList;
		}
		
		//Links CD properties to appropriate InventoryModel
		private static InventoryModel retrieveCD()
		{
		      InventoryModel cdList = new InventoryModel();
		      cdList.setPropertyName(cds);
		      return cdList;
		}
		
		//Links DVD properties to appropriate InventoryModel
		private static InventoryModel retrieveDVD()
		{
		      InventoryModel dvdList = new InventoryModel();
		      dvdList.setPropertyName(dvds);
		      return dvdList;
		}
		
		//Declares three files to save and load properties from.
		static File file1 = new File("bookInventory.dat");
		static File file2 = new File("cdInventory.dat");
		static File file3 = new File("dvdInventory.dat");
		
		//Method to save properties to file. 
		public static void saveProperties( Properties prop, File file )
		{
			try
			{
				FileOutputStream output = new FileOutputStream( file );
				prop.store( output, "Properties in Inventory" ); 
				output.close();
			}
			catch ( IOException ioException )
			{
				ioException.printStackTrace();
			}
		}
		
		//Method to load properties from a file.
		public static void loadProperties( Properties prop, File file )
		{
			try
			{
				FileInputStream input = new FileInputStream( file );
				prop.load( input ); // load properties
				input.close();
			}
			catch ( IOException ioException )
			{
				ioException.printStackTrace();
			}
		}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		InventoryModel bookList = retrieveBook();
		InventoryView bookView = new InventoryView();
		bookController = new InventoryController(bookList, bookView);
		
		InventoryModel cdList = retrieveCD();
		InventoryView cdView = new InventoryView();
		cdController = new InventoryController(cdList, cdView);
		
		InventoryModel dvdList = retrieveDVD();
		InventoryView dvdView = new InventoryView();
		dvdController = new InventoryController(dvdList, dvdView);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Connects to SQL Database
		try {
			Connection connection = DriverManager.getConnection(host, username, password);
			statement = connection.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingView window = new SwingView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	/////////////////////Create Swing Elements/////////////////////
	private void setFrame2()
	{
		frame.remove(newButton);
        frame.remove(welcome);
        frame.remove(loadButton);
        frame.remove(continue1);
        
        frame.remove(results);
		frame.remove(textArea);
		frame.remove(continue3);
		frame.remove(continue4);
		frame.remove(scrollPane);
        
        frame.getContentPane().add(bookButton);
        frame.getContentPane().add(cdButton);
        frame.getContentPane().add(dvdButton);
        frame.getContentPane().add(addButton);
        frame.getContentPane().add(editButton);
        frame.getContentPane().add(deleteButton);
        frame.getContentPane().add(lblTitle);
        frame.getContentPane().add(lblISBN);
        frame.getContentPane().add(lblNumber);
        numberField.setText("");
        titleField.setText("");
        iSBNField.setText("");
        frame.getContentPane().add(continue2);
        frame.getContentPane().add(numberField);
        frame.getContentPane().add(titleField);
        frame.getContentPane().add(iSBNField);
        
        frame.repaint();
	}
	
	private void setFrame3()
	{
		frame.remove(bookButton);
		frame.remove(cdButton);
		frame.remove(dvdButton);
		frame.remove(addButton);
		frame.remove(editButton);
		frame.remove(deleteButton);
		frame.remove(lblTitle);
		frame.remove(lblNumber);
		frame.remove(lblISBN);
		frame.remove(continue2);
		frame.remove(numberField);
		frame.remove(titleField);
		frame.remove(iSBNField);
		
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(results);
		frame.getContentPane().add(continue3);
		frame.getContentPane().add(continue4);
		frame.repaint();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textArea.setLineWrap(true);
		textArea.setBounds(47, 49, 356, 168);
		
		results = new JLabel("List:");
		results.setHorizontalAlignment(SwingConstants.CENTER);
		results.setBounds(111, 21, 228, 16);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 49, 375, 167);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		continue3 = new JButton("Continue...");
		continue3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setFrame2();
				
			}
		});
		continue3.setBounds(87, 229, 117, 29);
		
		continue4 = new JButton("Finished!");
		continue4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.remove(continue3);
				frame.remove(continue4);
				results.setText("Total Inventory:");
				
				textArea.setText("Books in Inventory:\n" + bookController.updateView() + 
			        			"\nCDs in Inventory:\n" + cdController.updateView() +
			        			"\nDVDs in Inventory:\n" + dvdController.updateView());
				frame.repaint();
				saveProperties(books, file1);
				saveProperties(cds, file2);
				saveProperties(dvds, file3);
			}
		});
		continue4.setBounds(241, 229, 117, 29);
		
		addButton = new JRadioButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addButton.isSelected()) {
			        lblNumber.setEnabled(true);
			        numberField.setEnabled(true);  
			        lblISBN.setEnabled(true);
			        iSBNField.setEnabled(true);
			        frame.repaint();
			    }
			}
		});
		addButton.setBounds(89, 20, 141, 23);
		
		editButton = new JRadioButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (editButton.isSelected()) {
			        lblNumber.setEnabled(true);
			        numberField.setEnabled(true);  
			        lblISBN.setEnabled(false);
			        iSBNField.setEnabled(false);
			        iSBNField.setText("");
			        frame.repaint();
			    }
			}
		});
		editButton.setBounds(89, 41, 141, 23);
		
		deleteButton = new JRadioButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (deleteButton.isSelected()) {
			        lblNumber.setEnabled(false);
			        numberField.setText("");
			        numberField.setEnabled(false);
			        lblISBN.setEnabled(false);
			        iSBNField.setEnabled(false);
			        iSBNField.setText("");
			        frame.repaint();
			    }
			}
		});
		deleteButton.setBounds(89, 62, 141, 23);
		
		aedGroup = new ButtonGroup();
		aedGroup.add(addButton);
		aedGroup.add(editButton);
		aedGroup.add(deleteButton);
		
		titleField = new JTextField();
		titleField.setHorizontalAlignment(SwingConstants.LEFT);
		titleField.setBounds(161, 95, 233, 28);
		titleField.setColumns(10);
		
		numberField = new JTextField();
		numberField.setBounds(161, 123, 101, 28);
		numberField.setColumns(10);
		
		lblTitle = new JLabel("Title:");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setBounds(78, 101, 61, 16);
		
		lblNumber = new JLabel("Number:");
		lblNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumber.setBounds(78, 129, 61, 16);
		
		continue2 = new JButton("Continue");
		continue2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add or edit
				if (addButton.isSelected()) 
				{
					title = titleField.getText();
					number = numberField.getText();
					iSBN = iSBNField.getText();
					
					if (!title.isEmpty() && title != null && !number.isEmpty() && number != null && !iSBN.isEmpty() && iSBN != null)
					{
						if (bookButton.isSelected())
						{
							bookController.addItem(title, number);
							textArea.setText("Books in Inventory:\n" + bookController.updateView());
							saveProperties(books, file1);
							
							INSERT_QUERY = "INSERT INTO Inventory (ISBN, Title, Amount, Type)" + 
									"VALUES ('" + iSBN + "', '" + title + "', '" + number + "', '" + "book" + "')";
							try {
								statement.executeUpdate(INSERT_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (cdButton.isSelected())
						{
							cdController.addItem(title, number);
							textArea.setText("CDs in Inventory:\n" + cdController.updateView());
							saveProperties(cds, file2);
							
							INSERT_QUERY = "INSERT INTO Inventory (ISBN, Title, Amount, Type)" + 
									"VALUES ('" + iSBN + "', '" + title + "', '" + number + "', '" + "cd" + "')";
							try {
								statement.executeUpdate(INSERT_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (dvdButton.isSelected())
						{
							dvdController.addItem(title, number);
							textArea.setText("DVDs in Inventory:\n" + dvdController.updateView());
							saveProperties(dvds, file3);
							
							INSERT_QUERY = "INSERT INTO Inventory (ISBN, Title, Amount, Type)" + 
									"VALUES ('" + iSBN + "', '" + title + "', '" + number + "', '" + "dvd" + "')";
							try {
								statement.executeUpdate(INSERT_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						setFrame3();
					}
				}
				else if (editButton.isSelected()) 
				{
					title = titleField.getText();
					number = numberField.getText();
					iSBN = iSBNField.getText();
					
					if (!title.isEmpty() && title != null && !number.isEmpty() && number != null)
					{
						if (bookButton.isSelected())
						{
							bookController.addItem(title, number);
							textArea.setText("Books in Inventory:\n" + bookController.updateView());
							saveProperties(books, file1);
							
							UPDATE_QUERY = "UPDATE Inventory " + 
									"SET Amount = '" + number + "' WHERE Title = '" + title + "'";
							try {
								statement.executeUpdate(UPDATE_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (cdButton.isSelected())
						{
							cdController.addItem(title, number);
							textArea.setText("CDs in Inventory:\n" + cdController.updateView());
							saveProperties(cds, file2);
							
							INSERT_QUERY = "INSERT INTO Inventory (ISBN, Title, Amount, Type)" + 
									"VALUES ('" + iSBN + "', '" + title + "', '" + number + "', '" + "dvd" + "')";
							try {
								statement.executeUpdate(INSERT_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (dvdButton.isSelected())
						{
							dvdController.addItem(title, number);
							textArea.setText("DVDs in Inventory:\n" + dvdController.updateView());
							saveProperties(dvds, file3);
							
							INSERT_QUERY = "INSERT INTO Inventory (ISBN, Title, Amount, Type)" + 
									"VALUES ('" + iSBN + "', '" + title + "', '" + number + "', '" + "dvd" + "')";
							try {
								statement.executeUpdate(INSERT_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						setFrame3();
					}
				}
				else if (deleteButton.isSelected())
				{
					title = titleField.getText();
					if (!title.isEmpty() && title != null)
					{
						if (bookButton.isSelected())
						{
							bookController.removeItem(title);
							textArea.setText("Books in Inventory:\n" + bookController.updateView());
							saveProperties(books, file1);
							
							DELETE_QUERY = "DELETE FROM Inventory " + 
									"WHERE Title = '" + title + "'";
							try {
								statement.executeUpdate(DELETE_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (cdButton.isSelected())
						{
							cdController.removeItem(title);
							textArea.setText("CDs in Inventory:\n" + cdController.updateView());
							saveProperties(cds, file2);
							
							DELETE_QUERY = "DELETE FROM Inventory " + 
									"WHERE Title = '" + title + "'";
							try {
								statement.executeUpdate(DELETE_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (dvdButton.isSelected())
						{
							dvdController.removeItem(title);
							textArea.setText("DVDs in Inventory:\n" + dvdController.updateView());
							saveProperties(dvds, file3);
							
							DELETE_QUERY = "DELETE FROM Inventory " + 
									"WHERE Title = '" + title + "'";
							try {
								statement.executeUpdate(DELETE_QUERY);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						setFrame3();
					}
				}
				//Set jtable text
				String SELECT_QUERY = "SELECT * FROM Inventory";
				Connection connection;
				try {
					connection = DriverManager.getConnection(host, username, password);
					PreparedStatement pst = connection.prepareStatement(SELECT_QUERY);
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		continue2.setBounds(166, 200, 117, 29);
		
		bookButton = new JRadioButton("Book");
		bookButton.setBounds(232, 6, 141, 23);
		
		cdButton = new JRadioButton("CD");
		cdButton.setBounds(232, 29, 141, 23);
		
		dvdButton = new JRadioButton("DVD");
		dvdButton.setBounds(232, 54, 141, 23);
		
		bcdGroup = new ButtonGroup();
		bcdGroup.add(bookButton);
		bcdGroup.add(cdButton);
		bcdGroup.add(dvdButton);
		
		iSBNField = new JTextField();
		iSBNField.setBounds(161, 152, 134, 28);
		iSBNField.setColumns(10);
		
		lblISBN = new JLabel("ISBN:");
		lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblISBN.setBounds(78, 158, 61, 16);
		
		/////////////////Swing Elements that are present from the start/////////////////
		
		welcome = new JLabel("Welcome. Load or create new inventory?");
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setBounds(61, 47, 321, 54);
		frame.getContentPane().add(welcome);
		
		loadButton = new JRadioButton("Load");
		loadButton.setBounds(154, 109, 141, 23);
		frame.getContentPane().add(loadButton);
		
		newButton = new JRadioButton("New");
		newButton.setBounds(154, 133, 141, 23);
		frame.getContentPane().add(newButton);
		
		nlGroup = new ButtonGroup();
		nlGroup.add(newButton);
		nlGroup.add(loadButton);
		
		
		continue1 = new JButton("Continue");
		continue1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (newButton.isSelected()) {
					
					saveProperties(books, file1);
					saveProperties(cds, file2);
					saveProperties(dvds, file3);
					
					DELETE_QUERY = "DELETE FROM Inventory";
					System.out.println(DELETE_QUERY);
					try {
						statement.executeUpdate(DELETE_QUERY);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					setFrame2();
			    }
				else if (loadButton.isSelected()){
					
					loadProperties(books, file1);
					loadProperties(cds, file2);
					loadProperties(dvds, file3);
			        
			        setFrame2();
				}
			}
		});
		continue1.setBounds(144, 168, 117, 29);
		frame.getContentPane().add(continue1);
	}
}
