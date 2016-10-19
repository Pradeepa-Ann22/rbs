package com.pr.rbs.rbs_core;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pr.rbs.rbs_core.models.Customer;
import com.pr.rbs.rbs_core.repository.CustomerRepository;
import com.pr.rbs.rbs_core.service.DataService;


/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class App implements CommandLineRunner
{
	
		
	   @Inject
	   private CustomerRepository cr;
		  
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   public JPanel controlPanel;
	   private JMenuBar menuBar;
	   
	   //Borders
	   

		Border blackline = BorderFactory.createLineBorder(Color.black);
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border empty = BorderFactory.createEmptyBorder();
	   
	   
	   public App(){
		      
		   }
	   
    private void prepareGUI() {

    	mainFrame = new JFrame("Retail Billing Solution");
        mainFrame.setSize(800,600);
        mainFrame.setLayout(new GridLayout(3, 1));
        
        menuBar = new JMenuBar();
        menuBar.setName("PAV");
        menuBar.setVisible(true);
        
       
        mainFrame.setJMenuBar(menuBar);
        
        headerLabel = new JLabel("Header",JLabel.CENTER );
        statusLabel = new JLabel("Status",JLabel.CENTER);        

        statusLabel.setSize(350,100);
        
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
   	        System.exit(0);
            }        
         });    

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        
        
        	headerLabel.setText("Control in action: Button"); 

	      JButton okButton = new JButton("OK");
	      JButton submitButton = new JButton("Submit");
	      JButton cancelButton = new JButton("Cancel");

	      okButton.setActionCommand("OK");
	      submitButton.setActionCommand("Submit");
	      cancelButton.setActionCommand("Cancel");

	      okButton.addActionListener(new ButtonClickListener()); 
	      submitButton.addActionListener(new ButtonClickListener()); 
	      cancelButton.addActionListener(new ButtonClickListener()); 

	      controlPanel.add(okButton);
	      controlPanel.add(submitButton);
	      controlPanel.add(cancelButton);   
	      
	      
	      
        mainFrame.setVisible(true); 

		
	}

	public static void main( String[] args )
    {
		App s=new App();
		s.prepareGUI();
    	/// DB
		// ConfigurableApplicationContext ctx= SpringApplication.run(App.class,args);
		// ConfigurableApplicationContext context = 
		//new SpringApplicationBuilder(App.class).headless(false).run(args);
		   
		 //App mainObj = context.getBean(App.class);
		 
		 /*
		 String[] beanNames = context.getBeanDefinitionNames();
	        Arrays.sort(beanNames);
	        for (String beanName : beanNames) {
	            System.out.println(beanName);
	        }
		*/
    	
    	ResultSet rs=null;
    	try {
			rs=DataService.getResultSet("select id,name,price from rbs.item");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			while(rs.next())
			{
			System.out.println("===== ITEM  =====");
			System.out.println("ID : "+rs.getLong(1));
			System.out.println("NAME : "+rs.getString(2));
			System.out.println("PRICE : "+rs.getDouble(3));
			 JButton jButton = new JButton(rs.getString(2));
			 jButton.setActionCommand("SELECT");
			 jButton.addActionListener(new App().new ButtonClickListener());
			 s.controlPanel.add(jButton);
			}
			s.controlPanel.repaint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	/// SWING
    	// new App();
    	
    	
    }
	

	   private class ButtonClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();
	         JButton source=(JButton) e.getSource();
	         if( command.equals( "OK" ))  {
	            statusLabel.setText("Ok Button clicked.");
	         }
	         else if( command.equals( "Submit" ) )  {
	            statusLabel.setText("Submit Button clicked."); 
	         }
	         else if( command.equals( "SELECT" ) )  {
		            statusLabel.setText(source.getName()+" Button clicked."); 
		         }
	         else  {
	            statusLabel.setText("Cancel Button clicked.");
	         }  	
	      }		
	   }


	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		//prepareGUI();
		System.out.println("======================== JPA ===================");
		Customer c1=new Customer("Prasanth","Mathew",9789969613L);
		Customer c2=new Customer("Varghese","Mathew",9840440360L);
		Customer c3=new Customer("Pradeepa","Ann",9884676886L);
		java.util.List<Customer> records=new ArrayList<Customer>();
		records.add(c1);
		records.add(c2);
		records.add(c3);
		cr.save(records);
		System.out.println("===========================");
		System.out.println(cr.findByMobile(9789969613L));
		System.out.println("===========================");
		System.out.println(cr.findOne(9884676886L));
		System.out.println(cr.findAll());
		System.out.println("===========DONE================");
		
	}
	   
	   
}
