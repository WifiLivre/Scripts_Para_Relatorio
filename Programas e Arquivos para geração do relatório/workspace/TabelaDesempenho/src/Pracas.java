import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Pracas {

	public void gerarDadosDe(String dataInicio,String regiao){
		AcessaBanco a = new AcessaBanco();
		AcessaBanco b = new AcessaBanco();
		a.abrirConexao();
		b.abrirConexao();
		a.setSql("SELECT "
				+ "distinct praca.id_praca, "
				+ "praca.nomePref, "
				+ "praca.zona "
				+ "FROM "
				+ "dados_praca_por_hora , "
				+ "praca "
				+ "WHERE "
				+ "dados_praca_por_hora.id_data_hora >  '"+dataInicio+"' AND "
				+ "dados_praca_por_hora.id_praca =  praca.id_praca AND "
				+ "praca.zona =  '"+regiao+"' "
				+ "ORDER BY "
				+ "praca.nomePref ASC ");
		ResultSet rs = a.consulta();
		try {
			while(rs.next()){
								
				System.out.print(rs.getString(1)+";"+rs.getString(2)+";"+rs.getString(3)+";");
				
				b.setSql("SELECT "
						+ "Avg(dados_praca_por_hora.entrada), "
						+ "Avg(dados_praca_por_hora.usuarios) "
						+ "FROM "
						+ "dados_praca_por_hora "
						+ "WHERE "
						+ "dados_praca_por_hora.id_praca =  '"+rs.getString(1)+"'");
				ResultSet rs1 = b.consulta();
				
				DecimalFormat eS = new DecimalFormat("0.00");
					
				
				while(rs1.next()){
					String ent;
					String usu;
					if(rs1.getString(1) != null){
						double entrada = Double.parseDouble(rs1.getString(1));
						ent = eS.format(entrada/1000);
					}else{
						ent = "";
					}
					if(rs1.getString(2) != null){
						double usuarios = Double.parseDouble(rs1.getString(2));
						usu = eS.format(usuarios);
					}else{
						usu = "";
					}
					
					
					System.out.print(ent+";"+usu+";");					
					
				
				}
				
				b.setSql("SELECT "
						+ "Avg(simetpraca.tcpDownloadStr), "
						+ "Count(simetpraca.tcpDownloadStr), "
						+ "Avg(simetpraca.latenciaStr), "
						+ "Count(simetpraca.latenciaStr), "
						+ "Avg(simetpraca.perdaDePacotesStr), "
						+ "Count(simetpraca.perdaDePacotesStr) "
						+ "FROM "
						+ "simetpraca "
						+ "WHERE "
						+ "simetpraca.id_praca =  '"+rs.getString(1)+"'");
				
					rs1 = b.consulta();
				
					while(rs1.next()){
						String ent = "";
						if(rs1.getInt(2) > 0){
							ent = eS.format(Double.parseDouble(rs1.getString(1)));
						}
						String lat = "";
						if(rs1.getInt(4) > 0){
							lat = eS.format(Double.parseDouble(rs1.getString(3)));
						}
						String per = "";
						if(rs1.getInt(6) > 0){
							per = eS.format(Double.parseDouble(rs1.getString(5)));
						}
						
						
						
						System.out.print(";"+ent+";"+lat+";"+per);					
						
					
					}
				
	
				System.out.println("");
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		b.fecharConexao();
		a.fecharConexao();	
		
		
		
	}

	
	
	
}
