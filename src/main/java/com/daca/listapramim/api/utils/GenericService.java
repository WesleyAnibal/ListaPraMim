package com.daca.listapramim.api.utils;

import javax.management.RuntimeErrorException;

import com.daca.listapramim.api.item.Item;
import com.daca.listapramim.api.item.itemExceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericService<ID, MODEL extends Model<ID>, REPOSITORY extends JpaRepository<MODEL,ID>> {
	
	@Autowired
	protected REPOSITORY repository;
	
	public MODEL createTransaction (MODEL model) {
		try {
			return repository.save(model);
		} catch (Exception e) {
			throw new RuntimeErrorException(null, e.getMessage());
		}
	}

    public MODEL show(ID id){
        try{
            return this.repository.findById(id).orElseThrow(RuntimeException::new);
        }catch (IllegalArgumentException iae){
            throw new RuntimeException("");
        }
    }

    public MODEL update(ID id, MODEL model){
        model.setId(id);
        return this.repository.save(model);
    }

    public void delete(ID id){
        this.repository.deleteById(id);
    }
	
}
