package xyz;

    import javax.swing.*;

	import javax.swing.event.*;

	import java.awt.*;

	import java.awt.event.*;

	import java.util.*;

	import java.io.*;

	 

	public class CarFrame extends JFrame
	
	{

	    public static JTabbedPane index;

	    public static CarLot myCarLot;

	     

	    public CarFrame()

	        {

	        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

	        this.setDefaultCloseOperation(3);

	        this.setTitle("Car Park System");

	        this.setResizable(false);

	        Color newColor = new Color(0.2f, 0.1f, 0.8f, 0.1f);

	        myCarLot = new CarLot(15, "carData.txt");

	        index = new JTabbedPane();

	        index.setBackground(newColor);

	        final JPanel statusTab = Status.startup();

	        final JPanel addOrRemoveCarTab = AddOrRemoveCar.startup();

	        index.addTab("Lot Status", statusTab);

	        index.addTab("Add Or Remove Cars", addOrRemoveCarTab);

	        this.getContentPane().add(index);

	    }

	 
	    public static void main(String[] args)

	    {

	        
	        CarFrame main = new CarFrame();

	        main.setVisible(true);

	    }

	}

	 

	class CarLot

	{

	    
	    private Vector registeredDrivers;

	    private Vector parkingStalls;

	    private String dataFile;

	    private int maxLotSize;

	     

	    

	    public CarLot(int maxSize, String fileName)

	    {

	        registeredDrivers = new Vector();

	        parkingStalls = new Vector();

	        maxLotSize = maxSize;

	        dataFile = fileName;

	        loadData();

	    }

	 

	    public String getDataFileName()

	    {

	        return dataFile;

	    }

	 

	    public int getMaxSize()

	    {

	        return maxLotSize;

	    }

	 

	    public int carCount()

	    {

	        return parkingStalls.size();

	    }

	 

	 

	    public String findStallLocation(String licenseNum)

	    {

	        String currentStall = "";

	        String returnVal = "";

	 

	        for(int i = 0; i < parkingStalls.size(); i++)

	        {

	            currentStall = (String)parkingStalls.elementAt(i);

	 

	            if(licenseNum.equals(currentStall))

	            {

	                returnVal = Integer.toString(i);

	                break;

	            }          

	        }

	 

	        return returnVal;

	    }

	 

	  
	    public int loadData()

	    {

	        
	        FileReader file;

	        BufferedReader buffer;

	        StringTokenizer tokens;

	        String currentLine = "";

	        String licensePlate = "";

	        String currentlyParked = "";

	         

	        try

	        {

	            file = new FileReader(dataFile);

	            buffer = new BufferedReader(file);

	            while((currentLine = buffer.readLine()) != null)

	            {

	                int returnVal = 0;

	                tokens = new StringTokenizer(currentLine, "|");

	                licensePlate = tokens.nextToken();

	                currentlyParked = tokens.nextToken();

	                registeredDrivers.addElement(licensePlate);

	                if(currentlyParked.equals("Y"))

	                {

	                   if(parkingStalls.size() < maxLotSize)

	                   {
	                     parkingStalls.addElement(licensePlate);

                   }

	                   else

	                   {

	                        returnVal = -1;

	                   }

	                }

	            }  

	        }

	        catch(FileNotFoundException f)

	        {

	            return -1;

	        }

	        catch(IOException io)

	        {

	            return -1;

	        }      

	         

	        return 0;

	    }

	 

	    public int saveData()

	    {

	        FileWriter writer = null;

	        PrintWriter printer = null;

	        String currentRecord = "";

	        String licensePlate = "";

	        String parkedPlates = "";

	        String currentlyParked = "";

	 

	        try

	        {

	            writer = new FileWriter(dataFile);

	            printer = new PrintWriter(writer);

	            for(int i = 0; i < registeredDrivers.size(); i++)

	            {

              licensePlate = (String)registeredDrivers.elementAt(i);

	              currentlyParked = "N";

	 

	              for(int j = 0; j < parkingStalls.size(); j++)

	              {

	                parkedPlates = (String)parkingStalls.elementAt(j);

	 

	                if(parkedPlates.equals(licensePlate))

	                {

	                  currentlyParked = "Y";

	                  break;

	                }

	              }

	 

	              currentRecord = licensePlate + "|" + currentlyParked;

	              printer.println(currentRecord);

	            }

	 

	            // close output streams

	            writer.close();

	            printer.close();

	        }

	        catch(IOException io)

	        {

	              return -1;

	        }  

	 

	        return 0;

	    }

	 

	    public boolean carEnter(String licenseNum){
            String parkedCar = "";

	        boolean alreadyHere = false;


	        for(int i = 0; i < parkingStalls.size(); i++)

	        {

	            parkedCar = (String)parkingStalls.elementAt(i);

	 

	            if(parkedCar.equals(licenseNum))

	            {

	                alreadyHere = true;

	            }          

	        }

	         

	        if(!alreadyHere)

	        {


	            if(!lotFull())

	            {

	                parkingStalls.addElement(licenseNum);

	                return true;

	            }

	           

	            else

	            {

	                return false;

	            }

	        }

	        
	        else

	        {

	            return false;

	        }

	    }

	 

	    public boolean carExit(String licenseNum)

	    {

	        boolean returnVal = false; 

	        String parkedCar = "";

	        for(int i = 0; i < parkingStalls.size(); i++)

	        {

	            parkedCar = (String)parkingStalls.elementAt(i);


	            if(parkedCar.equals(licenseNum))

	            {

	                parkingStalls.removeElementAt(i);

	                returnVal = true;

	                break;

	            }          

	        }

	 

	        return returnVal;

	    }
	 

	    public boolean lotFull()

	    {


	        if(parkingStalls.size() == maxLotSize)

	        {

	            return true;

	        }

	        else

	        {
	            return false;

	        }

	    }

	}

	 


	class Status

	{

	    public static JPanel statusTab = new JPanel();

	    public static JPanel statusScreen1;

	    static JTextField licensePlateField = new JTextField(20);

	    static JPanel startup()

	    {  

	        statusScreen1 = Status.getStatusScreen1();

	        statusTab.add(statusScreen1);

	 

	        statusScreen1.setVisible(true);

	 

	        return statusTab;

	 

	    }

	 

	    
	    static JPanel getStatusScreen1()

	        {  

	      statusScreen1 = new JPanel(new FlowLayout());

	          JPanel generalPanel = new JPanel();

	      generalPanel.setLayout(new BoxLayout(generalPanel,

	BoxLayout.Y_AXIS));

	      generalPanel.add(Box.createVerticalStrut(170));

	 

	      JPanel holderPanel = new JPanel(new BorderLayout());
      holderPanel.setLayout(new BoxLayout(holderPanel,

	BoxLayout.Y_AXIS));

	      JPanel criteriaPanel = new JPanel();

	      criteriaPanel.setLayout(new BoxLayout(criteriaPanel,

	BoxLayout.X_AXIS));

	      JLabel licensePlateLabel = new JLabel("License Plate Number:");

	 

	        Font textFont = new Font("SanSerif", Font.PLAIN, 24);

	        Font textFieldFont = new Font("Serif", Font.PLAIN, 20);

	 

	        licensePlateLabel.setFont(textFont);

	        licensePlateField.setFont(textFieldFont);

	 

	        criteriaPanel.add(Box.createHorizontalStrut(40));

	        criteriaPanel.add(licensePlateLabel);

	        criteriaPanel.add(licensePlateField);

	        criteriaPanel.add(Box.createHorizontalStrut(40));

	 

	        final JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new BoxLayout(buttonPanel,

	BoxLayout.X_AXIS));

	        JButton lotCapacityButton = new JButton("Check Lot Capacity");

	        JButton saveStateButton = new JButton("Save Lot State");

	        JButton findStallButton = new JButton("Locate Vehicle");

	 

	        JButton clearButton = new JButton(" Clear  ");

	 

	        lotCapacityButton.setFont(textFont);

	        saveStateButton.setFont(textFont);
	        findStallButton.setFont(textFont);
	        clearButton.setFont(textFont);

	 

	        buttonPanel.add(Box.createHorizontalStrut(10));

	        buttonPanel.add(lotCapacityButton);

	        buttonPanel.add(saveStateButton);

	        buttonPanel.add(findStallButton);

	        buttonPanel.add(clearButton);

	        holderPanel.add(criteriaPanel);

	        holderPanel.add(Box.createVerticalStrut(30));

	        holderPanel.add(buttonPanel);

	        generalPanel.add(holderPanel);

	        statusScreen1.add(generalPanel);

	        statusScreen1.add(Box.createHorizontalStrut(150));

	        lotCapacityButton.addActionListener(new ActionListener()

	        {

	            public void actionPerformed(ActionEvent e)
	                {  
           
	               String licensePlate = licensePlateField.getText().trim();

	 

	             int totalCapacity = CarFrame.myCarLot.getMaxSize();

	             int currentlyOccupied = CarFrame.myCarLot.carCount();

	             int freeSpace = totalCapacity - currentlyOccupied;

	             JOptionPane.showMessageDialog((Component) buttonPanel,

	                "Total Capacity: " + totalCapacity +

	                "\nCurrently Occupied: " + currentlyOccupied +

	                "\nFree Space: " + freeSpace,

	                "Current Car Lot Statistics",

	                                JOptionPane.INFORMATION_MESSAGE);

	                 

	              

	                CarFrame.index.setSelectedIndex(0);

	                licensePlateField.setText("");                     

	            }

	        });

	 

	        

	        saveStateButton.addActionListener(new ActionListener()

	        {

	            public void actionPerformed(ActionEvent e)

	            {  

	                

	                int result = CarFrame.myCarLot.saveData();
               

	                if(result == 0)

	                {

	            JOptionPane.showMessageDialog((Component) buttonPanel,

	        "Data for all registered users has been updated in file: " +

	            CarFrame.myCarLot.getDataFileName(),

	        "Data Stored Successfully", JOptionPane.INFORMATION_MESSAGE);

	                }

	                else

	                {

	            JOptionPane.showMessageDialog((Component) buttonPanel,

	                    "Data could not be stored!",

	                 "Data Extract Failure", JOptionPane.ERROR_MESSAGE);

	                }

	                CarFrame.index.setSelectedIndex(0);

	                licensePlateField.setText("");                     

	            }

	        });

	 


	        findStallButton.addActionListener(new ActionListener()

	        {

	            public void actionPerformed(ActionEvent e)

	            {  


	               String licensePlate = licensePlateField.getText().trim();

	     String stallNumber =

	CarFrame.myCarLot.findStallLocation(licensePlate);

	            if(!stallNumber.equals(""))

	            {

	             JOptionPane.showMessageDialog((Component) buttonPanel,

	                "Location of car #" + licensePlate + ":" +

	                "\nStall " + stallNumber,

	                "Car Location Found",

	                                JOptionPane.INFORMATION_MESSAGE);

	             }

	             else

	             {

	            JOptionPane.showMessageDialog((Component) buttonPanel, "Location of car #" + licensePlate + ":" + "Could not be found." +  "\nThe vehicle is either not registered or not currently parked. Car Location Found, stallNumber, JOptionPane.ERROR_MESSAGE");

	                }              

	          

	            CarFrame.index.setSelectedIndex(0);

	            licensePlateField.setText("");                     

	            }

	        });    

	        clearButton.addActionListener(new ActionListener()

	        {   public void actionPerformed(ActionEvent e)

	            {


	                licensePlateField.setText("");

	            }

	        });

	 

	        return statusScreen1;

	    }

	}


	class AddOrRemoveCar

	{

	    public static  JPanel addOrRemoveCarTab = new JPanel();

	    public static  JPanel addOrRemoveCarScreen1;

	 

	     static JTextField licensePlateField = new JTextField(20);

	 
	     static JPanel startup()

	    {    addOrRemoveCarScreen1 =

	AddOrRemoveCar.getAddOrRemoveCarScreen1();

	        addOrRemoveCarTab.add(addOrRemoveCarScreen1);

	 

	        addOrRemoveCarScreen1.setVisible(true);

	 

	        return addOrRemoveCarTab;

	    }

	 

	       static JPanel getAddOrRemoveCarScreen1()

	    {  

	        addOrRemoveCarScreen1 = new JPanel(new FlowLayout());

	        JPanel generalPanel = new JPanel();

	    generalPanel.setLayout(new BoxLayout(generalPanel,

	BoxLayout.Y_AXIS));

	        generalPanel.add(Box.createVerticalStrut(170));

	 

	        JPanel holderPanel = new JPanel(new BorderLayout());

	    holderPanel.setLayout(new BoxLayout(holderPanel, BoxLayout.X_AXIS));

	        JPanel criteriaPanel = new JPanel(new FlowLayout());

	                JLabel licensePlateLabel = new JLabel("License Plate Number:",

	                                                     

	SwingConstants.RIGHT);

	 

	        Font textFont = new Font("SanSerif", Font.PLAIN, 24);

	        Font textFieldFont = new Font("Serif", Font.PLAIN, 20);

	 

	        licensePlateLabel.setFont(textFont);

	        licensePlateField.setFont(textFieldFont);

	 

	        criteriaPanel.add(licensePlateLabel);

	        criteriaPanel.add(licensePlateField);

	 

	        final JPanel buttonPanel = new JPanel(new FlowLayout());

	    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	        JButton addButton = new JButton("Add Car to Lot");

	        JButton removeButton = new JButton("Remove Car from Lot");

	        JButton clearButton = new JButton("Clear Data");

	 
	        addButton.setFont(textFont);

	        removeButton.setFont(textFont);

	        clearButton.setFont(textFont);

	 

	        buttonPanel.add(Box.createHorizontalStrut(10));

	        buttonPanel.add(addButton);

	        buttonPanel.add(removeButton);

	        buttonPanel.add(clearButton);

	 
	        holderPanel.add(criteriaPanel);    

	        generalPanel.add(holderPanel);

	        holderPanel.add(Box.createVerticalStrut(75));

	        generalPanel.add(buttonPanel);

	        addOrRemoveCarScreen1.add(generalPanel);

	        addOrRemoveCarScreen1.add(Box.createHorizontalStrut(100));
	 

	        addButton.addActionListener(new ActionListener()

	        {

	            public void actionPerformed(ActionEvent e)

	            {  

	               String licensePlate = licensePlateField.getText().trim();


	                if((licensePlate.length() == 0))

	                {

	        JOptionPane.showMessageDialog((Component) buttonPanel,

	                             "Please fill in the field and try again",

	                            "Blank Field", JOptionPane.ERROR_MESSAGE);

	                }

	                else {

	       

	        boolean result = CarFrame.myCarLot.carEnter(licensePlate);

	                    if(!result)

	                    {

	        JOptionPane.showMessageDialog((Component) buttonPanel,

	    "This license plate is either not registered or is already in the lot. " +

	     "Please try again.",

	          "Invalid Operation", JOptionPane.ERROR_MESSAGE);

	                    }

	                    else

	                    {

	    int another = JOptionPane.showConfirmDialog((Component)

	buttonPanel,            "The car has been added. Add another car to the lot?",

	                "Add Car",

	            JOptionPane.YES_NO_OPTION);

	 


	        licensePlateField.setText("");


	        if(another == JOptionPane.NO_OPTION)

	        {

	                            CarFrame.index.setSelectedIndex(0);

	        }                      

	     }                 

	    }

	       }

	       });

	 

	  
	        removeButton.addActionListener(new ActionListener()

	        {

	            public void actionPerformed(ActionEvent e)

	            {  

	               

	        String licensePlate = licensePlateField.getText().trim();

	              

	                if((licensePlate.length() == 0))

	                {

	                    

	    JOptionPane.showMessageDialog((Component) buttonPanel,

	                           "Please fill in the field and try again",

	               "Blank Field", JOptionPane.ERROR_MESSAGE);

	                }

	                else

	                {

	                 
	        boolean result = CarFrame.myCarLot.carExit(licensePlate);

	 
	                    if(!result)

	                    { JOptionPane.showMessageDialog((Component) buttonPanel, "This license plate is invalid or is already in the lot. Please try	again.", "Invalid Operation", JOptionPane.ERROR_MESSAGE);

	                    }

	                    else

	                    {

	    int another = JOptionPane.showConfirmDialog((Component)

	buttonPanel,"The car has been removed. Remove another car?", "Add Car",

	                 JOptionPane.YES_NO_OPTION);

	 

	        licensePlateField.setText("");

	                         

	        if(another == JOptionPane.NO_OPTION)

	        {

	                            CarFrame.index.setSelectedIndex(0);

	        }                      

	    }                  

	       }

	      }

	      });

	 

	        // button listener for clear button

	        clearButton.addActionListener(new ActionListener()

	        {   public void actionPerformed(ActionEvent e)

	            {

	                // reset text field

	                licensePlateField.setText("");

	            }

	        });

	 

	        return addOrRemoveCarScreen1;

	    }

	}


