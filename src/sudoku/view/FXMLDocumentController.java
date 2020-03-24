
package sudoku.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sudoku.control.Lacuna;
import sudoku.control.Tabuleiro;

public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane painel;
    private Tabuleiro tabuleiro;
    private boolean podeResolver;
    private boolean inseriu;
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        setEnable(false);
        podeResolver = false;                 
    }
    
    public void limpaCelulas()
    {
        int[][] mat = new int[9][9];        
        tabuleiro = new Tabuleiro(mat);
        exibeMatriz();
        
    }
    
    
    public void exibeMatriz()
    {
        ObservableList<Node> filhosPainel = painel.getChildren();
        ObservableList<Node> celulas;
        int i = 0, lin=0, col=0, cel = 0;
        
        for (Node node : filhosPainel) 
        {
            if(node instanceof GridPane)
            {
                celulas = ((GridPane)node).getChildren();//celulas mais internas
            
                i = 0;
                List<Integer> L = tabuleiro.getSubMatriz(lin, col);
                for(Node c : celulas)
                {
                    String s = L.get(i) == 0? "": L.get(i)+""; i++;                
                    ((TextField)c).setText(s);

                }
                if(col == 2)
                {
                    col = 0;
                    lin++;
                }

                else
                    col ++;
                }            
        }
    }
    
    public void enviaDadosTabuleiro()
    {
        ObservableList<Node> filhosPainel = painel.getChildren();
        ObservableList<Node> celulas;
        int lin=0, col=0, cel = 0, lin2=0, col2=0;
        int[][] mat = new int[9][9];
        
        
        for (Node node : filhosPainel) 
        {
            if(node instanceof GridPane)
            {
                celulas = ((GridPane)node).getChildren();//celulas mais internas           
                              
                
                lin2 = lin*3;
                col2 = col*3;
                for(Node c : celulas)
                {
                    String s =  ((TextField)c).getText();                    
                    mat[lin2][col2] = s.equals("")?0:Integer.parseInt(s);
                    
                    if(col2 == 2 || col2 == 5 || col2 == 8)
                    {
                        col2 = col*3;
                        lin2++;
                    }
                    else
                        col2++;
                    }    
            }
            if(col == 2)
            {
                col = 0;
                lin++;
            }
            else
                col ++;
        }
        tabuleiro = new Tabuleiro(mat);
    }
        
    private void setEnable(boolean flag)
    {
        ObservableList<Node> filhosPainel = painel.getChildren();
        ObservableList<Node> celulas;
        
        
        
        for (Node node : filhosPainel) 
        {
            if(node instanceof GridPane)
            {
                celulas = ((GridPane)node).getChildren();//celulas mais internas           
                             
               
                for(Node c : celulas)
                    ((TextField)c).setEditable(flag);                    
             
            }
        }
    }
    
    
    
    @FXML
    private void ClkBtAleatorio(ActionEvent event) {
        tabuleiro = new Tabuleiro(null);
        podeResolver = true; 
        exibeMatriz();  
    }

    @FXML
    private void clkBtInserir(ActionEvent event) {
        setEnable(true);
        podeResolver = true;
        inseriu = true;
    }

    @FXML
    private void clkBtResolver(ActionEvent event) {
        if(inseriu)
        {
            enviaDadosTabuleiro();
            Lacuna lacuna = tabuleiro.validateTab();
            if(lacuna != null)
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("Valor já existente na linha:"+(lacuna.getRow()+1)+" ou coluna:"+(lacuna.getCol()+1));
                alert.show();
                podeResolver = false;
            }
            else
                podeResolver = true;
        }        
        
        if(podeResolver)
        {
            setEnable(false);
            tabuleiro.resolve();
            exibeMatriz();
        }
        
    }

    @FXML
    private void clkBtLimpar(ActionEvent event) {
        limpaCelulas();  
        podeResolver = false; 
        
    }
       
}
