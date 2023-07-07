package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;


import modelo.Filial;
import modelo.Funcionario;

@Stateless
public class FilialService extends GenericService<Filial>{
	
	public FilialService() {
		super(Filial.class);
	}
	
	 @PersistenceContext
	    private EntityManager entityManager;
	    
		 public List<Filial> listarFilialPorNome(String nome){
				final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
				final CriteriaQuery<Filial> cQuery = cBuilder.createQuery(Filial.class);
				final Root<Filial> rootAtendimento = cQuery.from(Filial.class);
				
				final Expression<String> expdescricao = rootAtendimento.get("nome");
				
				cQuery.select(rootAtendimento);
				cQuery.where(cBuilder.like(expdescricao, "%"+ nome +"%"));
				
				List<Filial> resultado = getEntityManager().createQuery(cQuery).getResultList();
				
				return resultado;
				
		 }
	 
		 public int obterNumeroFuncionarios(Filial filial) {
			    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
			    CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
			    Root<Funcionario> root = cQuery.from(Funcionario.class);
			    cQuery.select(cBuilder.count(root));
	
			    Join<Funcionario, Filial> joinFilial = root.join("filial");
			    cQuery.where(cBuilder.equal(joinFilial.get("id"), filial.getId()));
	
			    return entityManager.createQuery(cQuery).getSingleResult().intValue();
		 
		 }
		  
}