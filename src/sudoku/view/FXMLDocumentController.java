/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Aluno
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane painel;
    private Tabuleiro tabuleiro;
    private boolean podeResolver;
    private boolean inseriu;
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        painel.setDisable(true);
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
                celulas = ((GridPane)node).getChildren();
            
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
    
    @FXML
    private void ClkBtAleatorio(ActionEvent event) {
        tabuleiro = new Tabuleiro(null);
        podeResolver = true; 
        exibeMatriz();  
    }

    @FXML
    private void clkBtInserir(ActionEvent event) {
        painel.setDisable(false);
        podeResolver = true;
        inseriu = true;
    }

    @FXML
    private void clkBtResolver(ActionEvent event) {
        if(inseriu)
        {
            Lacuna lacuna = tabuleiro.validateTab();
            if(lacuna != null)
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("");
                alert.setContentText("Valor existente na linha:"+lacuna.getRow()+"coluna:"+lacuna.getCol());
            }
        }        
        
        if(podeResolver)
        {
            painel.setDisable(true);
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
