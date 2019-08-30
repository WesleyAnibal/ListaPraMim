package com.daca.listapramim.api.listaDeCompras;

import com.daca.listapramim.api.compra.Compra;
import com.daca.listapramim.api.compra.CompraService;
import com.daca.listapramim.api.item.Item;
import com.daca.listapramim.api.listaDeCompras.listaExceptions.ListaDeCompraNotFoundException;
import com.daca.listapramim.api.listaDeCompras.listaExceptions.ListaDeComprasDuplicateException;
import com.daca.listapramim.api.precos.MapaDePreco;
import com.daca.listapramim.api.utils.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaService extends GenericService<Long, ListaDeCompra, ListaRepository> {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private CompraService compraService;

    public List<ListaDeCompra> index(){
        return this.listaRepository.findAll();
    }

    public void create(ListaDeCompra listaDeCompra) {
        try {
            this.listaRepository.save(listaDeCompra);
        }catch (DataIntegrityViolationException e){
            throw new ListaDeComprasDuplicateException();
        }
    }


    @ReadOnlyProperty
    public ListaDeCompra show(Long id){
        if(!this.listaRepository.existsById(id)){
            throw new ListaDeCompraNotFoundException(id);
        }
        try{
            ListaDeCompra lista = this.listaRepository.findById(id).orElseThrow(RuntimeException::new);
            return lista;
        }catch (IllegalArgumentException iae){
            throw new RuntimeException("");
        }
    }

    public ListaDeCompra update(Long id, ListaDeCompra lista){
        if(!this.listaRepository.existsById(id)){
            throw new ListaDeCompraNotFoundException(id);
        }
        lista.setId(id);
        return this.listaRepository.save(lista);
    }

    public void delete(Long id){
        if(!this.listaRepository.existsById(id)){
            throw new ListaDeCompraNotFoundException(id);
        }
        this.listaRepository.deleteById(id);
    }

    //Pesquisas da lista de compras
    public List<ListaDeCompra> indexFilterByDescricao(String descricao){
        return this.listaRepository.findAllByDescricaoContainingIgnoreCase(descricao);
    }

    public List<Item> getAllItemsByLista(ListaDeCompra lista){
        List<Item> itens = new ArrayList<Item>();
        for (Compra compra: lista.getCompras()) {
            itens.add(compra.getItem());
        }

        return itens;
    }

    public List<ListaDeCompra> getByDescricao(String descricao){
        return this.listaRepository.findAllByDescricao(descricao);
    }



    public void estrategia(Long estrategia, ListaDeCompra lista, Long itemId){
        ListaDeCompra oldLista = null;
        if(estrategia == 1){
            oldLista = this.listaRepository.ultimaLista();
        }else if(estrategia == 2){
            List<Compra> compras = this.compraService.getByItemId(itemId);
            oldLista = compras.get(compras.size()-1).getListaDeCompra();
        }

        List<Compra> compras = new ArrayList<Compra>();
        for (Compra compra: oldLista.getCompras()) {
            compras.add(new Compra(compra.getItem(), lista, compra.getQtd()));
        }
        lista.setCompras(compras);
        this.create(lista);
    }


    public List<Compra> getItemByLocal(List<Compra> compras, String local){
        List<Compra> comprasSaida = new ArrayList<>();
        for (Compra compra: compras) {
            for (MapaDePreco preco: compra.getItem().getPrecos()) {
                if(preco.getLocal().equalsIgnoreCase(local)){
                    comprasSaida.add(compra);
                }
            }
        }
        return comprasSaida;
    }

}
