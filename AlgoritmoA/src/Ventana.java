import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Ventana extends JFrame {

    private JPanel panelMapa;
    private JSpinner columnSpinner;
    private JSpinner rowSpinner;
    private int filas = 5;
    private int columnas = 5;
    //los nombres que tenemos que poner son inicio, final, bloqueo, vacio, checkpoint(mirar si ponemos unos numeros para ordenarlos) y riesgo
    ImageIcon[] items =
        {
        	new ImageIcon("imagenes/awita.jpg","vacio"),
        	new ImageIcon("imagenes/chimuelo_v2.jpg","inicio"),
            new ImageIcon("imagenes/final.png", "final"),
            new ImageIcon("imagenes/vikingo.png", "bloqueo"),
            new ImageIcon("imagenes/checkpoint.jpg","waypoint"),
            new ImageIcon("imagenes/rayo.png","riesgo")
        };

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana frame = new Ventana();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ventana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        setBounds(100, 100, 450, 300);

        // Panel para el mapa con la rendija
        panelMapa = new JPanel();
        panelMapa.setLayout(new GridLayout(5, 5));
        getContentPane().add(panelMapa, BorderLayout.NORTH);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JComboBox<ImageIcon> comboBox = new JComboBox<ImageIcon>();
                
                for (int k = 0; k < items.length; k++) {
                	comboBox.addItem(items[k]);
				}
                comboBox.setSize(50, 50);
                panelMapa.add(comboBox);
            }
        }

        // Configurar la rendija y bot�n de play
        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.SOUTH);

        JButton playButton = new JButton("Play");
        columnSpinner = new JSpinner(new SpinnerNumberModel(5, 5, 15, 1));
        rowSpinner = new JSpinner(new SpinnerNumberModel(5, 5, 15, 1));
        JButton updateButton = new JButton("Aplicar Cambios");

        toolBar.add(playButton);
        toolBar.add(new JLabel("Columnas: "));
        toolBar.add(columnSpinner);
        toolBar.add(new JLabel("Filas: "));
        toolBar.add(rowSpinner);
        toolBar.add(updateButton);

        // Agregar ActionListener al bot�n de "play"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarPrograma();
            }
        });

        // Agregar ActionListener al bot�n de "actualizar"
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarMapa();
            }
        });
        pack();
    }

    private void iniciarPrograma() {
    	Component[] components = panelMapa.getComponents();
    	HashMap<Coordenada,String> coordenadas = new HashMap<>();
    	ArrayList<Coordenada> waypoints=new ArrayList<>();
    	int x=0,y=0;
    	int inicio=0;// si solo hay 1 se lanza el algoritmo;
    	Coordenada coor_ini=null;
    	int ter=0;// si solo hay 1 se lanza el algoritmo;
    	Coordenada coor_fin=null;
    	try {
    		for (Component component : components) {
	        if (component instanceof JComboBox) {
	        	   if(x>=columnas) {//para asociar cada elemento a su posicion en coordenada
	 	        	  y++;
	 	        	  x=0;
	 	           }
		            JComboBox<ImageIcon> comboBox = (JComboBox<ImageIcon>) component;
		            ImageIcon selectedIcon = (ImageIcon) comboBox.getSelectedItem();
		            if(selectedIcon.toString()=="inicio") {
		            	inicio++;
		            	coor_ini=new Coordenada(x,y);
		            	if(inicio>1) 
		            		throw new IllegalArgumentException("Hay mas de un punto de inicio");
		            	
		            }
		            else if(selectedIcon.toString()=="final") {
		            	ter++;
		            	coor_fin=new Coordenada(x,y);
		            	if(ter>1)
		            		throw new IllegalArgumentException("Hay mas de un punto final");
		            }
		            else if(selectedIcon.toString()=="waypoint") {
		            	waypoints.add( new Coordenada(x,y));
		            	
		            }
		            coordenadas.put(new Coordenada(x,y), selectedIcon.toString());
		            x++;
		            
		        }
			}
			AlgoritmoA sol= new AlgoritmoA(filas,columnas,coordenadas,coor_ini,coor_fin,waypoints);
			sol.ResolverCheck();
    	}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
    	
		
    }

    private void actualizarMapa() {
        // Obtener el n�mero de columnas y filas de los JSpinners
        filas = (int) columnSpinner.getValue();
        columnas = (int) rowSpinner.getValue();

        // Crear un nuevo JPanel con las nuevas dimensiones
        JPanel nuevoPanelMapa = new JPanel();
        nuevoPanelMapa.setLayout(new GridLayout(filas, columnas));

        // Agregar un JComboBox en cada celda del nuevo JPanel
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
            	 JComboBox<ImageIcon> comboBox = new JComboBox<ImageIcon>();
            	 for (int k = 0; k < items.length; k++) {
                 	comboBox.addItem(items[k]);
 				}
                 nuevoPanelMapa.add(comboBox);

            }
        }

        // Remover el panel anterior y agregar el nuevo panel al JFrame
        getContentPane().remove(panelMapa);
        panelMapa = nuevoPanelMapa;
        getContentPane().add(panelMapa, BorderLayout.NORTH);

        // Actualizar la interfaz gr�fica
        revalidate();
        repaint();
        
        pack();
    }
}
