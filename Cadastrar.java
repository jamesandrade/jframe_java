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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Cadastrar extends JFrame{
    //itens que serão usados no jframe
    private JLabel jlCodigo, jlNome, jlValPg, jlValVnd, jlQtd, jlUd, jlCateg;
    private JTextField jtCodigo, jtNome, jtValPg, jtValVnd, jtQtd, jtUd, jtCateg;
    private JButton btnOk, btnVoltar;
    public Cadastrar(){
        //define os itens 
        jlCodigo = new JLabel ("Código: ");
        jlNome = new JLabel ("Nome do Produto: ");
        jlValPg = new JLabel ("Valor Pago: ");
        jlValVnd = new JLabel ("Valor de Venda: ");
        jlQtd = new JLabel ("Quantidade em Estoque ");
        jlUd = new JLabel ("Unid. de Medida: ");
        jlCateg = new JLabel ("Categoria: ");
        
        jtCodigo = new JTextField (10);
        jtNome = new JTextField (10);
        jtValPg = new JTextField (10);
        jtValVnd = new JTextField (10);
        jtQtd = new JTextField (10);
        jtUd = new JTextField (10);
        jtCateg = new JTextField (10);
        
        btnOk = new JButton ("Salvar");
        btnVoltar = new JButton("Voltar");
        
        //adiciona ação aos botões
        btnOk.addActionListener(new Ouvinte());
        btnVoltar.addActionListener(new Ouvinte());
        
        //define um container para os itens que serão usados
        Container c= getContentPane();
        c.setLayout(new GridLayout(8,2));
        
        //adiciona os itens ao container
	c.add(jlCodigo); c.add(jtCodigo);
        c.add(jlNome); c.add(jtNome);
        c.add(jlValPg); c.add(jtValPg);
        c.add(jlValVnd); c.add(jtValVnd);
        c.add(jlQtd); c.add(jtQtd);
        c.add(jlUd); c.add(jtUd);
        c.add(jlCateg); c.add(jtCateg);
        c.add(btnOk); c.add(btnVoltar);
        
        setSize(500,300);   //tamanho do jframe
        setTitle("Cadastro de Produto");    //titulo do jframe
	setLocationRelativeTo(null); 
	setVisible(true);   //define o jframe como visivel
    }    
    /**
    * classe ouvinte onde define as funções de cada botão 
    */
    private class Ouvinte implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //função do botão "salvar"
            if (e.getSource()==btnOk) {
                //chama a função cadastrar, se ela retornar true o cadastro foi feito
                //senão houve um erro
                boolean c = cadastrar();
                
                if(c){
                  System.out.println("Cadastrado");
                }else{
                  System.out.println("Erro ao cadastrar");
                }
            }
            //função do botão voltar
            else if (e.getSource()==btnVoltar) {
                //fecha apenas o frame atual
                dispose();
            }
        }
    }
    private boolean cadastrar(){
        //strings necessárias para a conexão com o banco de dados
        String url = "jdbc:postgresql://localhost:5432/mercado"; 
	String driver="org.postgresql.Driver";
	String login="postgres";
	String passwd="root";
        Connection con = null;
        Statement stmt = null;

        //query para a inserção dos dados que foram passados através dos campos de texto
        String query = "INSERT into produto(codigo_produto,nome_produto,valor_pago,valor_venda,qtde_estoque,unidade_medida,categoria)VALUES("
                +jtCodigo.getText().trim()+",'"
                +jtNome.getText().trim()+"',"
                +jtValPg.getText().trim()+","
                +jtValVnd.getText().trim()+","
                +jtQtd.getText().trim()+",'"
                +jtUd.getText().trim()+"','"
                +jtCateg.getText().trim()+"');";
        try {
            
            //executa a query no banco de dados
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, login, passwd);
            stmt = con.createStatement(); 
            stmt.execute(query);   
            stmt.close(); 
            con.close(); 
                     
            JOptionPane.showMessageDialog(null, "Cadastrado");  //mensagem de confirmação
            
            //limpa os jTextfields caso seja cadastrado
            jtCodigo.setText("");
            jtNome.setText("");
            jtValPg.setText("");
            jtValVnd.setText("");
            jtQtd.setText("");
            jtUd.setText("");
            jtCateg.setText("");
            return true;
            
        } catch(SQLException ex) { 
            System.err.print("SQLException:"); 
            System.err.println(ex.getMessage()); 
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cadastrar.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
}
