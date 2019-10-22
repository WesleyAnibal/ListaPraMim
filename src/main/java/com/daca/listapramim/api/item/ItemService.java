package com.daca.listapramim.api.item;

import com.daca.listapramim.api.item.Categoria;
import com.daca.listapramim.api.item.Item;
import com.daca.listapramim.api.item.ItemRepository;
import com.daca.listapramim.api.item.itemExceptions.ItemDuplicateException;
import com.daca.listapramim.api.item.itemExceptions.ItemNotFoundException;
import com.daca.listapramim.api.user.Payload;
import com.daca.listapramim.api.user.UserRepository;
import com.daca.listapramim.api.user.UserService;
import com.daca.listapramim.api.utils.GenericService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService extends GenericService<Long, Item, ItemRepository> {

    @Autowired
    private ItemRepository itemRepository;

	@Autowired
	private UserService userRepository;

	private static Gson gson = new GsonBuilder().create();


	public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> index() {
        return this.itemRepository.findAll();
    }

    public List<Item> indexByOrderName() {
		String usuarioLogado = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Payload t = gson.fromJson(usuarioLogado, Payload.class);

        return this.itemRepository.findAllByUserModel(this.userRepository.show(t.getId()));
    }

    public List<Item> indexByOrderCategory(String categoria) {
        Categoria cat = Categoria.fromName(categoria);
        Sort sort = new Sort(Sort.Direction.ASC, "nome");
        List<Item> itens = this.itemRepository.findAllByCategoria(cat, sort);
        return itens;
    }

    public List<Item> indexByNomeAndCategoria(String nome, String categoria) {
        Categoria cat = Categoria.fromName(categoria);
        return this.itemRepository.findAllByNomeAndAndCategoria(nome, cat);
    }

    public List<Item> indexFilterByName(String nome) {
        return this.itemRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    public void create(Item item) {
        try {
            String usuarioLogado = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Payload t = gson.fromJson(usuarioLogado, Payload.class);
            item.setUserModel(this.userRepository.show(t.getId()));
			this.itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new ItemDuplicateException();
        }
    }


    @ReadOnlyProperty
    public Item show(Long id) {
        if (!this.itemRepository.existsById(id)) {
            throw new ItemNotFoundException(id);
        }
        try {
            Item item = this.itemRepository.findById(id).orElseThrow(RuntimeException::new);
            return item;
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("");
        }
    }

    public Item update(Long id, Item item) {
        if (!this.itemRepository.existsById(id)) {
            throw new ItemNotFoundException(id);
        }
        item.setId(id);
        return this.itemRepository.save(item);
    }

    public void delete(Long id) {
        if (!this.itemRepository.existsById(id)) {
            throw new ItemNotFoundException(id);
        }
        this.itemRepository.deleteById(id);
    }
}
