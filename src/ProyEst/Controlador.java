package ProyEst;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.*;
import java.util.Scanner;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
public class Controlador extends JFrame{
	private static Connection Conexion;
	private JTextField Txt_Id;
	private JTextField Txt_nombrePropietario;
	private JTextField Txt_salida;
	private JTable Tbl_Registros;
	private CallableStatement cts;
	private ResultSet r;
	private JTextField Txt_ModeloAuto;
	private JTextField Txt_Matricula;
	private JTextField Txt_Nombre_Buscador;
	private JTextField Txt_Modelo_Buscador;
	private JTextField Txt_Matricula_Buscador;
	private JTextField Txt_ID_Buscador;
	private JTextField Txt_Estatus_Buscador;
	private JTextField Txt_Cuota;
	private float Cuota=30.00f;
	private JTextField Txt_Eliminar_ID;

	public Controlador() {
		getContentPane().setBackground(new Color(0, 0, 0));
		//procesos para cuando se abre y cierra la ventana
		addWindowListener(new WindowAdapter() {
			@Override
			//para cuando se abre
			public void windowOpened(WindowEvent e) {
				
				Connection pruebaConexion=Controlador.getConnection();
				if(pruebaConexion!=null)
				{
					System.out.println("Conectado");
					System.out.println(pruebaConexion);
					//metodo cargar para cuando abra el programa carge el JTable
					cargar();
				}
				else
				{
					System.out.println("Fallo Al Conectarse");
				}
			
			}
			@Override
			//para cuando se cierra
			public void windowClosing(WindowEvent e) {
				if (Conexion != null) {
				    try {
				    	System.out.println("Desconectado");
				    	Conexion.close();
				    } catch (SQLException a) {	System.out.println("Desconectado");}
			}
				System.exit(0);
			}
		});
		
		setResizable(false);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 421, 1322, 269);
		getContentPane().add(scrollPane);
		
		Tbl_Registros = new JTable();
		scrollPane.setViewportView(Tbl_Registros);
		Tbl_Registros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Tbl_Registros.setBorder(new LineBorder(new Color(25, 25, 112), 2, true));
		
		JPanel Panel_Ingreso = new JPanel();
		Panel_Ingreso.setBackground(Color.WHITE);
		Panel_Ingreso.setBounds(22, 125, 322, 285);
		getContentPane().add(Panel_Ingreso);
		Panel_Ingreso.setLayout(null);
		
		JLabel lbl_NumeroDeRegistro = new JLabel("Numero de registro");
		lbl_NumeroDeRegistro.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_NumeroDeRegistro.setForeground(new Color(255, 255, 255));
		lbl_NumeroDeRegistro.setBounds(10, 25, 164, 14);
		Panel_Ingreso.add(lbl_NumeroDeRegistro);
		
		Txt_Id = new JTextField();
		Txt_Id.setForeground(new Color(0, 0, 0));
		Txt_Id.setBackground(new Color(175, 238, 238));
		Txt_Id.setBounds(10, 49, 236, 20);
		Panel_Ingreso.add(Txt_Id);
		Txt_Id.setColumns(10);
		
		Txt_nombrePropietario = new JTextField();
		Txt_nombrePropietario.setForeground(new Color(0, 0, 0));
		Txt_nombrePropietario.setBackground(new Color(175, 238, 238));
		Txt_nombrePropietario.setBounds(10, 105, 236, 20);
		Panel_Ingreso.add(Txt_nombrePropietario);
		Txt_nombrePropietario.setColumns(10);
		
		JLabel lbl_NombreDelPropietario = new JLabel("Nombre del Propietario");
		lbl_NombreDelPropietario.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_NombreDelPropietario.setForeground(new Color(255, 255, 255));
		lbl_NombreDelPropietario.setBounds(10, 80, 164, 14);
		Panel_Ingreso.add(lbl_NombreDelPropietario);
		
		JLabel lbl_ModeloAuto = new JLabel("Modelo Del Auto");
		lbl_ModeloAuto.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_ModeloAuto.setForeground(new Color(255, 255, 255));
		lbl_ModeloAuto.setBounds(10, 136, 164, 14);
		Panel_Ingreso.add(lbl_ModeloAuto);
		
		Txt_ModeloAuto = new JTextField();
		Txt_ModeloAuto.setForeground(new Color(0, 0, 0));
		Txt_ModeloAuto.setBackground(new Color(175, 238, 238));
		Txt_ModeloAuto.setBounds(10, 161, 236, 20);
		Panel_Ingreso.add(Txt_ModeloAuto);
		Txt_ModeloAuto.setColumns(10);
		
		JLabel lbl_Matricula = new JLabel("Matricula");
		lbl_Matricula.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_Matricula.setForeground(new Color(255, 255, 255));
		lbl_Matricula.setBounds(10, 192, 164, 14);
		Panel_Ingreso.add(lbl_Matricula);
		
		Txt_Matricula = new JTextField();
		Txt_Matricula.setForeground(new Color(0, 0, 0));
		Txt_Matricula.setBackground(new Color(175, 238, 238));
		Txt_Matricula.setBounds(10, 217, 236, 20);
		Panel_Ingreso.add(Txt_Matricula);
		Txt_Matricula.setColumns(10);
		
		JButton Guardar = new JButton("Ingresar");
		Guardar.setBounds(85, 243, 89, 20);
		Panel_Ingreso.add(Guardar);
		
		JLabel lbl_Fondo_Entrada = new JLabel("");
		lbl_Fondo_Entrada.setIcon(new ImageIcon(Controlador.class.getResource("/ProyEst/e n t r a d a s.png")));
		lbl_Fondo_Entrada.setBounds(0, 0, 322, 285);
		Panel_Ingreso.add(lbl_Fondo_Entrada);
		
		JPanel Panel_Salida = new JPanel();
		Panel_Salida.setBackground(Color.WHITE);
		Panel_Salida.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Salida De Datos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)), "Salida De Autos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		Panel_Salida.setBounds(354, 125, 322, 285);
		getContentPane().add(Panel_Salida);
		Panel_Salida.setLayout(null);
		
		JLabel lbl_NumeroDeRegistro_1 = new JLabel("Numero de registro");
		lbl_NumeroDeRegistro_1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_NumeroDeRegistro_1.setForeground(new Color(255, 255, 255));
		lbl_NumeroDeRegistro_1.setBounds(10, 24, 140, 14);
		Panel_Salida.add(lbl_NumeroDeRegistro_1);
		
		Txt_salida = new JTextField();
		Txt_salida.setForeground(new Color(0, 0, 0));
		Txt_salida.setBackground(new Color(175, 238, 238));
		Txt_salida.setBounds(10, 49, 235, 20);
		Panel_Salida.add(Txt_salida);
		Txt_salida.setColumns(10);
		
		JButton btnDarSalida = new JButton("Dar salida");
		btnDarSalida.setBounds(72, 80, 115, 23);
		Panel_Salida.add(btnDarSalida);
		
		JLabel lbl_Fondo_Salida = new JLabel("");
		lbl_Fondo_Salida.setIcon(new ImageIcon(Controlador.class.getResource("/ProyEst/Salidas2.png")));
		lbl_Fondo_Salida.setBounds(0, 0, 322, 285);
		Panel_Salida.add(lbl_Fondo_Salida);
		
		JPanel Panel_Filtro = new JPanel();
		Panel_Filtro.setBackground(Color.WHITE);
		Panel_Filtro.setLayout(null);
		Panel_Filtro.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Panel_Filtro.setBounds(686, 125, 658, 285);
		getContentPane().add(Panel_Filtro);
		//Boton para filtrar
		JButton btn_Buscar = new JButton("Buscar");
		btn_Buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int id;
		  		String matriculaAuto;
		  		String nombre;
		  		String modeloAuto;
		  		String estatus;
		  		//id
		  		if(Txt_ID_Buscador.getText().length()==0)
		  		{
		  			id=00;
		  		}
		  		else { id=Integer.parseInt(Txt_ID_Buscador.getText());}
		  		//matricula
		  		if(Txt_Matricula_Buscador.getText().length()==0)
		  		{
		  			matriculaAuto="vacio";
		  		}
		  		else { matriculaAuto=(Txt_Matricula_Buscador.getText());}
		  		//nombre
		  		if(Txt_Nombre_Buscador.getText().length()==0)
		  		{
		  			nombre="vacio";
		  		}
		  		else { nombre=(Txt_Nombre_Buscador.getText());}
		  		if(Txt_Modelo_Buscador.getText().length()==0)
		  		{
		  			modeloAuto="vacio";
		  		}
		  		else { modeloAuto=(Txt_Modelo_Buscador.getText());}
		  		if(Txt_Estatus_Buscador.getText().length()==0)
		  		{
		  			estatus="vacio";
		  		}
		  		else { estatus=(Txt_Estatus_Buscador.getText());}
		  		//metodo para filtrar
		  		
				Buscador(id,matriculaAuto,nombre,modeloAuto,estatus);
			}
		});
		btn_Buscar.setBounds(10, 244, 115, 23);
		Panel_Filtro.add(btn_Buscar);
		
		JLabel lbl_NombreDelPropietario_1 = new JLabel("Nombre del Propietario");
		lbl_NombreDelPropietario_1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_NombreDelPropietario_1.setForeground(new Color(255, 255, 255));
		lbl_NombreDelPropietario_1.setBounds(10, 24, 164, 14);
		Panel_Filtro.add(lbl_NombreDelPropietario_1);
		
		Txt_Nombre_Buscador = new JTextField();
		Txt_Nombre_Buscador.setForeground(new Color(0, 0, 0));
		Txt_Nombre_Buscador.setBackground(new Color(175, 238, 238));
		Txt_Nombre_Buscador.setColumns(10);
		Txt_Nombre_Buscador.setBounds(10, 49, 326, 20);
		Panel_Filtro.add(Txt_Nombre_Buscador);
		
		JLabel lbl_ModeloAuto_1 = new JLabel("Modelo Del Auto");
		lbl_ModeloAuto_1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_ModeloAuto_1.setForeground(new Color(255, 255, 255));
		lbl_ModeloAuto_1.setBounds(10, 80, 164, 14);
		Panel_Filtro.add(lbl_ModeloAuto_1);
		
		Txt_Modelo_Buscador = new JTextField();
		Txt_Modelo_Buscador.setForeground(new Color(0, 0, 0));
		Txt_Modelo_Buscador.setBackground(new Color(175, 238, 238));
		Txt_Modelo_Buscador.setColumns(10);
		Txt_Modelo_Buscador.setBounds(10, 105, 326, 20);
		Panel_Filtro.add(Txt_Modelo_Buscador);
		
		JLabel lbl_Matricula_1 = new JLabel("Matricula");
		lbl_Matricula_1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_Matricula_1.setForeground(new Color(255, 255, 255));
		lbl_Matricula_1.setBounds(10, 136, 164, 14);
		Panel_Filtro.add(lbl_Matricula_1);
		
		Txt_Matricula_Buscador = new JTextField();
		Txt_Matricula_Buscador.setForeground(new Color(0, 0, 0));
		Txt_Matricula_Buscador.setBackground(new Color(175, 238, 238));
		Txt_Matricula_Buscador.setColumns(10);
		Txt_Matricula_Buscador.setBounds(10, 161, 326, 20);
		Panel_Filtro.add(Txt_Matricula_Buscador);
		
		JLabel lbl_NumeroDeRegistro_1_1_1 = new JLabel("Numero de registro");
		lbl_NumeroDeRegistro_1_1_1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_NumeroDeRegistro_1_1_1.setForeground(new Color(255, 255, 255));
		lbl_NumeroDeRegistro_1_1_1.setBounds(10, 192, 142, 14);
		Panel_Filtro.add(lbl_NumeroDeRegistro_1_1_1);
		
		Txt_ID_Buscador = new JTextField();
		Txt_ID_Buscador.setForeground(new Color(0, 0, 0));
		Txt_ID_Buscador.setBackground(new Color(175, 238, 238));
		Txt_ID_Buscador.setColumns(10);
		Txt_ID_Buscador.setBounds(10, 217, 142, 20);
		Panel_Filtro.add(Txt_ID_Buscador);
		
		JLabel lbl_NumeroDeRegistro_1_1_1_1 = new JLabel("Estatus");
		lbl_NumeroDeRegistro_1_1_1_1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_NumeroDeRegistro_1_1_1_1.setForeground(new Color(255, 255, 255));
		lbl_NumeroDeRegistro_1_1_1_1.setBounds(162, 192, 142, 14);
		Panel_Filtro.add(lbl_NumeroDeRegistro_1_1_1_1);
		
		Txt_Estatus_Buscador = new JTextField();
		Txt_Estatus_Buscador.setForeground(new Color(0, 0, 0));
		Txt_Estatus_Buscador.setBackground(new Color(175, 238, 238));
		Txt_Estatus_Buscador.setColumns(10);
		Txt_Estatus_Buscador.setBounds(162, 217, 174, 20);
		Panel_Filtro.add(Txt_Estatus_Buscador);
		
		JButton btn_Mostrar = new JButton("Mostrar todos los registros");
		btn_Mostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
cargar();
			}
		});
		btn_Mostrar.setBounds(145, 244, 191, 23);
		Panel_Filtro.add(btn_Mostrar);
		
		JButton btn_Eliminar = new JButton("Eliminar");
		btn_Eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				try {
					int ID=Integer.parseInt(Txt_Eliminar_ID.getText());
					String Eliminar=("DELETE FROM registro WHERE id='"+ID+"'");
					
					Statement statement = Conexion.createStatement();
					statement.executeUpdate(Eliminar);
					Txt_Eliminar_ID.setText(null);
					cargar();
					}
					catch(Exception a) {
					
						System.out.println("Error:");
						System.out.println("Revise que no este registrando ID de nuevo.");
					}
			}
		});
		btn_Eliminar.setBounds(346, 82, 239, 23);
		Panel_Filtro.add(btn_Eliminar);
		
		JLabel lbl_Cuota = new JLabel("Cambiar cuota por hora");
		lbl_Cuota.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_Cuota.setForeground(new Color(255, 255, 255));
		lbl_Cuota.setBounds(346, 128, 239, 14);
		Panel_Filtro.add(lbl_Cuota);
		
		Txt_Cuota = new JTextField();
		Txt_Cuota.setForeground(new Color(0, 0, 0));
		Txt_Cuota.setBackground(new Color(175, 238, 238));
		Txt_Cuota.setColumns(10);
		Txt_Cuota.setBounds(346, 153, 239, 20);
		Panel_Filtro.add(Txt_Cuota);
		JLabel lbl_Cuota_Precio = new JLabel("Cuota: "+Cuota);
		lbl_Cuota_Precio.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_Cuota_Precio.setForeground(new Color(255, 255, 255));
		lbl_Cuota_Precio.setBounds(346, 224, 239, 14);
		Panel_Filtro.add(lbl_Cuota_Precio);
		
		JButton btn_Ingresar_Cuota = new JButton("Ingresar");
		btn_Ingresar_Cuota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Cuota=Float.parseFloat(Txt_Cuota.getText());
				lbl_Cuota_Precio.setText("Cuota: "+Cuota);
				cargar();
				Txt_Cuota.setText(null);
				}
				catch(Exception g) {System.out.println("Ocurrior Un Error");}
			}
		});
		btn_Ingresar_Cuota.setBounds(346, 190, 239, 23);
		Panel_Filtro.add(btn_Ingresar_Cuota);
		
		JLabel lbl_Eliminar_Registro = new JLabel("Para Borrar Ingrese el numero de registro.");
		lbl_Eliminar_Registro.setFont(new Font("Microsoft JhengHei UI", Font.BOLD | Font.ITALIC, 11));
		lbl_Eliminar_Registro.setForeground(new Color(255, 255, 255));
		lbl_Eliminar_Registro.setBounds(346, 11, 273, 31);
		Panel_Filtro.add(lbl_Eliminar_Registro);
		
		Txt_Eliminar_ID = new JTextField();
		Txt_Eliminar_ID.setForeground(new Color(0, 0, 0));
		Txt_Eliminar_ID.setBackground(new Color(175, 238, 238));
		Txt_Eliminar_ID.setColumns(10);
		Txt_Eliminar_ID.setBounds(346, 47, 239, 20);
		Panel_Filtro.add(Txt_Eliminar_ID);
		
		JLabel lbl_Fondo_Filtros = new JLabel("");
		lbl_Fondo_Filtros.setIcon(new ImageIcon(Controlador.class.getResource("/ProyEst/Filtros.png")));
		lbl_Fondo_Filtros.setBounds(0, 0, 658, 285);
		Panel_Filtro.add(lbl_Fondo_Filtros);
		
		JLabel lbl_Imagen_Titulo = new JLabel("");
		lbl_Imagen_Titulo.setIcon(new ImageIcon(Controlador.class.getResource("/ProyEst/REGISTRO DE entradas Y SALIDAS.png")));
		lbl_Imagen_Titulo.setBounds(0, 0, 1370, 123);
		getContentPane().add(lbl_Imagen_Titulo);
		
		
		btnDarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id=Integer.parseInt(Txt_salida.getText());
					String Salida="UPDATE registro SET fecha_salida=GETDATE(), hora_salida=GETDATE(), estatus='Salio' where id='"+id+"'";
					
					Statement statement = Conexion.createStatement();
					statement.executeUpdate(Salida);
					Txt_salida.setText(null);
					cargar();
					}
					catch(Exception a) {
				
						System.out.println("Error:");
						System.out.println("Revise que no este registrando ID de nuevo.");
					}
			}
		});
		Guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
				int id=Integer.parseInt(Txt_Id.getText());
				String nombre=(Txt_nombrePropietario.getText());
				String modelo=(Txt_ModeloAuto.getText());
				String matricula=(Txt_Matricula.getText());
				String Ingreso="INSERT INTO registro(id,fecha_entrada,hora_entrada,nombre,modeloAuto,matriculaAuto,estatus,fecha_salida,hora_salida)"
						+ " VALUES('"+id+"',GETDATE(),GETDATE(),'"+nombre+"','"+modelo+"','"+matricula+"','Guardado',NULL,NULL)";
				
				Statement statement = Conexion.createStatement();
				statement.executeUpdate(Ingreso);
				Txt_nombrePropietario.setText(null);
				Txt_ModeloAuto.setText(null);
				Txt_Matricula.setText(null);
				Txt_Id.setText(null);
				cargar();
				}
				catch(Exception a) {
					System.out.println("Error:");
					System.out.println("Revise que no este registrando ID de nuevo.");
				}
	
			}
		});
		
	}
	//fin de el metodo controlador
	
	//metodo conector
	public static Connection getConnection() 
	{
		try
		{

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Conexion=DriverManager.getConnection("jdbc:sqlserver:"
					+ "//localhost:1433;databaseName=Prueba;integratedSecurity=true");
			

		}
		catch(Exception e)
		{
			Conexion=null;
		}
		return Conexion;
	}
	//Metodo para mostrar datos en la tabla y donde se encuentra-
	//el modelo para el JTable
	public void cargar(){
	
		 DefaultTableModel Tbl_RegistrosModel= new DefaultTableModel();
      try{
    	 
    	 Tbl_RegistrosModel.addColumn("ID");
    	Tbl_RegistrosModel.addColumn("Fecha De Entrada");
    	Tbl_RegistrosModel.addColumn("Hora De Entrada");
    	Tbl_RegistrosModel.addColumn("Nombre");
    	Tbl_RegistrosModel.addColumn("Modelo Auto");
    	Tbl_RegistrosModel.addColumn("Matricula");
    	Tbl_RegistrosModel.addColumn("Estatus");
      	Tbl_RegistrosModel.addColumn("Fecha De Salida");
   		Tbl_RegistrosModel.addColumn("Hora De Salida");
  		Tbl_RegistrosModel.addColumn("Pago");
  		Tbl_RegistrosModel.addColumn("Lapso De Tiempo En Minutos");
  		
      cts=Conexion.prepareCall("SELECT id,fecha_entrada,hora_entrada,nombre,modeloAuto,matriculaAuto,estatus,fecha_salida,hora_salida,"
      		+ "((DATEDIFF(minute, hora_entrada, hora_salida)/60.0)*'"+Cuota+"')AS Cuota,(DATEDIFF(minute, hora_entrada, hora_salida))AS Lapso FROM registro");
      r=cts.executeQuery();
      while (r.next()){
      Object dato[]=new  Object[11];
      for (int i=0; i<11; i++){
          dato[i]=r.getString(i+1);
      }
    
     Tbl_RegistrosModel.addRow(dato);
      
      }
     
   

      //aqui pasamos el modelo a nuestra tabla
     this.Tbl_Registros.setModel(Tbl_RegistrosModel);//jTable---jdatos
     
     //el set enabled hace que no puedan editar mi tabla
     Tbl_Registros.setEnabled(false);
      }catch (Exception e){}
}
	//metodo buscador
	public void Buscador(int id,String matriculaAuto,String nombre,String modeloAuto,String estatus){
		 DefaultTableModel Tbl_Registros_BuscadorModel= new DefaultTableModel();
     try{
   	 
    Tbl_Registros_BuscadorModel.addColumn("ID");
    Tbl_Registros_BuscadorModel.addColumn("Fecha De Entrada");
    Tbl_Registros_BuscadorModel.addColumn("Hora De Entrada");
    Tbl_Registros_BuscadorModel.addColumn("Nombre");
    Tbl_Registros_BuscadorModel.addColumn("Modelo Auto");
    Tbl_Registros_BuscadorModel.addColumn("Matricula");
   	Tbl_Registros_BuscadorModel.addColumn("Estatus");
   	Tbl_Registros_BuscadorModel.addColumn("Fecha De Salida");
   	Tbl_Registros_BuscadorModel.addColumn("Hora De Salida");
   	Tbl_Registros_BuscadorModel.addColumn("Pago");
   	Tbl_Registros_BuscadorModel.addColumn("Lapso De Tiempo En Minutos");
   	
      cts=Conexion.prepareCall("SELECT id,fecha_entrada,hora_entrada,nombre,modeloAuto,matriculaAuto,estatus,fecha_salida,hora_salida,"
      		+ "((DATEDIFF(minute, hora_entrada, hora_salida)/60.0)*'"+Cuota+"')AS Cuota,(DATEDIFF(minute, hora_entrada, hora_salida)/60.0)AS Lapso FROM registro "
      		+ "WHERE id='"+id+"' or nombre='"+nombre+"' or modeloAuto='"+modeloAuto+"' or matriculaAuto='"+matriculaAuto+"' or estatus='"+estatus+"'\r\n"
      		+ "");
      r=cts.executeQuery();
      while (r.next()){
      Object dato[]=new  Object[11];
      for (int i=0; i<11; i++){
          dato[i]=r.getString(i+1);
      }
      
      Tbl_Registros_BuscadorModel.addRow(dato);
       
       }
       //aqui pasamos el modelo a nuestra tabla
      this.Tbl_Registros.setModel(Tbl_Registros_BuscadorModel);//jTable---jdatos
      
      //el set enabled hace que no puedan editar mi tabla
      Tbl_Registros.setEnabled(false);
       }catch (Exception e){}
	}
	
	
//metodo principar
	public static void main(String[] args)
	{
		Controlador a=new Controlador();
		
		a.setVisible(true);
		a.setSize(1380,740);
	}
}
