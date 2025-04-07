/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        //"SALVAR"
        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        conn = new conectaDAO().connectDB();

        String sql = "SELECT * FROM produtos";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listagem;
    }

    public void venderProduto(int produtoId) {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "UPDATE produtos SET status = ? WHERE id = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Atualiza o status para "Vendido"
            prep.setInt(2, produtoId); // Identifica o produto pelo ID
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar status: " + ex.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = ?";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                produtosVendidos.add(produto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produtosVendidos;
    }

}
