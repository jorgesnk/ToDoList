package br.senai.sp.informatica.tofolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.tofolist.modelo.ItemLista;
import br.senai.sp.informatica.tofolist.modelo.Lista;

@Repository
public class ItemDAO {

	@PersistenceContext
	private EntityManager manager;
	
	
	//@Transactional quando o metod precisa de transição
	@Transactional
	public void marcarCOmoFeito (Long idItem, boolean valor){
		ItemLista item= manager.find(ItemLista.class, idItem);
		item.setFeito(valor);
		manager.merge(item);
	}
	
	@Transactional
	public void inserir (Long idLista, ItemLista item){
		item.setLista(manager.find(Lista.class, idLista));
		manager.persist(item);
	}
	
	public ItemLista litarItem(Long idItem){
	 	  ItemLista itemLista=manager.find(ItemLista.class, idItem);
	 	  return itemLista;
	}
	
	
}
