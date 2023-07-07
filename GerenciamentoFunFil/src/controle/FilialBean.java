package controle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import modelo.Filial;
import modelo.Funcionario;

import modelo.Endereco;
import service.FilialService;
import service.FuncionarioService;


@ViewScoped
@ManagedBean
public class FilialBean {
	
	private Endereco endereco;
	private Boolean gravar = true; 
	private String filtro;
	
	public FilialBean() {
		filial = new Filial();
		endereco = new Endereco();
		filial.setEndereco(endereco);    
	}
	  
	@EJB
	private FilialService filialService;
	
	
	private Filial filial = new Filial();
	private List<Filial> filiais = new ArrayList<Filial>();
	
	

	// Método para pesquisar e fazer a listagem por nome da Filial
	public void pesquisar() {
		filiais = filialService.listarFilialPorNome(filtro);
	}
	
	// Método para calcular o número de funcionários que está em cada Filial 
	public int obterNumeroFuncionarios(Filial filial) {
		return filialService.obterNumeroFuncionarios(filial);
	}
	
	// Método para formatar o CNPJ de maneira padronizada
	public String formatarCNPJ(String cnpj) {
		cnpj = cnpj.replaceAll("[^0-9]", "");
	
		if(cnpj.length() != 14) {
			return cnpj;
		}
	
		return cnpj.substring(0, 2) + "." +
			   cnpj.substring(2, 5) + "." +
			   cnpj.substring(5, 8) + "/" + 
			   cnpj.substring(8, 12) + "-" + 
			   cnpj.substring(12);
	}
	
	
	@PostConstruct // Método após a construção da classe
	private void inicializar() {
		atualizarLista();
	}
	
	// Método para listagem das filiais
	public void listarFiliais() {
		filiais = filialService.listAll(); 
	}
	
	// Método para gravar e atualizar as filiais
	public void gravar() {
		if(filial.getId() != null) {
			filialService.merge(filial);
		}else {
			filialService.create(filial);
		}
		   
		filial = new Filial();
		endereco = new Endereco();
		filial.setEndereco(endereco);
		
		listarFiliais();
		gravar = true;				
	}
	
	// Método para carregar a Filial
	public void carregarFilial(Filial f) {
		filial = f;
		endereco = f.getEndereco();
		
		gravar = false;
	}

	// Método para atualizar a lista de Filial
	private void atualizarLista() {
		filiais = filialService.listAll();
		
	}
	
	// Gets e Sets da classe Filial
	

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

	public Boolean getGravar() {
		return gravar;
	}

	public void setGravar(Boolean gravar) {
		this.gravar = gravar;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	
}
