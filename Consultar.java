/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mercado;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *
 * 
 */
public class Consultar extends JFrame{
    
    //Declara os itens que compõem o jframe
    JTextArea t1 = new JTextArea ( 15, 25 );
    JButton Fechar = new JButton ("Fechar");
    JButton BotCarregarDados = new JButton ("Carregar Dados");		
    JButton Limpar = new JButton ("Limpar");
    JLabel jlNome =new JLabel("Nome .:");
    JTextField jtNome= new JTextField (20);
    
    public Consultar(){
        //define um container para colocar os itens
        Container c = getContentPane ();
	FlowLayout layout = new FlowLayout ();
        c.setLayout (layout);
	layout.setAlignment (FlowLayout.CENTER);
        
        //adiciona ação aos botões
	Fechar.addActionListener ( new Ouvinte());
	BotCarregarDados.addActionListener ( new Ouvinte());
        Limpar.addActionListener ( new Ouvinte());			
        
        //adiciona os itens ao container 
        c.add(jlNome);  c.add(jtNome);
	c.add (t1);
	c.add (Limpar);
	c.add (BotCarregarDados);
	c.add (Fechar);
			
	setTitle("Consultar Produtos"); //titulo do jframe
        setSize (350,520);  //tamanho do frame
	setVisible (true);	//torna o frame visivel
    }

    /**
     * classe ouvinte onde define as funções de cada botão
    **/
  
    private  class Ouvinte implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //botão limpar
            if (e.getSource()==Limpar) {
                //limpa os campos de texto 
                t1.setText ("");
                jtNome.setText("");
		t1.requestFocus();
            }
            //botão fechar
            else if(e.getSource()==Fechar){
                //fecha apenas o jframe que está aberto
                dispose();
            }
            //botão carregar dados
            else if(e.getSource()==BotCarregarDados){
                //chama a função busca
                busca();
            }
        }                       
    }
    /**
     * Função busca é responsavel por pesquisar o nome do produto no
     * banco de dados e imprimir no jtextarea
     */
    private void busca(){
        //Strings necessárias para conexão com o banco de dados
        String url = "jdbc:postgresql://localhost:5432/mercado"; 
	String login="postgres";
	String passwd="root";//senha
        Connection con = null;
        String lista = "",filtro="";
	
        //caso o campo de texto não esteja vazio	   	
        if (!jtNome.getText().equals("")){
            //parte da query que compõe a busca no banco de dados
            // LIKE '%STRING%' procura na coluna nome_produto tuplas que contêm todo ou
            //parte da string digitada
            filtro += "WHERE nome_produto LIKE '%"+jtNome.getText()+"%'";
		   	
	try{
            //cria conexão
            con=DriverManager.getConnection(url,login,passwd);
            Statement sent=con.createStatement();
            ResultSet rs = sent.executeQuery("SELECT * FROM produto "+filtro+" " );	
            
            //armazena os resultados da busca
            while(rs.next()){	
		lista += "Código Produto: " +rs.getInt(1);
		lista += "\nNome do Produto: " +rs.getString(2);		
                lista += "\nValor Pago: " +rs.getDouble(3);		
                lista += "\nValor de Venda: " +rs.getDouble(4);
                lista += "\nEstoque: " +rs.getString(5);
                lista += "\nUnidade de Medida : " +rs.getString(6);
                lista += "\nCategoria: " +rs.getString(7);
                lista += "\n\n";
            }
            sent.close();		
	}catch(SQLException ex){
            System.out.println("Erro de consulta");	
	}	
            //imprime na textarea o conteudo da string que armazena os resultados da busca
            t1.setText(lista);
        }
    }
}
