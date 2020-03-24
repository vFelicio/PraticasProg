package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Pais;

import java.sql.Connection;

public class PaisDAO {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	// Obtém conexão com o banco de dados
	public static Connection obtemConexao() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useTimezone=true&serverTimezone=America/Sao_Paulo&user=root&password=bomdesql312");
	}
	public static int criar(Pais pais) {
		String sqlInsert = "INSERT INTO pais(nome, populacao, area) VALUES (?, ?, ?)";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setString(1, pais.getNomePais());
			stm.setLong(2, pais.getPopulacaoPais());
			stm.setDouble(3, pais.getAreaPais());
			stm.execute();
			String sqlQuery = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery);
					ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					pais.setIdPais(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pais.getIdPais();
	}
	
	public static void atualizar(int idPais, String nomePais, long populacaoPais, double areaPais) {
		String sqlUpdate = "UPDATE pais SET nome=?, populacao=?, area=? WHERE id=?";
		// usando o try with resources do Java 7, quefecha o queabriu
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setInt(1, idPais);
			stm.setString(2, nomePais);
			stm.setLong(3, populacaoPais);
			stm.setDouble(4, areaPais);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void excluir(int idPais) {
		String sqlDelete = "DELETE FROM pais WHERE id = ?";
		// usando o try with resources do Java 7, quefecha o queabriu
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, idPais);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Pais carregar(int idPais) {
		Pais pais = null;
		String sqlSelect = "SELECT nome, populacao, area FROM pais WHERE id = ?";
		// usando o try with resources do Java 7, quefecha o queabriu
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, idPais);
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					String nome = rs.getString("nome");
					Long populacao = rs.getLong("populacao");
					Double area = rs.getDouble("area");
					pais = new Pais(idPais, nome, populacao, area);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return pais;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList buscaPaisMaisHab() {
		ArrayList buscaHabi = new ArrayList();
		String sqlSelect = "select * from pais order by populacao desc limit 1";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					buscaHabi.add(rs.getString("nome"));
					buscaHabi.add(rs.getString("populacao"));
					buscaHabi.add(rs.getString("area"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return buscaHabi;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList buscaPaisMenor() {
		ArrayList buscaArea = new ArrayList();
		String sqlSelect = "select * from pais order by area cres limit 1";
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					buscaArea.add(rs.getString("nome"));
					buscaArea.add(rs.getString("populacao"));
					buscaArea.add(rs.getString("area"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return buscaArea;
	}

	//criar o vetor de 3 paises
	public static Pais[] vetor3() {
		Pais[] vetor = new Pais[3];
		vetor[0] = carregar(1);
		vetor[1] = carregar(2);
		vetor[2] = carregar(3);
		return vetor;
	}
	
	public static Pais[] outroVetor() {
		Pais pais = null;
		Pais[] vetor = new Pais[4];
		int count = 0;
		String sqlSelect = "SELECT id, nome, populacao, area FROM pais limit 4";
		// usando o try with resources do Java 7, quefecha o queabriu
		try (Connection conn = obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					Integer id = rs.getInt("id");
					String nome = rs.getString("nome");
					Long populacao = rs.getLong("populacao");
					Double area = rs.getDouble("area");
					pais = new Pais(id, nome, populacao, area);
					vetor[count++] = pais;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return vetor;
	}
	
}