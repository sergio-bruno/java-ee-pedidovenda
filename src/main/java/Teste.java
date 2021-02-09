import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.model.vo.DataValor;

public class Teste {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
			EntityManager manager = factory.createEntityManager();
			Session session = manager.unwrap(Session.class);
			Map<Date,BigDecimal>valores = valoresTotaisPorData(15, null,session);
			for(Date data : valores.keySet()){
				System.out.println(data +" = "+valores.get(data));

			}
					manager.close();
		}

	public static Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDias ,Usuario criadoPor,Session session){
		numeroDias -=1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH,numeroDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDias,dataInicial);
		Criteria criteria = session.createCriteria(Pedido.class);
		
		// select date(data_criacao) as data, sum(valor_tota) as valor from pedido
		// where data_cricao >= :dataInicial and vendedor_id = :criador
		// group by date(data_criacao)
		
		criteria.setProjection(Projections.projectionList().add(Projections.sqlGroupProjection("date(data_criacao)as data",
				"date(data_criacao)",new String[]{"data"},new Type[]{ StandardBasicTypes.DATE } ))
				.add(Projections.sum("valorTotal").as("valor")))
				.add(Restrictions.ge("dataCriacao",dataInicial.getTime()));
				if(criadoPor != null){
					criteria.add(Restrictions.eq("vendedor",criadoPor));
				}
		@SuppressWarnings("unchecked")
		List<DataValor> valoresPorData = criteria.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
		for(DataValor dataValor : valoresPorData){
			resultado.put(dataValor.getData(),dataValor.getValor());
		}		
		return resultado;
	}

	private static Map<Date, BigDecimal> criarMapaVazio(Integer numeroDias,	Calendar dataInicial) {
		dataInicial =(Calendar) dataInicial.clone();
		
		Map<Date,BigDecimal>mapaInicial = new TreeMap<>();
		for(int i = 0; i<= numeroDias; i++){
			mapaInicial.put(dataInicial.getTime(),BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		return mapaInicial;
	}
}