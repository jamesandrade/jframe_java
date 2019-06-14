/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mercado;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;


public class Mercado extends JFrame{
    //nesse trabalho foi adicionado a biblioteca jdbc do postgree, que vem no netbeans
    //itens do jframe (botões) 
    private JButton btnCadastrar, btnConsultar, btnSair;

    public Mercado(){
       //define os botões
        btnCadastrar = new JButton ("Cadastrar Produto");
        btnConsultar = new JButton ("Consultar Produtos");
        btnSair = new JButton ("Sair");
        btnCadastrar.addActionListener(new Ouvinte());
        btnConsultar.addActionListener(new Ouvinte());
        btnSair.addActionListener(new Ouvinte());


        //define container para os jButton's
        Container c= getContentPane();
        c.setLayout(new GridLayout(3,1));
        //adiciona jbutton's ao container
        c.add(btnCadastrar);
        c.add(btnConsultar);
        c.add(btnSair);
        setResizable(false);    //desativa a opção de mudar o tamanho do frame manualmente
               
        setSize(300,300);   //tamanho do jframe
	setTitle("Mercado - Home"); //titulo
	setLocationRelativeTo(null); 
	setVisible(true);   //torna o jframe visivel
    }
    /**
    * classe ouvinte onde define as funções de cada botão 
    */
   private class Ouvinte implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //função do botão cadastrar produto
            if (e.getSource()==btnCadastrar) {
                //chama um jframe cadastrar 
                Cadastrar c = new Cadastrar();
                //torna visivel o jframe chamado
                c.setVisible(true);
            }
            //função do botão consultar produto
            else if (e.getSource()==btnConsultar) {
                //chama um jframe consultar 
                Consultar c = new Consultar();
                //torna visivel o jframe chamado
                c.setVisible(true);
            }
            //função do botão Sair
            else if (e.getSource()==btnSair) {
                //mata(fecha) a aplicação toda
                System.exit(0);
            }
        }
   }
   /**
    *IMPORTANTE!
    * função main é a principal função do código, pois ela é a primeira a ser 
    * chamada
    */
    public static void main(String[] args){
	//chama a função criartabela (toda vez que a aplicação é iniciada)
        criartabela();        
        
        //define o tema usado na aplicação
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); //tema nimbus
            } catch(Exception e) {
                e.printStackTrace();
            }
        //chama o jframe principal (ele mesmo)
        Mercado janela = new Mercado();
        janela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		System.exit(0);
            }
	});
    }
    
    private static void criartabela() {
        String url = "jdbc:postgresql://localhost:5432/mercado"; 
	String driver="org.postgresql.Driver";
	String login="postgres";
	String passwd="root";
        Connection con;
        Statement stmt; 

        //cria a tabela caso não exista
        String query = "CREATE TABLE IF NOT EXISTS produto("
                + "codigo_produto integer not null primary key,"
                + "nome_produto varchar(60),"
                + "valor_pago numeric(12,2),"
                + "valor_venda numeric(12,2),"
                + "qtde_estoque numeric(12,2),"
                + "unidade_medida varchar(20),"
                + "categoria varchar(60));";
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mercado.class.getName()).log(Level.SEVERE, null, ex);
        }

        try { 
            //cria conexão e executa a query
            con = (Connection) DriverManager.getConnection(url, login, passwd);
            stmt = con.createStatement(); 
            int rs = stmt.executeUpdate(query); //aqui executa a query   
            stmt.close(); //fecha o statement
            con.close(); //fecha a conexão
        } catch(SQLException ex) { 
            System.err.print("SQLException:"); 
            System.err.println(ex.getMessage()); 
        } 
    }
}
    
