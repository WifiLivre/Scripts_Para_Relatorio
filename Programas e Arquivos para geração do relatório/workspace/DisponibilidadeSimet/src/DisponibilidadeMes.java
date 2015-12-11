import java.sql.ResultSet;
import java.sql.SQLException;


public class DisponibilidadeMes {

	public void dispMes(String mes1, int qtdDiasMes1,String mes2, int qtdDiasMes2,String mes3, int qtdDiasMes3){
	
		
		qtdDiasMes1 = qtdDiasMes1+1;
		qtdDiasMes2 = qtdDiasMes2+1;
		qtdDiasMes3 = qtdDiasMes3+1;
		
		AcessaBanco a1 = new AcessaBanco();
		AcessaBanco a2 = new AcessaBanco();	
		a1.abrirConexao();
		a2.abrirConexao();
				
				int idpraca = 0;
		
				a1.setSql("SELECT Distinct simetpraca.id_praca FROM simetpraca where simetpraca.id_praca < 1000 ");
			
				ResultSet rs1 = a1.consulta();
				try {
					while(rs1.next()){
						idpraca = Integer.parseInt(rs1.getString(1));
		
						String mesIns = null;
						String dia = null;	
						int cont = 0;
						int qtdMaxDias = 0; 
						
						for (int x = 0; x < 3;x++){
							if(x == 0){
								mesIns = mes1;
								qtdMaxDias = qtdDiasMes1;
							}
							if(x == 1){
								mesIns = mes2;
								qtdMaxDias = qtdDiasMes2;
							}
							if(x == 2){
								mesIns = mes3;
								qtdMaxDias = qtdDiasMes3;
							}
							
							for(int j = 1; j < qtdMaxDias;j++){	
								
								if(j < 10){
									dia = "0"+ String.valueOf(j);
								}else{
									dia = String.valueOf(j);
								}	
								
								
								
								for(int i = 0; i < 24; i++){
									String hora = null;	
									
									if(i < 10){
										hora = "0"+ String.valueOf(i);
									}else{
										hora = String.valueOf(i);
									}			
																
								
								
									
									a2.setSql("SELECT "
											+ "Max(simetpraca.id_data_hora) "
											+ "FROM simetpraca "
											+ "WHERE "
											+ "simetpraca.id_praca =  '"+idpraca+"' AND "
											+ "simetpraca.id_data_hora LIKE  '2015-"+mesIns+"-"+dia+" "+hora+":%'");
							
									ResultSet rs2 = a2.consulta();
									try{
										while(rs2.next()){
											if(rs2.getString(1) != null){
													
											//System.out.println(rs2.getString(1));
												cont++;
											}
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
						
								}
				
							}
						}
						System.out.println(idpraca+" | "+cont);
						
						
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			a2.fecharConexao();
			a1.fecharConexao();
	}
		
}
		

