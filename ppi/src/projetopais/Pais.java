package projetopais;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Pais {
	
	private int id;
	private String nome;
	private long populacao;
	private double area;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Pais(int id, String nome, long populacao, double area) {
		this.id = id;
		this.nome = nome;
		this.populacao = populacao;
		this.area = area;
	}

	public Pais() {
	}
	
	public Connection obtemConexao() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost/mydb?user=root&password=bomdesql312");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getPopulacao() {
		return populacao;
	}

	public void setPopulacao(long populacao) {
		this.populacao = populacao;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}
	
	   public void incluir() {
		      String sqlInsert = 
		         "INSERT INTO pais (nome, populacao, area) VALUES (?, ?, ?)";
		      try (Connection conn = obtemConexao();
						PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
		         stm.setString(1, getNome());
		         stm.setLong(2, getPopulacao());
		         stm.setDouble(3, getArea());
		         stm.execute();
		         String sqlQuery  = "SELECT LAST_INSERT_ID()";
					try(PreparedStatement stm2 = conn.prepareStatement(sqlQuery);
						ResultSet rs = stm2.executeQuery();) {
						if(rs.next()){
							setId(rs.getInt(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	   
	   public void excluir() {
		      String sqlDelete = "DELETE FROM pais WHERE pais.id = ?";
		      try (Connection conn = obtemConexao();
						PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
		         stm.setInt(1, getId());
		      
		         stm.execute();
		      } catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		public void atualizar() {
			String sqlUpdate = "UPDATE pais SET nome=?, populacao=?, area=? WHERE id=?";
			try (Connection conn = obtemConexao();
					PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
				stm.setString(1, getNome());
				stm.setLong(2, getPopulacao());
				stm.setDouble(3, getArea());
				stm.setInt(4, getId());
				stm.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		   public void carregar() {
		      String sqlSelect = 
		         "SELECT * FROM pais WHERE pais.id = ?";
		   
		      try (Connection conn = obtemConexao();
						PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
		         stm.setInt(1, getId());
		         try (ResultSet rs = stm.executeQuery();){
		            if (rs.next()) {
		               this.setNome(rs.getString(2));
		               this.setPopulacao(rs.getLong(3));
		               this.setArea(rs.getDouble(4));
		            }
		         
		         } 
		         catch (Exception e) {
		            e.printStackTrace();
		         }
		      }
		      catch (SQLException e1) {
		         System.out.print(e1.getStackTrace());
		      }
		   } 
		   
		   public Pais maiorpopulacao() {
				Pais maiorPopulacao = new Pais();
				
				String sqlSelect = "SELECT * FROM pais WHERE populacao = (select max(populacao) from pais)";
				try (Connection conn = obtemConexao();
						PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
					try (ResultSet rs = stm.executeQuery();) {
						if (rs.next()) {
							maiorPopulacao.setId(rs.getInt("id"));
							maiorPopulacao.setNome(rs.getString("nome"));
							maiorPopulacao.setPopulacao(rs.getLong("populacao"));
							maiorPopulacao.setArea(rs.getDouble("area"));
						} 
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (SQLException e1) {
					System.out.println(e1.getStackTrace());
				}
				
				return maiorPopulacao;
		   }
		   
		   
		   
		   public Pais menorarea() {
							Pais menorarea = new Pais();
					
					String sqlSelect = "SELECT * FROM pais WHERE area = (select min(area) from pais)";
					try (Connection conn = obtemConexao();
							PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
						try (ResultSet rs = stm.executeQuery();) {
							if (rs.next()) {
								menorarea.setId(rs.getInt("id"));
								menorarea.setNome(rs.getString("nome"));
								menorarea.setPopulacao(rs.getLong("populacao"));
								menorarea.setArea(rs.getDouble("area"));
							} 
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} catch (SQLException e1) {
						System.out.println(e1.getStackTrace());
					}
					
					return menorarea;
			   }
		   
			public Pais[] tresPaises(){		
				Pais tresPaises[] = new Pais[3];
				int i = 0;
				String sqlSelect = "SELECT * FROM pais LIMIT 0,3";
				try (Connection conn = obtemConexao();
						PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
					try (ResultSet rs = stm.executeQuery();) {
						while (rs.next()) {
							tresPaises[i] = new Pais(rs.getInt("id"), rs.getString("nome"), rs.getLong("populacao"), rs.getDouble("area"));
							i++;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (SQLException e1) {
					System.out.println(e1.getStackTrace());
				}
				
				return tresPaises;
			}
		   
		@Override
		public String toString() {
			return "Pais [id=" + id + ", nome=" + nome + ", populacao=" + populacao + ", area=" + area + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pais other = (Pais) obj;
			if (populacao == 0) {
				if (other.populacao != 0)
					return false;
			} else if (populacao != other.populacao)
				return false;
			if (area == 0) {
				if (other.area != 0)
					return false;
			} else if (area != other.area)
				return false;
			if (id != other.id)
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}
	
	

}
