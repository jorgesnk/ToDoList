package br.senai.sp.informatica.tofolist.dao;



import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.tofolist.modelo.ItemLista;
import br.senai.sp.informatica.tofolist.modelo.Lista;

@Repository
public class ListaDao {
	// @PersistenceContext serve para nao estanciaro EntityManager
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserir(Lista lista){
		manager.persist(lista);
		
	}
	
	public List<Lista> listar(){
		TypedQuery<Lista> query=
				manager.createQuery("select l from Lista l",Lista.class);
		return query.getResultList();
	}
	
	@Transactional
	public void excluir(Long idlista){
		Lista lista= manager.find(Lista.class, idlista);
		manager.remove(lista);
	}
	
	
	
	@Transactional
	public void excluirItem(Long idItem){
		ItemLista item=manager.find(ItemLista.class, idItem);
		Lista lista=item.getLista();
		lista.getItens().remove(item);
		manager.merge(lista);
	}
	
	
	@Transactional
	public Lista buscaLista(Long idLista){
		
		Lista lista=manager.find(Lista.class,idLista);
		return lista;
		
	}
	
	
}
