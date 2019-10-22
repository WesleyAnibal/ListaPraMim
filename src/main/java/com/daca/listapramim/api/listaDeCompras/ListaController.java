package com.daca.listapramim.api.listaDeCompras;

import com.daca.listapramim.api.compra.DTO.CompraIO;
import com.daca.listapramim.api.compra.DTO.CompraOutput;
import com.daca.listapramim.api.compra.Compra;
import com.daca.listapramim.api.item.DTO.ItemIO;
import com.daca.listapramim.api.item.ItemService;
import com.daca.listapramim.api.listaDeCompras.DTO.*;
import com.daca.listapramim.api.precos.MapaDePreco;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lista")
@Api(tags = "Lista de Compra")
@CrossOrigin
public class ListaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListaController.class.getSimpleName());

    @Autowired
    private ListaIO listaIO;
    @Autowired
    private ListaService listaService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemIO itemIO;

    @Autowired
    private CompraIO compraIO;

    @PreAuthorize("hasAuthority('" + "LC" + "')")
    @PostMapping({"/", ""})
    @ApiOperation(value = "Create Lista De Compras")
    public ResponseEntity create( @Valid @RequestBody EstrategiaInput input) {
        if(input.getEstrategiaId() != null){
            LOGGER.info("Geração Automática Lista de Compras com a Estratégia "+ input.getEstrategiaId());
            return this.generateLista(input);
        }else{
            LOGGER.info("Criando Lista de Compras");
            ListaDeCompra listaDeCompra = this.listaIO.mapTo(new ListaInput(input.getDescricao()));
            listaDeCompra.setCreatedAt(LocalDateTime.now());
            this.listaService.create(listaDeCompra);
            LOGGER.info("Lista de Compras criada");
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasAuthority('" + "LC" + "')")
    @GetMapping({"/{id}/", "/{id}"})
    @ApiOperation(value = "Get A Listas De Compras")
    public ListaOutput show(@Min(value = 1) @PathVariable("id") Long id) {
            ListaDeCompra lista = this.listaService.show(id);
            LOGGER.info("Show Lista de compras com id " + id);
            return this.listaIO.mapTo(lista);
    }

    @PreAuthorize("hasAuthority('" + "LC" + "')")
    @GetMapping({"/", ""})
    @ApiOperation(value = "Get All Listas De Compras")
    public List<ListaOutput> index(@RequestParam(required = false) String descricao) {
        Type type = new TypeToken<List<ListaOutput>>() {
        }.getType();
        List<ListaDeCompra> listas = new ArrayList<ListaDeCompra>();
        if(descricao != null){
            listas = this.listaService.getByDescricao(descricao);
            LOGGER.info("Index All Lista de compras By Descrição");
        }else{
            listas = this.listaService.index();
            LOGGER.info("Index All Lista de compras");
        }
        return this.listaIO.toList(listas, type);
    }

    @PreAuthorize("hasAuthority('" + "LC" + "')")
    @ApiOperation(value = "Update Lista De Compras")
    @PutMapping({"/{id}/", "/{id}"})
    public ResponseEntity<?> update(@Min(value = 1) @PathVariable("id") Long id,
                                    @Valid @RequestBody ListaInput listaInput) {
        ListaDeCompra lista = this.listaIO.mapTo(listaInput);
        LOGGER.info("Atualizando Lista de compras");
        lista.setUpdatedAt(LocalDateTime.now());
        this.listaService.update(id, lista);
        LOGGER.info("Lista de compras com id " + id + " Atualizado");
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('" + "LC" + "')")
    @ApiOperation(value = "Delete Lista De Compras")
    @DeleteMapping({"/{id}/", "/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        LOGGER.info("Excluindo lista de compras com id " + id);
        this.listaService.delete(id);
        LOGGER.info("Lista de compras com id " + id + " excluído");
        return ResponseEntity.ok().build();
    }

    /*@GetMapping({"/{id}/itens/", "{id}/itens"})
    @ApiOperation(value = "Get Itens From Lista de Compras")
    public List<ItemOutput> getItensLista(@Min(value = 1) @PathVariable("id") Long id) {
        ListaDeCompra lista = this.listaService.show(id);
        Type type = new TypeToken<List<ItemOutput>>() {
        }.getType();
        List<Item> itens = this.listaService.getAllItemsByLista(lista);
        LOGGER.info("Get itens da lista de compras com id " + id);
        return this.itemIO.toList(itens, type);
    }*/

    private ResponseEntity generateLista(EstrategiaInput input){
        ListaDeCompra lista = this.listaIO.mapTo(input);
        lista.setCreatedAt(LocalDateTime.now());
        this.listaService.estrategia(input.getEstrategiaId(), lista, input.getItemId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('" + "LC" + "')")
    @GetMapping({"/sugestao/{id}/", "/sugestao/{id}"})
    @ApiOperation(value = "Get Total Prices in all Establishments")
    public List<CarrinhoOutput> melhorEstabelecimento(@PathVariable("id") Long id){
        List<CarrinhoOutput> saida = new ArrayList<>();
        ListaDeCompra lista = this.listaService.show(id);
        List<Compra> compras = lista.getCompras();
        List<String> locais = new ArrayList<>();
        for (Compra compra: compras) {
            for (MapaDePreco preco: compra.getItem().getPrecos()) {
                if(!locais.contains(preco.getLocal())){
                    locais.add(preco.getLocal());
                }
            }
        }

        for (String local: locais) {
            CarrinhoOutput carrinhoOutput = new CarrinhoOutput();
            carrinhoOutput.setLocal(local);
            List<Compra> com = this.listaService.getItemByLocal(compras, local);
            Type type = new TypeToken<List<CompraOutput>>() {}.getType();
            carrinhoOutput.setLista(this.compraIO.toList(com, type));
            Double total = 0.0;
            for (Compra compra: com) {
                for (MapaDePreco preco: compra.getItem().getPrecos()) {
                    if(preco.getLocal().equalsIgnoreCase(local)){
                        total += preco.getPreco()*compra.getQtd();
                    }
                }
            }
            carrinhoOutput.setTotal(total);
            saida.add(carrinhoOutput);
        }
        return saida;
    }



}
