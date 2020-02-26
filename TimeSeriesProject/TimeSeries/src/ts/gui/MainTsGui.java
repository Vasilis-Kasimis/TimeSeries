package ts.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ts.commons.TimeLine;
import ts.engine.EngineFactory;
import ts.engine.IEngine;
import ts.engine.MainEngine;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import java.awt.Window.Type;

public class MainTsGui {

	private JFrame frmTsSegmentator;
	private File file;
	private IEngine engine;
	private EngineFactory engineFactory;
	private String generatorFormat;
	private String str;
	private ArrayList<String> labels = new ArrayList<String>();
	private double maxError = 1;
	private JTextField errorField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainTsGui window = new MainTsGui();
					window.frmTsSegmentator.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainTsGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JRadioButton rdbtnTopdown = new JRadioButton("TopDown");
		JRadioButton rdbtnBottomup = new JRadioButton("BottomUp");
		
		frmTsSegmentator = new JFrame();
		frmTsSegmentator.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		frmTsSegmentator.setTitle("T/S Segmentator");
		frmTsSegmentator.setBounds(100, 100, 699, 550);
		frmTsSegmentator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		errorField = new JTextField();
		errorField.setText("Insert max error");
		errorField.setBounds(358, 85, 116, 22);
		frmTsSegmentator.getContentPane().add(errorField);
		errorField.setColumns(10);
		
		DefaultListModel defaultList = new DefaultListModel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 86, 175, 358);
		frmTsSegmentator.getContentPane().add(scrollPane);
		
		JList list = new JList(defaultList);
		scrollPane.setViewportView(list);
		defaultList.addElement("No timeseries");
		
		JList listd = new JList(defaultList);
		JMenuBar menuBar = new JMenuBar();
		frmTsSegmentator.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoadData = new JMenuItem("Load Data");
		mntmLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					openFile();
					engineFactory = new EngineFactory();
					engine = engineFactory.createMainEngine();
						try {
							engine.setTimeLine(str,generatorFormat);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<TimeLine> timelines = engine.getTimelines();
				defaultList.clear();
				for(int i = 0; i < timelines.size(); i++) {
					labels.add(timelines.get(i).getLabel());
					defaultList.addElement(timelines.get(i).getLabel());
				}
			}
		});
		mnFile.add(mntmLoadData);
		
		JLabel lblNewLabel = new JLabel("TimeSeries Segmentator Version 1.0\r\n");
		lblNewLabel.setBounds(298, 31, 298, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		frmTsSegmentator.getContentPane().setLayout(null);
		frmTsSegmentator.getContentPane().add(lblNewLabel);
		
		JLabel lblMaxerror = new JLabel("MaxError");
		lblMaxerror.setBounds(269, 86, 77, 20);
		frmTsSegmentator.getContentPane().add(lblMaxerror);
		
		JLabel lblChooseTimeseries = new JLabel("Choose timeseries");
		lblChooseTimeseries.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChooseTimeseries.setBounds(33, 31, 143, 19);
		frmTsSegmentator.getContentPane().add(lblChooseTimeseries);
		
		
		rdbtnTopdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnTopdown.isSelected())
					rdbtnBottomup.setSelected(false);
			}
		});
		rdbtnTopdown.setBounds(269, 153, 127, 25);
		rdbtnTopdown.setSelected(true);
		frmTsSegmentator.getContentPane().add(rdbtnTopdown);
		
		
		rdbtnBottomup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnBottomup.isSelected())
					rdbtnTopdown.setSelected(false);
			}
		});
		rdbtnBottomup.setBounds(422, 153, 127, 25);
		frmTsSegmentator.getContentPane().add(rdbtnBottomup);
		
		JLabel lblSegmentaitonAlgoritm = new JLabel("Segmentaiton Algoritm");
		lblSegmentaitonAlgoritm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSegmentaitonAlgoritm.setBounds(316, 120, 158, 24);
		frmTsSegmentator.getContentPane().add(lblSegmentaitonAlgoritm);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(labels.size() == 0) {
						JOptionPane.showMessageDialog(null, "Please load a time series");
					}
					else if(list.isSelectionEmpty()) {
						JOptionPane.showMessageDialog(null, "Please select a time series");
					}
					else
						engine.setVisualizer(list.getSelectedIndex());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnShow.setBounds(259, 253, 97, 25);
		frmTsSegmentator.getContentPane().add(btnShow);
		
		JButton btnSegment = new JButton("Segment");
		btnSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnBottomup.isSelected() == false && rdbtnTopdown.isSelected() == false) {
					JOptionPane.showMessageDialog(null, "Please select a segmentation algorithm");
				}
				else if(labels.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please load a time series");
				}
				else if(list.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select a time series");
				}
				else {
					if(errorField.getText().isEmpty() == false){
						try {
	
							maxError = Double.valueOf(errorField.getText());
						}
						catch(NumberFormatException e1) {
							maxError = 0.5;
						}
					}
					else {
						maxError = 0.5;
					}
					System.out.println("maxError = "+maxError);
					if(rdbtnBottomup.isSelected())
						engine.setSegmentator("BottomUp");
					else 
						engine.setSegmentator("TopDown");
					int pos = list.getSelectedIndex();
					
					try {
						TimeLine segmentedTimeLine = engine.segmentTimeline(pos, maxError);
						engine.setVisualizer(pos, segmentedTimeLine);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		btnSegment.setBounds(422, 253, 97, 25);
		frmTsSegmentator.getContentPane().add(btnSegment);
		
	}
	
	public boolean openFile() throws FileNotFoundException, IOException{
		JFileChooser filechooser = new JFileChooser();
		 
		filechooser.setCurrentDirectory(new File("."));
		int retval = filechooser.showOpenDialog(null);
		boolean open = false;
		
		
		if(retval == JFileChooser.APPROVE_OPTION){ 
			file= filechooser.getSelectedFile();
			
			str = file.toString();
			
			int i=0;
			
			for(String formatFile: str.split("\\.")){
				if(i==1){
					if((formatFile.equals("csv")) || (formatFile.equals("tsv")) || (formatFile.equals("xlsx"))) {
						if(formatFile.equals("tsv")){
							generatorFormat = "tsv";
						}
						if(formatFile.equals("csv")){
							generatorFormat = "csv";
						}
						if(formatFile.equals("xlsx")){
							generatorFormat = "xlsx";
						}
					}
					else {
							JOptionPane.showMessageDialog(null, "Please choose the correct format, .csv or .tsv or .xlsx");
					}
						return open = true;
							
						
					}
					i++;
			}
			if(i ==1){
				JOptionPane.showMessageDialog(null, "Error Opening File:Wrong Format \r\n Please choose the correct format, csv or .tsv or .xlsx");
			}
		}
		return open;
	}
}
