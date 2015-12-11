import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Usuario {
	
	public double media;
	public double devPad;
	public double variancia;
	public int qtd;
	
	public void tabela5(String zona){
		
		double aux, var;	
		
		
		AcessaBanco a = new AcessaBanco();
		AcessaBanco b = new AcessaBanco();
		AcessaBanco c = new AcessaBanco();
		
		
		a.setSql("SELECT "
				+ "praca.id_praca, "
				+ "praca.nomePref, "
				+ "praca.zona, "
				+ "praca.n_max_usuarios, "
				+ "Avg(dados_praca.usuarios), "
				+ "Max(dados_praca.usuarios), "
				+ "Min(dados_praca.usuarios) "
				+ "FROM "
				+ "praca , "
				+ "dados_praca "
				+ "WHERE "
				+ "praca.id_praca =  dados_praca.id_praca AND "
				+ "praca.zona =  '"+ zona +"' "
				+ "GROUP BY "
				+ "praca.id_praca, "
				+ "praca.nomePref, "
				+ "praca.n_max_usuarios, "
				+ "praca.zona "
				+ "ORDER BY "
				+ "praca.nomePref ASC");
		
		a.abrirConexao();
		b.abrirConexao();
		c.abrirConexao();
		
		ResultSet rs = a.consulta();
		try {
			while(rs.next()){
				variancia = 0.0;
				//System.out.println(rs.getString(1));
				
				b.setSql("SELECT "
						+ "dados_praca.usuarios ,"
						+ "dados_praca.id_data_hora "
						+ "FROM "
						+ "dados_praca "
						+ "WHERE "
						+ "dados_praca.id_praca =  '"+ rs.getString(1) +"' "
						+ "");
				
				c.setSql("SELECT "
						+ "Count(dados_praca.usuarios) "
						+ "FROM "
						+ "dados_praca "
						+ "WHERE "
						+ "dados_praca.id_praca =  '"+ rs.getString(1) +"' "
						+ "");			
				
				
				
				if(rs.getString(5) != null){
					media = Double.parseDouble(rs.getString(5));
					
					ResultSet rs2 = c.consulta();
					try {
						while(rs2.next()){
							qtd = Integer.parseInt(rs2.getString(1));
			//				System.out.println(qtd);
						}
					}catch (SQLException e2) {
						e2.printStackTrace();
					}
					
					ResultSet rs1 = b.consulta();
					try {
						while(rs1.next()){
						aux = 0.0;
						var = 0.0;
						if(rs1.getString(1) != null){
							aux = Double.parseDouble(rs1.getString(1));
						}else{
							qtd = qtd-1;
						}
					//	System.out.println(rs1.getString(2)+";"+aux);
						aux = aux - media;
						var = aux * aux;	
						aux = var /	(qtd-1);
						variancia = aux + variancia;	
							
							
							
							
							
							
							
							
							
						}
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
									
					
				}else{
					media = 0;
					devPad = 0;
				}
				devPad = Math.sqrt(variancia);
				String dev,med;
				DecimalFormat eS = new DecimalFormat("0.00");
				med = eS.format(media);
				dev = eS.format(devPad);
				System.out.println(rs.getString(1)+";"+rs.getString(2)+";"+rs.getString(3)+";"+rs.getString(4)+";"+med+";"+dev+";"+rs.getString(6)+";"+rs.getString(7));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		b.fecharConexao();
		a.fecharConexao();	
		
	}
}
