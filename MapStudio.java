  /* 	MapStudio.java
  	*	
	*	Microtech Map Studio for Krux Version 1.0.017
  	*	Copyright MicroTech Technologies Inc. 2006 - 2009
	*	Copyright Radioactive Reds Animation 2003 - 2009
   *
 	* Copyright (c) 2010 Sun Microsystems, Inc. All Rights Reserved.
 	* 
 	* Redistribution and use in source and binary forms, with or without
 	* modification, are permitted provided that the following conditions are met:
 	* 
 	* -Redistribution of source code must retain the above copyright notice, this
 	*  list of conditions and the following disclaimer.
 	* 
 	* -Redistribution in binary form must reproduce the above copyright notice, 
 	*  this list of conditions and the following disclaimer in the documentation
 	*  and/or other materials provided with the distribution.
 	* 
 	* Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 	* be used to endorse or promote products derived from this software without 
 	* specific prior written permission.
 	* 
	* This software is provided "AS IS," without a warranty of any kind. ALL 
	* EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
	* ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
	* OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
	* AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 	* AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 	* DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 	* REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 	* INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 	* OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 	* EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 	* 
 	* You acknowledge that this software is not designed, licensed or intended
 	* for use in the design, construction, operation or maintenance of any
 	* nuclear facility.
	*
	
	   Microtech Map Studio
		Version:					1.1.x, Fifth Edition
		Support Version: 		1.1.x
   	Project "SILVER FERRET"
		Build Number 0017 */
  
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.net.*;
   import java.util.*;
   import java.applet.*;
   import javax.swing.*;
   import javax.swing.event.*;
   import java.beans.*;
   import java.io.*;
   import sun.audio.*;
   import javax.sound.sampled.*;
   import javax.imageio.*;
   
  
    public class MapStudio  {
      public static final String PRODUCT = "Microtech© Map Studio";
      public static final String VERSION = "1.1.x";
      public static final String BUILD = "139";
      public static final String RELEASE = "1.1.(" + BUILD + ")";
      public static final String DATE = new java.util.Date().toString();
    
      protected JMenuItem floor1tool;
      protected JMenuItem floor2tool;
      protected JMenuItem floor3tool;
      protected JMenuItem floor4tool;
      protected JMenuItem floor5tool;
      
      protected JRadioButtonMenuItem d0;
      protected JRadioButtonMenuItem dl;
      protected JRadioButtonMenuItem d2;
      
      protected JMenu maptile2 = new JMenu("Design 1");
      protected JMenu maptile3 = new JMenu("Design 2");
      protected JMenu maptile4 = new JMenu("Design 3");
      protected JMenu maptile5 = new JMenu("Design 4");
      protected JMenu maptile6 = new JMenu("Design 5");
          
      protected PrintWriter printErr;        // Default Microtech error stream writer
    
      protected boolean hasEdited = false;
      protected boolean newMap = true;
      protected File loadedFile = null;
      protected Point mapsize = new Point(8,8);
      protected Point kohflag = null;
      protected int maxbounds = (mapsize.x * mapsize.y)  / 2 ;
      protected String mapContent[][] = new String[mapsize.x][mapsize.y];
      protected JFrame Main;
      protected JFileChooser chooser1 = new JFileChooser("./tileset");
      protected JFileChooser chooser2 = new JFileChooser(".");
      
      protected FilePreviewer previewer = new FilePreviewer(chooser1);
      
      protected JMenuItem undoBound = new JMenuItem("Undo One Static Boundary", new ImageIcon(getClass().getResource("/images/undostatic.PNG")));
      protected JMenuItem undoMBound = new JMenuItem("Undo One Mobile Boundary", new ImageIcon(getClass().getResource("/images/undomobile.PNG")));
      protected JMenuItem removeFlag = new JMenuItem("Remove K.O.T.H Flag", new ImageIcon(getClass().getResource("/images/undoflag.PNG")));
      
      protected JTextField statusbar = new JTextField("");
   	
      protected Image tileset = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
      protected Image bound = new ImageIcon(getClass().getResource("/krux2/K_Border1.GIF")).getImage();
      protected Image mbound = new ImageIcon(getClass().getResource("/krux2/K_Border.GIF")).getImage();
      protected Image tbound = new ImageIcon(getClass().getResource("/krux2/K_Border2.png")).getImage();
   	
      protected String tile = "default";
      protected String bnd = "default";
      protected String mbnd = "default";
      protected String tbnd = "default";
      protected String fl1 = "default";
      protected String fl2 = "default";
      protected String fl3 = "default";
      protected String fl4 = "default";
      protected String fl5 = "default";
      
      protected String mapName = "default";
      
      protected Point spawnPoint1 = new Point(0,0);
      protected Point spawnPoint2 = new Point(mapsize.x - 1, mapsize.y - 1);
      
      protected JToolBar toolbar;
      protected JToggleButton tool1;
      protected JToggleButton tool2;
      protected JToggleButton tool3;
      protected JToggleButton tool4;
      protected JToggleButton tool5;
      protected JToggleButton tool6;
      protected JToggleButton tool7;
      protected JToggleButton tool8;
      protected JToggleButton tool9;
      protected JToggleButton tool10;
      protected JToggleButton tool11;
      protected JToggleButton tool12;
      protected JToggleButton tool13;
      
      protected Point[] floors1 = new Point[mapsize.x * mapsize.y];
      protected Point[] floors2 = new Point[mapsize.x * mapsize.y];
      protected Point[] floors3 = new Point[mapsize.x * mapsize.y];
      protected Point[] floors4 = new Point[mapsize.x * mapsize.y];
      protected Point[] floors5 = new Point[mapsize.x * mapsize.y];
      protected Image floortile1 = tileset;
      protected Image floortile2 = tileset;
      protected Image floortile3 = tileset;
      protected Image floortile4 = tileset;
      protected Image floortile5 = tileset;
      protected int floors1cnt = 0;
      protected int floors2cnt = 0;
      protected int floors3cnt = 0;
      protected int floors4cnt = 0;
      protected int floors5cnt = 0;
      
      protected JTextField hitCounter = new JTextField("");
      
      public Point[] bounds;  								// Holds static boundary positions
      public Point[] mobileBounds; 							// Holds shifting boundary positions
      public Point[] trackBounds; 							// Holds tracking boundary positions
   	
      protected int currentTool = 0;
   	
      public static final int NO_TOOL = 0;
      public static final int BOUND_TOOL = 1;
      public static final int MBOUND_TOOL = 2;
      public static final int PSPAWN_TOOL = 3;
      public static final int ESPAWN_TOOL = 4;
      public static final int FLAG_TOOL = 5;
      public static final int REMOVER_TOOL = 6;
      
      public static final int FLOOR1_TOOL = 7;
      public static final int FLOOR2_TOOL = 8;
      public static final int FLOOR3_TOOL = 9;
      public static final int FLOOR4_TOOL = 10;
      public static final int FLOOR5_TOOL = 11;
      public static final int TBOUND_TOOL = 12;
      
      protected boolean saveAsThree = true;
      protected boolean saveAsThreeEX = false;
   	
      protected int count = 0;
      protected int countM = 0; 
      protected int countT = 0; 
   	
      protected JToggleButton playerSp = new JToggleButton("Set Player Spawn Point", false);
      protected JToggleButton enemySp = new JToggleButton("Set Enemy Spawn Point", false);
      
      protected final Image play = new ImageIcon(getClass().getResource("/krux2/player.png")).getImage();
      protected final Image enim = new ImageIcon(getClass().getResource("/krux2/enemy.png")).getImage();
      protected final Image flag = new ImageIcon(getClass().getResource("/krux2/grayflag.PNG")).getImage();
   	   	
      protected JPanel MainMap = 
          new JPanel() {
             public void paintComponent(Graphics g) {
               g.clearRect(0,0,(32) * 30,(32) * 30); // erases the previous frame
               
                  // draws the choson tileset to the map
               for (int x = 0; x < mapsize.x ; x ++) {
                  for (int y = 0; y < mapsize.y; y ++) {
                     g.drawImage(tileset,x * 30, y * 30,this);
                  }
               }
               g.setColor(new Color(100,100,100));
            	
               for (int i = 0; i < floors1cnt; i++) {
                  g.drawImage(floortile1, (floors1[i].x) * 30, (floors1[i].y) * 30, this);
               }
               for (int i = 0; i < floors2cnt; i++) {
                  g.drawImage(floortile2, (floors2[i].x) * 30, (floors2[i].y) * 30, this);
               }
               for (int i = 0; i < floors3cnt; i++) {
                  g.drawImage(floortile3, (floors3[i].x) * 30, (floors3[i].y) * 30, this);
               }
               for (int i = 0; i < floors4cnt; i++) {
                  g.drawImage(floortile4, (floors4[i].x) * 30, (floors4[i].y) * 30, this);
               }
               for (int i = 0; i < floors5cnt; i++) {
                  g.drawImage(floortile5, (floors5[i].x) * 30, (floors5[i].y) * 30, this);
               }
               
            	   // draws physical boundries
               for(int x = 0; x < count; x++) {
                  if(bounds[x].x != 0 && bounds[x].y != 0) {
                     g.drawImage(bound,(bounds[x].x - 1) * 30,(bounds[x].y - 1) * 30,this);
                  }
               }
                  
                  
               	// draws mobile boundries
               for(int x = 0; x < countM; x++) {
                  if(mobileBounds[x].x != 0 && mobileBounds[x].y != 0) {
                     g.drawImage(mbound,(mobileBounds[x].x - 1) * 30,(mobileBounds[x].y - 1) * 30,this);
                  }
               }
               
               for(int x = 0; x < countT; x++) {
                  if(trackBounds[x].x != 0 && trackBounds[x].y != 0) {
                     g.drawImage(tbound,(trackBounds[x].x - 1) * 30,(trackBounds[x].y - 1) * 30,this);
                  }
               }
               
            	   // draws the character
               g.drawImage(play,(spawnPoint1.x * 30) + 5,(spawnPoint1.y * 30) + 5,this);
                  
               	// draws the enemy
               g.drawImage(enim,(spawnPoint2.x * 30) + 5,(spawnPoint2.y * 30) + 5,this);
               
               if(kohflag != null) {
                  g.drawImage(flag,(kohflag.x * 30),(kohflag.y * 30),this);
               }
               
                  // draws the grid system
               for (int i = 0; i < mapsize.x ; i ++) {
                  g.drawRect((i * 30), 0, 30, mapsize.y * 30);
               }
               for (int i = 0; i < mapsize.y ; i ++) {
                  g.drawRect(0, (i * 30), mapsize.x * 30, 30);
               }
                  
            }
         };
      JScrollPane scroller = new JScrollPane(MainMap);
   	
      protected Cursor standard = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor1.gif"), new Point(0,0), "maincursor");
      protected Cursor boundCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor2.gif"), new Point(4,3), "boundcursor");
      protected Cursor mboundCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor3.gif"), new Point(4,3), "mboundcursor");
      protected Cursor playerCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor4.gif"), new Point(4,3), "playcursor");
      protected Cursor enemyCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor5.gif"), new Point(4,3), "enemcursor");
      protected Cursor flagCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor6.gif"), new Point(4,3), "kohflag");
      protected Cursor removeCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor7.gif"), new Point(4,3), "remover"); 
      protected Cursor floor1Cursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor8.gif"), new Point(4,3), "remover"); 
      protected Cursor floor2Cursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor9.gif"), new Point(4,3), "remover"); 
      protected Cursor floor3Cursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor10.gif"), new Point(4,3), "remover"); 
      protected Cursor floor4Cursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor11.gif"), new Point(4,3), "remover"); 
      protected Cursor floor5Cursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor12.gif"), new Point(4,3), "remover"); 
      protected Cursor tboundCursor = MainMap.getToolkit().createCustomCursor(MainMap.getToolkit().createImage("images/cursor13.gif"), new Point(4,3), "tboundcursor");
      
      ActionListener noToolAction = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = NO_TOOL;
               MainMap.setCursor(standard);
               hitCounter.setText(""); 
               tool1.setSelected(true);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
            }
         };
      	
      ActionListener boundToolAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = BOUND_TOOL;
               statusbar.setText("Static Boundary Tool Selected, Remaining Bounds: " + (maxbounds - count));
               hitCounter.setText("" + (maxbounds - count) + "/ " + maxbounds); 
               MainMap.setCursor(boundCursor);
               tool1.setSelected(false);
               tool2.setSelected(true);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
                
      ActionListener mboundToolAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = MBOUND_TOOL;
               statusbar.setText("Mobile Boundary Tool Selected, Remaining Bounds: " + (maxbounds - countM));
               hitCounter.setText("" + (maxbounds - countM) + "/ " + maxbounds); 
               MainMap.setCursor(mboundCursor);
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(true);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener tboundToolAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = TBOUND_TOOL;
               statusbar.setText("Tracking Boundary Tool Selected, Remaining Bounds: " + ((maxbounds / mapsize.x) - countM));
               hitCounter.setText("" + ((maxbounds / mapsize.x) - countM) + "/ " + (maxbounds / mapsize.x)); 
               MainMap.setCursor(tboundCursor);
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(true);
            }
         };
               
      ActionListener pspawnToolAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = PSPAWN_TOOL;
               MainMap.setCursor(playerCursor);
               hitCounter.setText(""); 
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(true);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
                
      ActionListener espawnToolAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = ESPAWN_TOOL;
               MainMap.setCursor(enemyCursor);
               hitCounter.setText(""); 
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(true);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
               
      ActionListener flagToolAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = FLAG_TOOL;
               MainMap.setCursor(flagCursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(true);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener removeToolAction = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = REMOVER_TOOL;
               statusbar.setText("Object Remove Tool Selected, Click a tile to remove and object");
               MainMap.setCursor(removeCursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(true);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener floor1Action = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = FLOOR1_TOOL;
               statusbar.setText("Click to set floor type 1");
               MainMap.setCursor(floor1Cursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(true);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener floor2Action = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = FLOOR2_TOOL;
               statusbar.setText("Click to set floor type 2");
               MainMap.setCursor(floor2Cursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(true);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener floor3Action = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = FLOOR3_TOOL;
               statusbar.setText("Click to set floor type 3");
               MainMap.setCursor(floor3Cursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(true);
               tool11.setSelected(false);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener floor4Action = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = FLOOR4_TOOL;
               statusbar.setText("Click to set floor type 4");
               MainMap.setCursor(floor4Cursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(true);
               tool12.setSelected(false);
               tool13.setSelected(false);
            }
         };
         
      ActionListener floor5Action = 
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               currentTool = FLOOR5_TOOL;
               statusbar.setText("Click to set floor type 5");
               MainMap.setCursor(floor5Cursor);
               hitCounter.setText("");
               tool1.setSelected(false);
               tool2.setSelected(false);
               tool3.setSelected(false);
               tool4.setSelected(false);
               tool5.setSelected(false);
               tool6.setSelected(false);
               tool7.setSelected(false);
               tool8.setSelected(false);
               tool9.setSelected(false);
               tool10.setSelected(false);
               tool11.setSelected(false);
               tool12.setSelected(true);
               tool13.setSelected(false);
            }
         };
         
      ActionListener saveAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               save();
            }
         };
      	
      ActionListener saveAsAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               saveas();
            }
         };
         
      ActionListener openAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               if(editChecker()) {
                  int ret = chooser2.showOpenDialog(Main);
                  if(ret == JFileChooser.APPROVE_OPTION) {
                     preinit();
                     loadedFile = chooser2.getSelectedFile();
                     loadCustomMap(loadedFile);
                     MainMap.setPreferredSize(new Dimension(mapsize.x * 30, mapsize.y * 30));
                     MainMap.repaint();      
                     scroller.revalidate();
                     hasEdited = false;
                     Main.setTitle(PRODUCT + " - [" + loadedFile.getName() + "]");    
                        
                     JOptionPane.showMessageDialog(
                              null,
                              "Completed loading of \"" + chooser2.getSelectedFile().getName() + " from disc!",
                              "Generation Complete",
                              JOptionPane.INFORMATION_MESSAGE);
                  }
               }
               else {
               }
            }
         };
      			
      ActionListener newAction =
          new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               if(editChecker()) {
                  spawnPoint1 = new Point(0,0);
                  spawnPoint2 = new Point(7,7);
                  mapsize = new Point(8,8);
                  maxbounds = (mapsize.x * mapsize.y);
                  count = 0;
                  countM = 0;
                  preinit();
                  Main.setTitle(PRODUCT + " - [Untitled.kmf]");
               }
            }
         };
         	
       /**
       * Map Studio Method: PREINIT
       *
       * Performs Pre-Initialization procedures and clears the map screen
       */
       protected void preinit() {
         undoBound.setEnabled(false);
         undoMBound.setEnabled(false);
         removeFlag.setEnabled(false);
         MainMap.setPreferredSize(new Dimension(mapsize.x * 30, mapsize.y * 30));
         maxbounds = (mapsize.x * mapsize.y)  / 2 ; 
            
         bounds = new Point[maxbounds];
         mobileBounds = new Point[maxbounds];
         trackBounds = new Point[maxbounds];
         mapContent = new String[mapsize.x][mapsize.y];
         kohflag = null;
         
         count = 0;
         countM = 0;
         
         tileset = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
         bound = new ImageIcon(getClass().getResource("/krux2/K_Border1.GIF")).getImage();
         mbound = new ImageIcon(getClass().getResource("/krux2/K_Border.GIF")).getImage();
         tbound = new ImageIcon(getClass().getResource("/krux2/K_Border2.png")).getImage();
         floortile1 = tileset;
         floortile2 = tileset;
         floortile3 = tileset;
         floortile4 = tileset;
         floortile5 = tileset;
      	
         spawnPoint1 = new Point(0,0);
         spawnPoint2 = new Point(mapsize.x - 1, mapsize.y - 1);
                     
         for(int h = 0; h < maxbounds; h ++) {
            bounds[h] = new Point(0,0);
         }
         for(int h = 0; h < maxbounds; h ++) {
            mobileBounds[h] = new Point(0,0);
         }
         for(int h = 0; h < (maxbounds / mapsize.x); h ++) {
            trackBounds[h] = new Point(0,0);
         }
         for(int x = 0; x < mapsize.x; x++) {
            for(int y = 0; y < mapsize.y; y++) {
               mapContent[x][y] = "?";
            }
         }
         
         mapContent[0][0] = "P";
         mapContent[mapsize.x - 1][mapsize.y - 1] = "E";
         
         floors1 = new Point[mapsize.x * mapsize.y];
         floors2 = new Point[mapsize.x * mapsize.y];
         floors3 = new Point[mapsize.x * mapsize.y];
         floors4 = new Point[mapsize.x * mapsize.y];
         floors5 = new Point[mapsize.x * mapsize.y];
         floors1cnt = 0;
         floors2cnt = 0;
         floors3cnt = 0;
         floors4cnt = 0;
         floors5cnt = 0;
         
         MainMap.repaint();
      }
   	
      /**
       * Map Studio Method: INITIALIZE
       *
       * Standard Remlocke OS start-up method and generic initialization method
       */
       protected void initialize() {
         try {
            chooser1.setAccessory(previewer);
            printErr = new PrintWriter(new FileOutputStream(new File("errdesc.txt")));
         }
             catch (Throwable e) {
               printErrMeth(e, "INIT", false); 
            }
         MainMap.setCursor(standard);
         statusbar.setEditable(false);
         JSlider maxX = new JSlider(JSlider.HORIZONTAL, 8, 128, 8);
         JSlider maxY = new JSlider(JSlider.HORIZONTAL, 8, 128, 8);
         
         chooser1.setFileFilter(
                new javax.swing.filechooser.FileFilter() {
                  FileFilter mxtFilter;
                           
                   public boolean accept(File f) {
                     return f.isDirectory() || f.getName().toLowerCase().endsWith(".gif") || f.getName().toLowerCase().endsWith(".png");
                  }
                           
                   public String getDescription() {
                     return "Images (*.GIF/ *.PNG)";
                  }
               }
                        );
         chooser2.setFileFilter(
                new javax.swing.filechooser.FileFilter() {
                  FileFilter mxtFilter;
                           
                   public boolean accept(File f) {
                     return f.isDirectory() || f.getName().toLowerCase().endsWith(".kmf");
                  }
                           
                   public String getDescription() {
                     return "Krux Map Format (*.KMF)";
                  }
               }
                        );
      	
         maxX.addChangeListener(
                new ChangeListener() {
                   public void stateChanged(ChangeEvent e) {
                     JSlider source = (JSlider)e.getSource();
                     if (!source.getValueIsAdjusting()) {
                        mapsize.x = (int)source.getValue();
                        maxbounds = (mapsize.x * mapsize.y);
                        preinit();
                     }
                  } 
               });
      			
         maxY.addChangeListener(
                new ChangeListener() {
                   public void stateChanged(ChangeEvent e) {
                     JSlider source = (JSlider)e.getSource();
                     if (!source.getValueIsAdjusting()) {
                        mapsize.y = (int)source.getValue();
                        maxbounds = (mapsize.x * mapsize.y);
                        preinit();
                     }
                  } 
               });
      	
         JMenuBar mainmenus = new JMenuBar();
         JMenu filemenu = new JMenu("File");
         JMenu mapmenu = new JMenu("Map");
         JMenu toolsmenu = new JMenu("Tools");
         JMenu infomenu = new JMenu("Info");
      	
         mainmenus.add(filemenu);
         mainmenus.add(mapmenu);
         mainmenus.add(toolsmenu);
         mainmenus.add(infomenu);
         
         JMenuItem newfile = new JMenuItem("New", new ImageIcon(getClass().getResource("/images/new.png")));
         JMenuItem savefile = new JMenuItem("Save", new ImageIcon(getClass().getResource("/images/save.png")));
         JMenuItem savefileas = new JMenuItem("Save As...", new ImageIcon(getClass().getResource("/images/saveas.png")));
         JMenuItem openfile = new JMenuItem("Open", new ImageIcon(getClass().getResource("/images/open.png")));
         
         JMenuItem about = new JMenuItem("About", new ImageIcon(getClass().getResource("/images/about.png")));
         about.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     Object[] mess = { new ImageIcon(getClass().getResource("/images/splash.png")), PRODUCT, "Fourth Edition\n(Version " + VERSION + ")\n\nCopyright© Microtech Technologies 2010"};
                     String[] ops = { "OK" };               
                     JOptionPane.showOptionDialog(
                                       null,
                                       mess,
                                       "About " + PRODUCT,
                                       JOptionPane.DEFAULT_OPTION,
                                       JOptionPane.PLAIN_MESSAGE,
                                       null,
                                       ops,
                                       ops[0]);
                  }
               });
         
         infomenu.add(about);
         
         newfile.addActionListener(newAction);
         savefile.addActionListener(saveAction);
         savefileas.addActionListener(saveAsAction);
         openfile.addActionListener(openAction);
      	
         filemenu.add(newfile);
         newfile.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_N, ActionEvent.CTRL_MASK));
         filemenu.add(savefile);
         savefile.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_S, ActionEvent.CTRL_MASK));
         filemenu.add(savefileas);
         filemenu.add(openfile);
         openfile.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      			
         undoBound.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
         
         undoMBound.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
         
         removeFlag.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_F, ActionEvent.CTRL_MASK));
      			
         removeFlag.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     mapContent[kohflag.x][kohflag.y] = "?";
                     kohflag = null;
                     MainMap.repaint();
                     removeFlag.setEnabled(false);
                  }
               });
      			
         undoBound.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     count--;
                     mapContent[bounds[count].x - 1][bounds[count].y - 1] = "?";
                     MainMap.repaint();
                     statusbar.setText("Boundary Removed from (" + (bounds[count].x - 1) + "; " + (bounds[count].y - 1)+ "), Remaining Bounds: " + (maxbounds - (count + 1)));
                     if (count == 0) {
                        undoBound.setEnabled(false);
                     }
                  }
               });
         undoMBound.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     countM--;
                     mapContent[mobileBounds[countM].x - 1][mobileBounds[countM].y - 1] = "?";
                     MainMap.repaint();
                     statusbar.setText("Mobile Boundary Removed from (" + (mobileBounds[countM].x - 1) + "; " + (mobileBounds[countM].y - 1) + "), Remaining Bounds: " + (maxbounds - (countM + 1)));
                     if (countM == 0) {
                        undoMBound.setEnabled(false);
                     }
                  }
               });
      				
         toolsmenu.add(undoBound);
         toolsmenu.add(undoMBound);
         toolsmenu.add(removeFlag);
         toolsmenu.addSeparator();
         
         JMenuItem notool = new JMenuItem("Arrow", new ImageIcon(getClass().getResource("/images/arrow.PNG")));
         notool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_1, ActionEvent.CTRL_MASK));
         JMenuItem boundtool = new JMenuItem("Static Boundaries", new ImageIcon(getClass().getResource("/images/static.PNG")));
         boundtool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_2, ActionEvent.CTRL_MASK));
         JMenuItem mboundtool = new JMenuItem("Mobile Boundaries", new ImageIcon(getClass().getResource("/images/mobile.PNG")));
         mboundtool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_3, ActionEvent.CTRL_MASK));
         final JMenuItem tboundtool = new JMenuItem("Tracking Boundaries", new ImageIcon(getClass().getResource("/images/track.PNG")));
         JMenuItem playerspawntool = new JMenuItem("Player Spawn Location", new ImageIcon(getClass().getResource("/images/player.PNG")));
         playerspawntool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_4, ActionEvent.CTRL_MASK));
         JMenuItem enemyspawntool = new JMenuItem("Enemy Spawn Location", new ImageIcon(getClass().getResource("/images/enemy.PNG")));
         enemyspawntool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_5, ActionEvent.CTRL_MASK));
         JMenuItem flagtool = new JMenuItem("K.O.T.H Flag Location", new ImageIcon(getClass().getResource("/images/flag.PNG")));
         flagtool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_6, ActionEvent.CTRL_MASK));
         JMenuItem removetool = new JMenuItem("Object Remover", new ImageIcon(getClass().getResource("/images/erase.png")));
         removetool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_7, ActionEvent.CTRL_MASK));
         floor1tool = new JMenuItem("Floor Tile 1 Tool", new ImageIcon(getClass().getResource("/images/floor1.PNG")));
         floor1tool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_8, ActionEvent.CTRL_MASK));
         floor2tool = new JMenuItem("Floor Tile 2 Tool", new ImageIcon(getClass().getResource("/images/floor2.PNG")));
         floor2tool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_9, ActionEvent.CTRL_MASK));
         floor3tool = new JMenuItem("Floor Tile 3 Tool", new ImageIcon(getClass().getResource("/images/floor3.PNG")));
         floor3tool.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_0, ActionEvent.CTRL_MASK));
         floor4tool = new JMenuItem("Floor Tile 4 Tool", new ImageIcon(getClass().getResource("/images/floor4.PNG")));
         floor5tool = new JMenuItem("Floor Tile 5 Tool", new ImageIcon(getClass().getResource("/images/floor5.PNG")));
      			      	
         toolsmenu.add(notool);
         toolsmenu.add(boundtool);
         toolsmenu.add(mboundtool);
         toolsmenu.add(tboundtool);
         toolsmenu.add(playerspawntool);
         toolsmenu.add(enemyspawntool);
         toolsmenu.add(flagtool);
         toolsmenu.add(removetool);
         toolsmenu.addSeparator();
         toolsmenu.add(floor1tool);
         toolsmenu.add(floor2tool);
         toolsmenu.add(floor3tool);
         toolsmenu.add(floor4tool);
         toolsmenu.add(floor5tool);
         toolsmenu.addSeparator();
         
         ButtonGroup group = new ButtonGroup();
         
         d0 = new JRadioButtonMenuItem("KMF format (Krux2 Compatible)", false);
         d0.setToolTipText("Sets if Map Studio should store the map in a Krux 2 compatible format");
         d0.addItemListener(
                new ItemListener() {
                   public void itemStateChanged(ItemEvent e) {
                     saveAsThree = false;
                     saveAsThreeEX = false;
                     statusbar.setText("KMF1 Mode Enabled");
                     
                     tool8.setEnabled(false);
                     tool9.setEnabled(false);
                     tool10.setEnabled(false);
                     tool11.setEnabled(false);
                     tool12.setEnabled(false);
                     tool13.setEnabled(false);
                     tboundtool.setEnabled(false);
                     floor1tool.setEnabled(false);
                     floor2tool.setEnabled(false);
                     floor3tool.setEnabled(false);
                     floor4tool.setEnabled(false);
                     floor5tool.setEnabled(false);
                     maptile2.setEnabled(false);
                     maptile3.setEnabled(false);
                     maptile4.setEnabled(false);
                     maptile5.setEnabled(false);
                     maptile6.setEnabled(false);
                  }});
         group.add(d0);
         toolsmenu.add(d0);
                  
         dl = new JRadioButtonMenuItem("KMF2 format (Krux3 Compatible)", true);
         dl.setToolTipText("Sets if Map Studio should store the map in a Krux 3/ Krux 3 RT7 format");
         dl.addItemListener(
                new ItemListener() {
                   public void itemStateChanged(ItemEvent e) {
                     if(!saveAsThree) {
                        saveAsThree = true;
                        saveAsThreeEX = false;
                        statusbar.setText("KMF2 Mode Enabled");
                        
                        tool8.setEnabled(true);
                        tool9.setEnabled(true);
                        tool10.setEnabled(true);
                        tool11.setEnabled(false);
                        tool12.setEnabled(false);
                        tool13.setEnabled(false);
                        tboundtool.setEnabled(false);
                        floor1tool.setEnabled(true);
                        floor2tool.setEnabled(true);
                        floor3tool.setEnabled(true);
                        floor4tool.setEnabled(false);
                        floor5tool.setEnabled(false);
                        maptile2.setEnabled(true);
                        maptile3.setEnabled(true);
                        maptile4.setEnabled(true);
                        maptile5.setEnabled(false);
                        maptile6.setEnabled(false);
                     }
                  }});
         group.add(dl);
         toolsmenu.add(dl);
         
         d2 = new JRadioButtonMenuItem("KMF3 format (Krux3 RT7 Compatible)", false);
         d2.setToolTipText("Sets if Map Studio should store the map in a Krux 3 RT 7 exclusive format");
         d2.addItemListener(
                new ItemListener() {
                   public void itemStateChanged(ItemEvent e) {
                     if(!saveAsThreeEX) {
                        saveAsThree = false;
                        saveAsThreeEX = true;
                        statusbar.setText("KMF3 Mode Enabled");
                        
                        saveAsThreeEX = true;
                        tool8.setEnabled(true);
                        tool9.setEnabled(true);
                        tool10.setEnabled(true);
                        tool11.setEnabled(true);
                        tool12.setEnabled(true);
                        tool13.setEnabled(true);
                        tboundtool.setEnabled(true);
                        floor1tool.setEnabled(true);
                        floor2tool.setEnabled(true);
                        floor3tool.setEnabled(true);
                        floor4tool.setEnabled(true);
                        floor5tool.setEnabled(true);
                        maptile2.setEnabled(true);
                        maptile3.setEnabled(true);
                        maptile4.setEnabled(true);
                        maptile5.setEnabled(true);
                        maptile6.setEnabled(true);
                     }
                  }});  
         group.add(d2);   
         toolsmenu.add(d2);
      	
         notool.addActionListener(noToolAction);
         boundtool.addActionListener(boundToolAction);
         mboundtool.addActionListener(mboundToolAction);
         tboundtool.addActionListener(tboundToolAction);
         playerspawntool.addActionListener(pspawnToolAction);
         enemyspawntool.addActionListener(espawnToolAction);
         flagtool.addActionListener(flagToolAction);
         removetool.addActionListener(removeToolAction);
         floor1tool.addActionListener(floor1Action);
         floor2tool.addActionListener(floor2Action);
         floor3tool.addActionListener(floor3Action);
         floor4tool.addActionListener(floor4Action);
         floor5tool.addActionListener(floor5Action);
         
         JMenu mapmenusize = new JMenu("Map Size");
         mapmenusize.add(new JMenuItem("Map Size Width (8 - 128)"));
         mapmenusize.add(maxX);
         mapmenusize.add(new JMenuItem("Map Size Height (8 - 128)"));
         mapmenusize.add(maxY);
         
         JMenu mapmenutiles = new JMenu("Map Tileset");
       
         JMenu maptile = new JMenu("Tiles");
         
         JMenu maptile1 = new JMenu("Base Design");
         
         JMenuItem def1 = new JMenuItem("Default");
         def1.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     tileset = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
                     tile = "default";
                     MainMap.repaint();
                  }
               });
         maptile1.add(def1);
         JMenuItem cus1 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cus1.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_T, ActionEvent.CTRL_MASK));
         cus1.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     tileset = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     tile = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         maptile1.add(cus1);
         maptile.add(maptile1);
         
         JMenuItem defl1 = new JMenuItem("Default");
         defl1.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     floortile1 = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
                     fl1 = "default";
                     MainMap.repaint();
                  }
               });
         maptile2.add(defl1);
         JMenuItem cusl1 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cusl1.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     floortile1 = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     fl1 = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         maptile2.add(cusl1);
         maptile.add(maptile2);
         
         JMenuItem defl2 = new JMenuItem("Default");
         defl2.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     floortile2 = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
                     fl2 = "default";
                     MainMap.repaint();
                  }
               });
         maptile3.add(defl2);
         JMenuItem cusl2 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cusl2.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     floortile2 = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     fl2 = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         maptile3.add(cusl2);
         maptile.add(maptile3);
         
         JMenuItem defl3 = new JMenuItem("Default");
         defl3.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     floortile3 = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
                     fl3 = "default";
                     MainMap.repaint();
                  }
               });
         maptile4.add(defl3);
         JMenuItem cusl3 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cusl3.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     floortile3 = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     fl3 = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         maptile4.add(cusl3);
         maptile.add(maptile4);
         
         JMenuItem defl4 = new JMenuItem("Default");
         defl4.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     floortile4 = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
                     fl4 = "default";
                     MainMap.repaint();
                  }
               });
         maptile5.add(defl4);
         JMenuItem cusl4 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cusl4.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     floortile4 = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     fl4 = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         maptile5.add(cusl4);
         maptile.add(maptile5);
         
         JMenuItem defl5 = new JMenuItem("Default");
         defl5.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     floortile5 = new ImageIcon(getClass().getResource("/krux2/floor.GIF")).getImage();
                     fl5 = "default";
                     MainMap.repaint();
                  }
               });
         maptile6.add(defl5);
         JMenuItem cusl5 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cusl5.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     floortile5 = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     fl5 = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         maptile6.add(cusl5);
         maptile.add(maptile6);
         
      	
         JMenu bctile = new JMenu("Static Bounds");
         JMenuItem def2 = new JMenuItem("Default");
         def2.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     bound = new ImageIcon(getClass().getResource("/krux2/K_Border1.GIF")).getImage();
                     bnd = "default";
                     MainMap.repaint();
                  }
               });
         bctile.add(def2);
         JMenuItem cus2 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cus2.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     bound = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     bnd = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         cus2.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_B, ActionEvent.CTRL_MASK));
         bctile.add(cus2);
      	
         JMenu mctile = new JMenu("Mobile Bounds");  
         JMenuItem def3 = new JMenuItem("Default");
         def3.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     mbound = new ImageIcon(getClass().getResource("/krux2/K_Border.GIF")).getImage();
                     mbnd = "default";
                     MainMap.repaint();
                  }
               });
         mctile.add(def3);
         JMenuItem cus3 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cus3.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_M, ActionEvent.CTRL_MASK));
         cus3.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     mbound = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     mbnd = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         mctile.add(cus3);
         
         JMenu tctile = new JMenu("Tracking Bounds");  
         JMenuItem def4 = new JMenuItem("Default");
         def4.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     tbound = new ImageIcon(getClass().getResource("/krux2/K_Border2.GIF")).getImage();
                     tbnd = "default";
                     MainMap.repaint();
                  }
               });
         tctile.add(def4);
         JMenuItem cus4 = new JMenuItem("Custom...", new ImageIcon(getClass().getResource("/images/open.png")));
         cus4.setAccelerator(KeyStroke.getKeyStroke(
               KeyEvent.VK_T, ActionEvent.CTRL_MASK));
         cus4.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     chooser1.showOpenDialog(Main);
                     tbound = new ImageIcon(chooser1.getSelectedFile().getAbsolutePath()).getImage();
                     tbnd = "tileset/" + chooser1.getSelectedFile().getName();
                     MainMap.repaint();
                  }
               });
         tctile.add(cus4);
      	
         mapmenutiles.add(maptile);
         mapmenutiles.add(bctile);
         mapmenutiles.add(mctile);
         mapmenutiles.add(tctile);
      	
         mapmenu.add(mapmenusize);
         mapmenu.add(mapmenutiles);
      	   	
         MainMap.addMouseListener(
                new MouseAdapter() {
                   public void mouseClicked(MouseEvent e) {
                     Point pos = new Point(e.getX() / 30, e.getY() / 30);
                     if(currentTool == NO_TOOL) {
                        if(pos.x > mapsize.x - 1 | pos.y > mapsize.y - 1) {
                        }
                        else {
                           statusbar.setText("(" + pos.x + "; " + pos.y + ") = " + mapContent[pos.x][pos.y]);
                        }
                     }
                     else if(currentTool == BOUND_TOOL) {
                        hasEdited = true;
                        if(count == maxbounds) {
                           statusbar.setText("Boundary Limit Reached!");
                        }
                        else {
                           if (mapContent[pos.x][pos.y].equals("S") | mapContent[pos.x][pos.y].equals("M") | mapContent[pos.x][pos.y].equals("T")) {
                              statusbar.setText("There is already a boundary in this tile!");
                           }
                           else {
                              statusbar.setText("Boundary Added to (" + pos.x + "; " + pos.y + ")");
                              hitCounter.setText("" + (maxbounds - (count + 1)) + "/ " + maxbounds); 
                              addBounds(pos.x + 1, pos.y + 1);
                              mapContent[pos.x][pos.y] = "S";
                              MainMap.repaint();
                              undoBound.setEnabled(true);
                           }
                        }
                     }
                     else if(currentTool == MBOUND_TOOL) {
                        hasEdited = true;
                        if(countM == maxbounds) {
                           statusbar.setText("Mobile Boundary Limit Reached!");
                        }
                        else {
                           if (mapContent[pos.x][pos.y].equals("S") | mapContent[pos.x][pos.y].equals("M") | mapContent[pos.x][pos.y].equals("T")){
                              statusbar.setText("There is already a boundary in this tile!");
                           }
                           else {
                              statusbar.setText("Mobile Boundary Added to (" + pos.x + "; " + pos.y + ")");
                              hitCounter.setText((maxbounds - (countM + 1)) + "/ " + maxbounds); 
                              addMBounds(pos.x + 1, pos.y + 1);
                              mapContent[pos.x][pos.y] = "M";
                              MainMap.repaint();
                              undoMBound.setEnabled(true);
                           }
                        }
                     }
                     else if(currentTool == TBOUND_TOOL) {
                        hasEdited = true;
                        if(countT == (maxbounds / mapsize.x)) {
                           statusbar.setText("Tracking Boundary Limit Reached!");
                        }
                        else {
                           if (mapContent[pos.x][pos.y].equals("S") | mapContent[pos.x][pos.y].equals("M") | mapContent[pos.x][pos.y].equals("T")) {
                              statusbar.setText("There is already a boundary in this tile!");
                           }
                           else {
                              statusbar.setText("Tracking Boundary Added to (" + pos.x + "; " + pos.y + ")");
                              hitCounter.setText(((maxbounds / mapsize.x) - (countT + 1)) + "/ " + (maxbounds / mapsize.x)); 
                              addTBounds(pos.x + 1, pos.y + 1);
                              mapContent[pos.x][pos.y] = "T";
                              MainMap.repaint();
                           }
                        }
                     }
                     else if(currentTool == PSPAWN_TOOL) {
                        if (!mapContent[pos.x][pos.y].equals("?")) {
                           statusbar.setText("Cannot intersect other objects!");
                        }
                        else {
                           hasEdited = true;
                           mapContent[spawnPoint1.x][spawnPoint1.y] = "?";
                           spawnPoint1 = pos;
                           mapContent[pos.x][pos.y] = "P";
                           MainMap.repaint();
                        }
                     }
                     else if(currentTool == ESPAWN_TOOL) {
                        if (!mapContent[pos.x][pos.y].equals("?")) {
                           statusbar.setText("Cannot intersect other objects!");
                        }
                        else {
                           hasEdited = true;
                           mapContent[spawnPoint2.x][spawnPoint2.y] = "?";
                           spawnPoint2 = pos;
                           mapContent[pos.x][pos.y] = "E";
                           MainMap.repaint();
                        }
                     }
                     else if(currentTool == FLAG_TOOL) {
                        if (!mapContent[pos.x][pos.y].equals("?")) {
                           statusbar.setText("Cannot intersect other objects!");
                        }
                        else {
                           hasEdited = true;
                           kohflag = pos;
                           mapContent[pos.x][pos.y] = "K";
                           MainMap.repaint();
                           removeFlag.setEnabled(true);
                        }
                     }
                     else if(currentTool == REMOVER_TOOL) {
                        removerTool(pos.x, pos.y);
                     }
                     else if(currentTool == FLOOR1_TOOL) {
                        hasEdited = true;
                        if (mapContent[pos.x][pos.y].equals("1")) {
                           statusbar.setText("This tile is already set to tipe 1!");
                        }
                        else {
                           statusbar.setText("Floor tile design 1 applied to (" + pos.x + "; " + pos.y + ")");
                           addFloorTileDesign(pos.x, pos.y, 1);
                           mapContent[pos.x][pos.y] = "1";
                           MainMap.repaint();
                        }
                     }
                     else if(currentTool == FLOOR2_TOOL) {
                        hasEdited = true;
                        if (mapContent[pos.x][pos.y].equals("2")) {
                           statusbar.setText("This tile is already set to tipe 2!");
                        }
                        else {
                           statusbar.setText("Floor tile design 2 applied to (" + pos.x + "; " + pos.y + ")");
                           addFloorTileDesign(pos.x, pos.y, 2);
                           mapContent[pos.x][pos.y] = "2";
                           MainMap.repaint();
                        }
                     }
                     else if(currentTool == FLOOR3_TOOL) {
                        hasEdited = true;
                        if (mapContent[pos.x][pos.y].equals("3")) {
                           statusbar.setText("This tile is already set to tipe 3!");
                        }
                        else {
                           statusbar.setText("Floor tile design 3 applied to (" + pos.x + "; " + pos.y + ")");
                           addFloorTileDesign(pos.x, pos.y, 3);
                           mapContent[pos.x][pos.y] = "3";
                           MainMap.repaint();
                        }
                     }
                     else if(currentTool == FLOOR4_TOOL) {
                        hasEdited = true;
                        if (mapContent[pos.x][pos.y].equals("4")) {
                           statusbar.setText("This tile is already set to tipe 4!");
                        }
                        else {
                           statusbar.setText("Floor tile design 4 applied to (" + pos.x + "; " + pos.y + ")");
                           addFloorTileDesign(pos.x, pos.y, 4);
                           mapContent[pos.x][pos.y] = "4";
                           MainMap.repaint();
                        }
                     }
                     else if(currentTool == FLOOR5_TOOL) {
                        hasEdited = true;
                        if (mapContent[pos.x][pos.y].equals("5")) {
                           statusbar.setText("This tile is already set to tipe 5!");
                        }
                        else {
                           statusbar.setText("Floor tile design 5 applied to (" + pos.x + "; " + pos.y + ")");
                           addFloorTileDesign(pos.x, pos.y, 5);
                           mapContent[pos.x][pos.y] = "5";
                           MainMap.repaint();
                        }
                     }
                  }
               });
      			
         preinit();
      	
         JPanel mainpane = new JPanel();
         mainpane.setLayout(new BorderLayout());
         mainpane.add(createToolbar(), "North");
         mainpane.add(scroller, "Center");
         mainpane.add(statusbar, "South");
         
         { // defaulting the map studio
            saveAsThree = true;
            saveAsThreeEX = false;
            statusbar.setText("Default Map Format set to KMF2");
                        
            tool8.setEnabled(true);
            tool9.setEnabled(true);
            tool10.setEnabled(true);
            tool11.setEnabled(false);
            tool12.setEnabled(false);
            tool13.setEnabled(false);
            tboundtool.setEnabled(false);
            floor1tool.setEnabled(true);
            floor2tool.setEnabled(true);
            floor3tool.setEnabled(true);
            floor4tool.setEnabled(false);
            floor5tool.setEnabled(false);
            maptile2.setEnabled(true);
            maptile3.setEnabled(true);
            maptile4.setEnabled(true);
            maptile5.setEnabled(false);
            maptile6.setEnabled(false);
         }
                  
         Main = implementWindow(PRODUCT + " - [Untitled.kmf]", mainpane, new ImageIcon(getClass().getResource("/krux/icosml.PNG")), mainmenus, 640, 480, false);
         Main.addWindowListener(
                new WindowAdapter() {
                   public void windowClosing(WindowEvent e) {
                     editChecker();
                     System.exit(0);
                  }
               });
      }
      
       private Component createToolbar() {
         toolbar = new JToolBar();
         toolbar.add(Box.createHorizontalStrut(5));
         toolbar.setRollover(true);
         toolbar.setFloatable(false);
         initTools();
         return toolbar;
      }
      
       protected void addFloorTileDesign(int x, int y, int design) {
         if (design == 1) {
            floors1[floors1cnt] = new Point(x, y);
            mapContent[x][y] = "1";
            floors1cnt++;
         }
         else if (design == 2) {
            floors2[floors2cnt] = new Point(x, y);
            mapContent[x][y] = "2";
            floors2cnt++;
         }
         else if (design == 3) {
            floors3[floors3cnt] = new Point(x, y);
            mapContent[x][y] = "3";
            floors3cnt++;
         }
         else if (design == 4) {
            floors4[floors4cnt] = new Point(x, y);
            mapContent[x][y] = "4";
            floors4cnt++;
         }
         else {
            floors5[floors5cnt] = new Point(x, y);
            mapContent[x][y] = "5";
            floors5cnt++;
         }
      }
      
       private void initTools() {
         JButton newfile = new JButton(new ImageIcon(getClass().getResource("/images/new.png")));
         JButton savefile = new JButton(new ImageIcon(getClass().getResource("/images/save.png")));
         JButton openfile = new JButton(new ImageIcon(getClass().getResource("/images/open.png")));
         
         newfile.setToolTipText("New");
         savefile.setToolTipText("Save");
         openfile.setToolTipText("Open");
         
         newfile.addActionListener(newAction);
         savefile.addActionListener(saveAction);
         openfile.addActionListener(openAction);
       
         tool1 = new JToggleButton(new ImageIcon(getClass().getResource("/images/arrow.PNG")), true);
         tool2 = new JToggleButton(new ImageIcon(getClass().getResource("/images/static.PNG")), false);
         tool3 = new JToggleButton(new ImageIcon(getClass().getResource("/images/mobile.PNG")), false);
         tool4 = new JToggleButton(new ImageIcon(getClass().getResource("/images/player.PNG")), false);
         tool5 = new JToggleButton(new ImageIcon(getClass().getResource("/images/enemy.PNG")), false);
         tool6 = new JToggleButton(new ImageIcon(getClass().getResource("/images/flag.PNG")), false);
         tool7 = new JToggleButton(new ImageIcon(getClass().getResource("/images/erase.png")), false);
         tool8 = new JToggleButton(new ImageIcon(getClass().getResource("/images/floor1.PNG")), false);
         tool9 = new JToggleButton(new ImageIcon(getClass().getResource("/images/floor2.PNG")), false);
         tool10 = new JToggleButton(new ImageIcon(getClass().getResource("/images/floor3.PNG")), false);
         tool11 = new JToggleButton(new ImageIcon(getClass().getResource("/images/floor4.PNG")), false);
         tool12 = new JToggleButton(new ImageIcon(getClass().getResource("/images/floor5.PNG")), false);
         tool13 = new JToggleButton(new ImageIcon(getClass().getResource("/images/track.PNG")), false);
         
         tool1.addActionListener(noToolAction);
         tool2.addActionListener(boundToolAction);
         tool3.addActionListener(mboundToolAction);
         tool4.addActionListener(pspawnToolAction);
         tool5.addActionListener(espawnToolAction);
         tool6.addActionListener(flagToolAction);
         tool7.addActionListener(removeToolAction);
         tool8.addActionListener(floor1Action);
         tool9.addActionListener(floor2Action);
         tool10.addActionListener(floor3Action);
         tool11.addActionListener(floor4Action);
         tool12.addActionListener(floor5Action);
         tool13.addActionListener(tboundToolAction);
         
         tool1.setToolTipText("Arrow Tool");
         tool2.setToolTipText("Static Boundary Tool");
         tool3.setToolTipText("Mobile Boundary Tool");
         tool4.setToolTipText("Player Spawn Point Tool");
         tool5.setToolTipText("Enemy Spawn Point Tool");
         tool6.setToolTipText("K.O.T.H Flag Tool");
         tool7.setToolTipText("Object Eraser Tool");
         tool8.setToolTipText("Floor 1 Tool");
         tool9.setToolTipText("Floor 2 Tool");
         tool10.setToolTipText("Floor 3 Tool");
         tool8.setToolTipText("Floor 4 Tool");
         tool9.setToolTipText("Floor 5 Tool");
         tool10.setToolTipText("Tracking Boundary Tool");
         
         toolbar.add(newfile);
         toolbar.add(savefile);
         toolbar.add(openfile);
         toolbar.add(Box.createHorizontalStrut(10));
         toolbar.add(tool1);
         toolbar.add(tool2);
         toolbar.add(tool3);
         toolbar.add(tool13);
         toolbar.add(tool4);
         toolbar.add(tool5);
         toolbar.add(tool6);
         toolbar.add(tool7);
         toolbar.add(Box.createHorizontalStrut(5));
         toolbar.add(tool8);
         toolbar.add(tool9);
         toolbar.add(tool10);
         toolbar.add(tool11);
         toolbar.add(tool12);
         toolbar.add(Box.createHorizontalStrut(10));
         toolbar.add(new JLabel("Buffer Remaining: "));
         toolbar.add(Box.createHorizontalStrut(5));
         toolbar.add(hitCounter);
         toolbar.add(Box.createHorizontalStrut(5));
                  
         hitCounter.setEditable(false);
      }
      
       public void removerTool(int x, int y) {
         if (mapContent[x][y].equals("S")) {
            int cnt = 0;
            Point boundstemp[] = new Point[count];
            for (int i = 0; i < count; i++) {
               if(bounds[i].x == (x + 1) && bounds[i].y == (y + 1)) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = bounds[i];
                  cnt++;
               }
            }
            count--;
            for (int i = 0; i < count; i++) {
               bounds[i] = boundstemp[i];
            }
            statusbar.setText("Boundary Removed from (" + x + "; " + y + "), Remaining Bounds: " + (maxbounds - (count + 1)));
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("M")) {
            int cnt = 0;
            Point boundstemp[] = new Point[countM];
            for (int i = 0; i < countM; i++) {
               if(mobileBounds[i].x == (x + 1) && mobileBounds[i].y == (y + 1)) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = mobileBounds[i];
                  cnt++;
               }
            }
            countM--;
            for (int i = 0; i < countM; i++) {
               mobileBounds[i] = boundstemp[i];
            }
            statusbar.setText("Mobile boundary Removed from (" + x + "; " + y + "), Remaining Bounds: " + (maxbounds - (countM + 1)));
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("T")) {
            int cnt = 0;
            Point boundstemp[] = new Point[countT];
            for (int i = 0; i < countM; i++) {
               if(trackBounds[i].x == (x + 1) && trackBounds[i].y == (y + 1)) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = trackBounds[i];
                  cnt++;
               }
            }
            countT--;
            for (int i = 0; i < countT; i++) {
               trackBounds[i] = boundstemp[i];
            }
            statusbar.setText("Tracking boundary Removed from (" + x + "; " + y + "), Remaining Bounds: " + ((maxbounds / mapsize.x) - (countT + 1)));
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("1")) {
            int cnt = 0;
            Point boundstemp[] = new Point[floors1cnt];
            for (int i = 0; i < floors1cnt; i++) {
               if(floors1[i].x == x && floors1[i].y == y) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = floors1[i];
                  cnt++;
               }
            }
            floors1cnt--;
            for (int i = 0; i < floors1cnt; i++) {
               floors1[i] = boundstemp[i];
            }
            statusbar.setText("Floortile at location (" + x + "; " + y + ") reset");
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("2")) {
            int cnt = 0;
            Point boundstemp[] = new Point[floors2cnt];
            for (int i = 0; i < floors2cnt; i++) {
               if(floors2[i].x == x && floors2[i].y == y) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = floors2[i];
                  cnt++;
               }
            }
            floors2cnt--;
            for (int i = 0; i < floors2cnt; i++) {
               floors2[i] = boundstemp[i];
            }
            statusbar.setText("Floortile at location (" + x + "; " + y + ") reset");
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("3")) {
            int cnt = 0;
            Point boundstemp[] = new Point[floors3cnt];
            for (int i = 0; i < floors3cnt; i++) {
               if(floors3[i].x == x && floors3[i].y == y) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = floors3[i];
                  cnt++;
               }
            }
            floors3cnt--;
            for (int i = 0; i < floors3cnt; i++) {
               floors3[i] = boundstemp[i];
            }
            statusbar.setText("Floortile at location (" + x + "; " + y + ") reset");
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("4")) {
            int cnt = 0;
            Point boundstemp[] = new Point[floors4cnt];
            for (int i = 0; i < floors4cnt; i++) {
               if(floors4[i].x == x && floors4[i].y == y) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = floors4[i];
                  cnt++;
               }
            }
            floors4cnt--;
            for (int i = 0; i < floors4cnt; i++) {
               floors4[i] = boundstemp[i];
            }
            statusbar.setText("Floortile at location (" + x + "; " + y + ") reset");
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("5")) {
            int cnt = 0;
            Point boundstemp[] = new Point[floors5cnt];
            for (int i = 0; i < floors5cnt; i++) {
               if(floors5[i].x == x && floors5[i].y == y) {
                  mapContent[x][y] = "?";
               }
               else {
                  boundstemp[cnt] = floors5[i];
                  cnt++;
               }
            }
            floors5cnt--;
            for (int i = 0; i < floors5cnt; i++) {
               floors5[i] = boundstemp[i];
            }
            statusbar.setText("Floortile at location (" + x + "; " + y + ") reset");
            MainMap.repaint();
         }
         else if (mapContent[x][y].equals("K")) {
            mapContent[kohflag.x][kohflag.y] = "?";
            kohflag = null;
            MainMap.repaint();
            removeFlag.setEnabled(false);
            statusbar.setText("K.O.T.H Flag Removed");
         }
         else if (mapContent[x][y].equals("P")) {
            statusbar.setText("Cannot Remove Player Spawn Point!");
         }
         else if (mapContent[x][y].equals("E")) {
            statusbar.setText("Cannot Remove Enemy Spawn Point!");
         }
         else {
            statusbar.setText("Nothing to Remove");
         }
      }
      
      /**
       * Map Studio Method: TOKENIZER
       *
       * Breaks text into tokens
       */
       protected String[] tokenizer(String input, String delim) {
         StringTokenizer st = new StringTokenizer(input, delim);
         String[] temp1 = new String[64];
         int counter = 0;
      	
         while(st.hasMoreTokens()) {
            temp1[counter] = st.nextToken();
            counter++;
         }
      	
         String[] temp2 = new String[counter];
      	
         for (int i = 0; i < counter; i++) {
            temp2[i] = temp1[i];
         }
      	
         return temp2;
      }
      
       /**
       * Map Studio Method: STORECUSTOMMAP
       *
       * stores KMF maps to disc
       */
       protected void storeCustomMap(File filename) {
         if (saveAsThree) {
            storeCustomMap3(filename);
         }
         else if (saveAsThreeEX) {
            storeCustomMap3x(filename);
         }
         else {
            try {
               FileWriter output = new FileWriter(filename);
               output.write("<KMFMAP>\r\n");
               output.write(mapName + "\r\n");
               output.write(tile + "\r\n");
               output.write(bnd + "\r\n");
               output.write(mbnd + "\r\n");
               output.write(mapsize.x + "," + mapsize.y + "\r\n");
               output.write("<MAPDATA>" + "\r\n");
               for (int y = 0; y < mapsize.y; y++) {
                  for (int x = 0; x < mapsize.x; x++) {
                     output.write(mapContent[x][y]);
                     if(x != mapsize.x - 1) {
                        output.write(",");
                     }
                  }
                  output.write("\r\n");
               }
               output.write("</MAPDATA>" + "\r\n");
               output.write("</KMFMAP>\r\n");
               output.close();
            }
                catch (Exception f) {
                  printErrMeth(f, "STOREEXTERNALMAP", true);
               }
         }
      }
      
      /**
       * Map Studio Method: STORECUSTOMMAP3
       *
       * stores KMF2 maps to disc
       */
       protected void storeCustomMap3(File filename) {
         try {
            FileWriter output = new FileWriter(filename);
            output.write("<KMF2MAP>\r\n");
            output.write(mapName + "\r\n");
            output.write(tile + "\r\n");
            output.write(bnd + "\r\n");
            output.write(mbnd + "\r\n");
            output.write(fl1 + "\r\n");
            output.write(fl2 + "\r\n");
            output.write(fl3 + "\r\n");
            output.write(mapsize.x + "," + mapsize.y + "\r\n");
            output.write("<MAPDATA>" + "\r\n");
            for (int y = 0; y < mapsize.y; y++) {
               for (int x = 0; x < mapsize.x; x++) {
                  output.write(mapContent[x][y]);
                  if(x != mapsize.x - 1) {
                     output.write(",");
                  }
               }
               output.write("\r\n");
            }
            output.write("</MAPDATA>" + "\r\n");
            output.write("</KMF2MAP>\r\n");
            output.close();
         }
             catch (Exception f) {
               printErrMeth(f, "STOREEXTERNALMAP", true);
            }
      }
      
      /**
       * Map Studio Method: STORECUSTOMMAP3X
       *
       * stores KMF3 maps to disc
       */
       protected void storeCustomMap3x(File filename) {
         try {
            FileWriter output = new FileWriter(filename);
            output.write("<KMF3MAP>\r\n");
            output.write(mapName + "\r\n");
            output.write(tile + "\r\n");
            output.write(bnd + "\r\n");
            output.write(mbnd + "\r\n");
            output.write(tbnd + "\r\n");
            output.write(fl1 + "\r\n");
            output.write(fl2 + "\r\n");
            output.write(fl3 + "\r\n");
            output.write(fl4 + "\r\n");
            output.write(fl5 + "\r\n");
            output.write(mapsize.x + "," + mapsize.y + "\r\n");
            output.write("<MAPDATA>" + "\r\n");
            for (int y = 0; y < mapsize.y; y++) {
               for (int x = 0; x < mapsize.x; x++) {
                  output.write(mapContent[x][y]);
                  if(x != mapsize.x - 1) {
                     output.write(",");
                  }
               }
               output.write("\r\n");
            }
            output.write("</MAPDATA>" + "\r\n");
            output.write("</KMF3MAP>\r\n");
            output.close();
         }
             catch (Exception f) {
               printErrMeth(f, "STOREEXTERNALMAP", true);
            }
      }
   	 
       /**
       * Map Studio Method: LOADCUSTOMMAP
       *
       * loads KMF maps from disc
       */  	
       protected void loadCustomMap(File filename) {
         try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String[] mapbuffer = new String[64];
            String temp = null;
            int counter = 0;
         	
            while((temp = input.readLine()) != null) {
               mapbuffer[counter] = temp;
               counter++;
            }
         	
            if(mapbuffer[0].equals("<KMF2MAP>")) {
               loadCustomMap3(filename);
            }
            else if(mapbuffer[0].equals("<KMF3MAP>")) {
               loadCustomMap3X(filename);
            }
            else {
            
               mapName = mapbuffer[1];
               if(!mapbuffer[2].equals("default")) {
                  tileset = new ImageIcon(mapbuffer[2]).getImage();
                  tile = mapbuffer[2];
               }
               if(!mapbuffer[3].equals("default")) {
                  bound = new ImageIcon(mapbuffer[3]).getImage();
                  bnd = mapbuffer[3];
               }
               if(!mapbuffer[4].equals("default")) {
                  mbound = new ImageIcon(mapbuffer[4]).getImage();
                  mbnd = mapbuffer[4];
               }
            
            
               String[] tempo = tokenizer(mapbuffer[5], ",");
               mapsize = new Point(Integer.parseInt(tempo[0]),Integer.parseInt(tempo[1]));
            
               maxbounds = (mapsize.x * mapsize.y); 
            
               bounds = new Point[maxbounds];
               mobileBounds = new Point[maxbounds];
               trackBounds = new Point[maxbounds / mapsize.x];
                     
               for(int h = 0; h < maxbounds; h ++) {
                  bounds[h] = new Point(0,0);
               }
               for(int h = 0; h < maxbounds; h ++) {
                  mobileBounds[h] = new Point(0,0);
               }
               for(int h = 0; h < (maxbounds / mapsize.x); h ++) {
                  trackBounds[h] = new Point(0,0);
               }
            
               mapContent = new String[mapsize.x][mapsize.y];
            
               for (int y = 0; y < mapsize.y; y++) {
                  String tempd[] = tokenizer(mapbuffer[y + 7], ",");
                  for (int x = 0; x < mapsize.x; x++) {
                     mapContent[x][y] = tempd[x];
                  }
               }
            
               for (int x = 0; x < mapsize.x; x++) {
                  for (int y = 0; y < mapsize.y; y++) {
                     if(mapContent[x][y].toLowerCase().equals("s")) {
                        addBounds(x + 1,y + 1);
                     }
                     else if(mapContent[x][y].toLowerCase().equals("m")) {
                        addMBounds(x + 1,y + 1);
                     }
                     else if(mapContent[x][y].toLowerCase().equals("p")) {
                        spawnPoint1 = new Point(x,y);
                     }
                     else if(mapContent[x][y].toLowerCase().equals("e")) {
                        spawnPoint2 = new Point(x,y);
                     }
                     else if(mapContent[x][y].toLowerCase().equals("k")) {
                        kohflag = new Point(x,y);
                     }
                     else {
                     }
                  } 
               }
            }
         }
             catch (Exception f) {
               printErrMeth(f, "LOADCUSTOMMAP", false);
            }
      }
   
       protected void loadCustomMap3(File filename) {
         try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String[] mapbuffer = new String[64];
            String temp = null;
            int counter = 0;
         	
            while((temp = input.readLine()) != null) {
               mapbuffer[counter] = temp;
               counter++;
            }
         	
            mapName = mapbuffer[1];
            if(!mapbuffer[2].equals("default")) {
               tileset = new ImageIcon(mapbuffer[2]).getImage();
               tile = mapbuffer[2];
            }
            if(!mapbuffer[3].equals("default")) {
               bound = new ImageIcon(mapbuffer[3]).getImage();
               bnd = mapbuffer[3];
            }
            if(!mapbuffer[4].equals("default")) {
               mbound = new ImageIcon(mapbuffer[4]).getImage();
               mbnd = mapbuffer[4];
            }
            if(!mapbuffer[5].equals("default")) {
               fl1 = mapbuffer[5];
               floortile1 = new ImageIcon(mapbuffer[5]).getImage();
            }
            if(!mapbuffer[6].equals("default")) {
               fl2 = mapbuffer[6];
               floortile2 = new ImageIcon(mapbuffer[6]).getImage();
            }
            if(!mapbuffer[7].equals("default")) {
               fl3 = mapbuffer[7];
               floortile3 = new ImageIcon(mapbuffer[7]).getImage();
            }
            
         	
            String[] tempo = tokenizer(mapbuffer[8], ",");
            mapsize = new Point(Integer.parseInt(tempo[0]),Integer.parseInt(tempo[1]));
            
            maxbounds = (mapsize.x * mapsize.y); 
            
            bounds = new Point[maxbounds];
            mobileBounds = new Point[maxbounds];
            trackBounds = new Point[maxbounds / mapsize.x];
            
            floors1 = new Point[mapsize.x * mapsize.y];
            floors2 = new Point[mapsize.x * mapsize.y];
            floors3 = new Point[mapsize.x * mapsize.y];
            floors4 = new Point[mapsize.x * mapsize.y];
            floors5 = new Point[mapsize.x * mapsize.y];
                     
            for(int h = 0; h < maxbounds; h ++) {
               bounds[h] = new Point(0,0);
            }
            for(int h = 0; h < maxbounds; h ++) {
               mobileBounds[h] = new Point(0,0);
            }
            for(int h = 0; h < maxbounds / mapsize.x; h ++) {
               trackBounds[h] = new Point(0,0);
            }
         	
            mapContent = new String[mapsize.x][mapsize.y];
         	
            for (int y = 0; y < mapsize.y; y++) {
               String tempd[] = tokenizer(mapbuffer[y + 10], ",");
               for (int x = 0; x < mapsize.x; x++) {
                  mapContent[x][y] = tempd[x];
               }
            }
            
            for (int x = 0; x < mapsize.x; x++) {
               for (int y = 0; y < mapsize.y; y++) {
                  if(mapContent[x][y].toLowerCase().equals("s")) {
                     addBounds(x + 1,y + 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("1")) {
                     addFloorTileDesign(x, y, 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("2")) {
                     addFloorTileDesign(x, y, 2);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("3")) {
                     addFloorTileDesign(x, y, 3);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("4")) {
                     addFloorTileDesign(x, y, 4);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("5")) {
                     addFloorTileDesign(x, y, 5);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("m")) {
                     addMBounds(x + 1,y + 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("t")) {
                     addTBounds(x + 1,y + 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("p")) {
                     spawnPoint1 = new Point(x,y);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("e")) {
                     spawnPoint2 = new Point(x,y);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("k")) {
                     kohflag = new Point(x,y);
                  }
                  else {
                  }
               }
            }
         }
             catch (Exception f) {
               printErrMeth(f, "LOADCUSTOMMAP", false);
            }
      }
      
       protected void loadCustomMap3X(File filename) {
         try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String[] mapbuffer = new String[64];
            String temp = null;
            int counter = 0;
         	
            while((temp = input.readLine()) != null) {
               mapbuffer[counter] = temp;
               counter++;
            }
         	
            mapName = mapbuffer[1];
            if(!mapbuffer[2].equals("default")) {
               tileset = new ImageIcon(mapbuffer[2]).getImage();
               tile = mapbuffer[2];
            }
            if(!mapbuffer[3].equals("default")) {
               bound = new ImageIcon(mapbuffer[3]).getImage();
               bnd = mapbuffer[3];
            }
            if(!mapbuffer[4].equals("default")) {
               mbound = new ImageIcon(mapbuffer[4]).getImage();
               mbnd = mapbuffer[4];
            }
            if(!mapbuffer[5].equals("default")) {
               tbound = new ImageIcon(mapbuffer[5]).getImage();
            }
            if(!mapbuffer[6].equals("default")) {
               fl1 = mapbuffer[5];
               floortile1 = new ImageIcon(mapbuffer[6]).getImage();
            }
            if(!mapbuffer[7].equals("default")) {
               fl2 = mapbuffer[6];
               floortile2 = new ImageIcon(mapbuffer[7]).getImage();
            }
            if(!mapbuffer[8].equals("default")) {
               fl3 = mapbuffer[7];
               floortile3 = new ImageIcon(mapbuffer[8]).getImage();
            }
            if(!mapbuffer[9].equals("default")) {
               fl4 = mapbuffer[6];
               floortile4 = new ImageIcon(mapbuffer[9]).getImage();
            }
            if(!mapbuffer[10].equals("default")) {
               fl5 = mapbuffer[7];
               floortile5 = new ImageIcon(mapbuffer[10]).getImage();
            }
            
         	
            String[] tempo = tokenizer(mapbuffer[11], ",");
            mapsize = new Point(Integer.parseInt(tempo[0]),Integer.parseInt(tempo[1]));
            
            maxbounds = (mapsize.x * mapsize.y); 
            
            bounds = new Point[maxbounds];
            mobileBounds = new Point[maxbounds];
            trackBounds = new Point[maxbounds / mapsize.x];
            
            floors1 = new Point[mapsize.x * mapsize.y];
            floors2 = new Point[mapsize.x * mapsize.y];
            floors3 = new Point[mapsize.x * mapsize.y];
            floors4 = new Point[mapsize.x * mapsize.y];
            floors5 = new Point[mapsize.x * mapsize.y];
                     
            for(int h = 0; h < maxbounds; h ++) {
               bounds[h] = new Point(0,0);
            }
            for(int h = 0; h < maxbounds; h ++) {
               mobileBounds[h] = new Point(0,0);
            }
            for(int h = 0; h < maxbounds / mapsize.x; h ++) {
               trackBounds[h] = new Point(0,0);
            }
         	
            mapContent = new String[mapsize.x][mapsize.y];
         	
            for (int y = 0; y < mapsize.y; y++) {
               String tempd[] = tokenizer(mapbuffer[y + 13], ",");
               for (int x = 0; x < mapsize.x; x++) {
                  mapContent[x][y] = tempd[x];
               }
            }
            
            for (int x = 0; x < mapsize.x; x++) {
               for (int y = 0; y < mapsize.y; y++) {
                  if(mapContent[x][y].toLowerCase().equals("s")) {
                     addBounds(x + 1,y + 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("1")) {
                     addFloorTileDesign(x, y, 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("2")) {
                     addFloorTileDesign(x, y, 2);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("3")) {
                     addFloorTileDesign(x, y, 3);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("4")) {
                     addFloorTileDesign(x, y, 4);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("5")) {
                     addFloorTileDesign(x, y, 5);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("m")) {
                     addMBounds(x + 1,y + 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("t")) {
                     addTBounds(x + 1,y + 1);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("p")) {
                     spawnPoint1 = new Point(x,y);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("e")) {
                     spawnPoint2 = new Point(x,y);
                  }
                  else if(mapContent[x][y].toLowerCase().equals("k")) {
                     kohflag = new Point(x,y);
                  }
                  else {
                  }
               }
            }
         }
             catch (Exception f) {
               printErrMeth(f, "LOADCUSTOMMAP", false);
            }
      }
   	
       public JFrame implementWindow(String title, Container content, ImageIcon hg, JMenuBar menu, int hsize, int vsize, boolean pack) {
         JFrame newframe = new JFrame(title);
         newframe.setContentPane(content);
         if (pack) {
            newframe.pack();
         }
         else {
            newframe.setSize(hsize,vsize);
         }
         newframe.setJMenuBar(menu);
         newframe.setIconImage(hg.getImage());
         newframe.show();
         newframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         return newframe;
      }
   	
       public JDialog implementDialog(String title, Container content, int hsize, int vsize, boolean pack) {
         JDialog newdiag = new JDialog(Main, title, false); // <-- Remember JFrame notneeded...that's what it is for!
         if (pack) {
            newdiag.pack();
         }
         else {
            newdiag.setSize(hsize,vsize);
         }
         newdiag.setContentPane(content);
         newdiag.show();
         return newdiag;
      }
   	
       public void addBounds(int x, int y) { // Adds static boundaries (they never move)
         bounds[count] = new Point(x, y);
         count++;
      }
      
       public void addMBounds(int x, int y) { // Adds shifting boundaries (they move)
         mobileBounds[countM] = new Point(x, y);
         countM++;
      }
      
       public void addTBounds(int x, int y) { // Adds shifting boundaries (they move)
         trackBounds[countT] = new Point(x, y);
         countT++;
      }
   	
       public static void main(String[] args) {
         MapStudio e = new MapStudio();
         e.initialize();
      }
      
       protected void save() {
         if(loadedFile == null) {
            saveas();
         }
         else {
            hasEdited = false;
            storeCustomMap(loadedFile);
            JOptionPane.showMessageDialog(
                           null,
                           "Map " + chooser2.getSelectedFile().getName() + " stored to disc",
                           "Storage Complete",
                           JOptionPane.INFORMATION_MESSAGE);
         }
      }
   	
       protected void saveas() {
         int result = 0;
         int rer = chooser2.showSaveDialog(Main);
         if(rer == JFileChooser.APPROVE_OPTION) {
            hasEdited = false;
            loadedFile = chooser2.getSelectedFile();
            
            // Test if the user has omitted the file extension, if so add it.
            if (!loadedFile.getName().toLowerCase().endsWith(".kmf")) {
               loadedFile = new File(loadedFile.getAbsolutePath() + ".kmf");
            }
            
            // Test if the file already exists, if so ask to overwrite.
            if (loadedFile.exists()) {
               Object[] mess = {"Would you like to overwrite  \"" + loadedFile.getName() + "\"?"};
               String[] ops = new String[] { "Yes", "No" };            
               result = JOptionPane.showOptionDialog(
                                       null,
                                       mess,
                                       "Overwrite Existing File?",
                                       JOptionPane.DEFAULT_OPTION,
                                       JOptionPane.QUESTION_MESSAGE,
                                       null,
                                       ops,
                                       ops[0]);
            }
            
            // If result is yes from the dialog (default) continue.
            if(result == 0) {
               storeCustomMap(loadedFile);                  	
               JOptionPane.showMessageDialog(
                           null,
                           "Map " + loadedFile.getName() + " stored to disc",
                           "Storage Complete",
                           JOptionPane.INFORMATION_MESSAGE);
               Main.setTitle(PRODUCT + " - [" + loadedFile.getName() + "]");
            }
         }
      }
      
       public boolean editChecker() {
         boolean allow = true;
         String file = "Untitled.kmf";
         if(loadedFile != null) {
            file = loadedFile.getName();
         }
         if(hasEdited) {
            Object[] mess = {"Save changes to \"" + file + "\"?"};
            String[] ops = new String[] { "Yes", "No", "Cancel" };            
            int result = JOptionPane.showOptionDialog(
                                       null,
                                       mess,
                                       "Save Changes?",
                                       JOptionPane.DEFAULT_OPTION,
                                       JOptionPane.QUESTION_MESSAGE,
                                       null,
                                       ops,
                                       ops[0]);
            if (result == 0) {
               if(newMap) {
                  saveas();
               }
               else {
                  save();
               }
               allow = true;
            }
            else if (result == 1) {
               allow = true;
            }
            else {
               allow = false;
            }
         }
         else {
            allow = true;
         }
         return allow;
      }
   
        /**
   	 	*	Microtech's proprietary error handling method shared by many applications
   		*  It handles and displays the appropriate error dialogs
   	 	*
   		*	@see		java.lang.Throwable
   		*	@param 	thr		the Throwable the caused the error
   		*  @param	meth		the name of the method or class that the error occured in
   		*	@param	serious	specifies if the error was serious enought to cause application failure
   		*	@version	1.02 (13/12/2009)
   		*	@author	Microtech Techonologies Incorporated(c) (2009)
   	 	*/
       public void printErrMeth(final Throwable thr, final String meth, boolean serious) {
         JTextArea blueText = new JTextArea("===STOP ERROR: EXCEPTION IN MTWORDWIN32===\n" + 
            					"An Exception has occured in method '" + meth + "' of MTWORDWIN32\n\n" +
                  			thr.toString() + "\n\n" +
                  			"System operation has been halted to prevent futher errors.\n\n" +
            					"If this is the first time you are seeing this screen, it may be due to recent file or hardware changes.\n" +
                  			"Remove or disable any new hardware or software and attempt to reboot your system. If this screen reoccurs, \n" +
            					"it is recommended that you contact your software provider or MicroTech Software Incorporated.\n" +
                  			"An error descriptor 'errdesc.txt' has been created in the your root folder of Device 1 for reference");
            					
         if(!serious) {
            JOptionPane.showMessageDialog(
                           null,
                           "<html><b>An Exception has occured in '" + meth + "'</b><br>" +
                  			"Exception Type:" + "\t\t" + "<i>" + thr.toString() + "</i><br>" +
                  			"Operation has been halted to prevent futher errors.<br><br>" +
                  			"<i>Please let MicroTech know of this error, an error descriptor has been created</i><br>" +
                  			"<br><font color=blue>Descriptor:\t errdesc.txt.</font></html>",
                           "Error: " + thr.getMessage(),
                           JOptionPane.ERROR_MESSAGE);
            printErr.println("==START EXCEPTION==");
            printErr.println("Application Version:\t" + VERSION + ", Build " + BUILD);
            printErr.println("Release:\t\t" + RELEASE + ", " + DATE);
            printErr.println("Exception in File:\tkernel64");
            printErr.println("Exception Type:\t\t" + thr.toString());
            printErr.println("Exception Details:\t" + thr.getMessage());
            printErr.println("Exception Location:\t'" + meth + "'");
            printErr.println("==Exception Stack Trace==");
            thr.printStackTrace();
            thr.printStackTrace(printErr);
            printErr.println("==END OF EXCEPTION==");
            printErr.flush();
         }
         else {
         	// The Error was serious display a Red Screen of Death!
            GraphicsEnvironment h = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice g = h.getDefaultScreenDevice();
         
            JWindow w = new JWindow();
            w.setContentPane(blueText);
            blueText.setBackground(Color.RED);
            blueText.setEditable(false);
            blueText.setForeground(Color.WHITE);
            blueText.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
            blueText.addMouseListener(
                   new MouseAdapter() {
                      public void mousePressed(MouseEvent e) {
                        printErr.println("==START EXCEPTION==");
                        printErr.println("Application Version:\t" + VERSION + ", Build " + BUILD);
                        printErr.println("Release:\t\t" + RELEASE + ", " + DATE);
                        printErr.println("Exception in File:\tKRUXLOADER");
                        printErr.println("Exception Type:\t\t" + thr.toString());
                        printErr.println("Exception Details:\t" + thr.getMessage());
                        printErr.println("Exception Location:\t'" + meth + "'");
                        printErr.println("==Exception Stack Trace==");
                        thr.printStackTrace();
                        thr.printStackTrace(printErr);
                        printErr.println("==END OF EXCEPTION==");
                        printErr.flush();
                        System.exit(2);
                     }
                  });
            g.setFullScreenWindow(w);
            g.setDisplayMode(new DisplayMode(640, 480, 8, 60));
         }
      }
   }
   
    class FilePreviewer extends JComponent implements PropertyChangeListener {
      ImageIcon thumbnail = null;
   
       public FilePreviewer(JFileChooser fc) {
         setPreferredSize(new Dimension(100, 50));
         fc.addPropertyChangeListener(this);
      }
   
       public void loadImage(File f) {
         if (f == null) {
            thumbnail = null;
         } 
         else {
            ImageIcon tmpIcon = new ImageIcon(f.getPath());
            if(tmpIcon.getIconWidth() > 90) {
               thumbnail = new ImageIcon(
                  tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
            } 
            else {
               thumbnail = tmpIcon;
            }
         }
      }
   
       public void propertyChange(PropertyChangeEvent e) {
         String prop = e.getPropertyName();
         if (prop == javax.swing.JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
            if(isShowing()) {
               loadImage((File) e.getNewValue());
               repaint();
            }
         }
      }
   
       public void paint(Graphics g) {
         if(thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;
            if(y < 0) {
               y = 0;
            }
         
            if(x < 5) {
               x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
         }
      }
   }