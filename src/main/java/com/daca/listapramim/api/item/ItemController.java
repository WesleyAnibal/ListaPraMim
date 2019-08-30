package com.daca.listapramim.api.item;

import com.daca.listapramim.api.item.DTO.ItemIO;
import com.daca.listapramim.api.item.DTO.ItemInput;
import com.daca.listapramim.api.item.DTO.ItemOutput;
import com.daca.listapramim.api.item.itemExceptions.ItemDuplicateException;
import com.daca.listapramim.api.precos.DTO.PrecoIO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.lang.reflect.Type;
import java.util.List;


@RestController
@RequestMapping("/api/item")
@Api(tags = "Item")
@CrossOrigin
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class.getSimpleName());

    private ItemIO itemIO;
    private PrecoIO precoIO;
    private ItemService itemService;

    @Autowired
    public ItemController(ItemIO itemIO, ItemService itemService, PrecoIO precoIO) {
        this.itemIO = itemIO;
        this.itemService = itemService;
        this.precoIO = precoIO;
    }

    @PostMapping({"/",""})
    @ApiOperation(value = "Create Item")
    public ResponseEntity create(@Valid @RequestBody ItemInput itemInput){
        LOGGER.info("Criando Item");
        Item item = this.itemIO.mapTo(itemInput);
        try {
            this.itemService.create(item);
            LOGGER.info("Item criado");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataIntegrityViolationException e){
            throw new ItemDuplicateException();
        }


    }

    @ApiOperation(value =  "Get a Item")
    @GetMapping({"/{id}/", "/{id}"})
    public ItemOutput show(@PathVariable("id") Long id){
        LOGGER.info("Show item by id " +id);
        Item item = this.itemService.show(id);
        ItemOutput output =this.itemIO.mapTo(item);
        return output;
    }

    @ApiOperation(value =  "Get all Item")
    @GetMapping({"/", ""})
    public List<ItemOutput> index(@RequestParam(required = false) String categoria, @RequestParam(required = false) String nome){
        LOGGER.info("Index Itens");
        Type type = new TypeToken<List<ItemOutput>>() {}.getType();
        if(categoria != null && nome != null){
            LOGGER.info("Index Order Itens  By Name and Categoria");
            return this.itemIO.toList(this.itemService.indexByNomeAndCategoria(nome, categoria), type);
        }else if(categoria != null){
            LOGGER.info("Index Order Itens");
            return this.itemIO.toList(this.itemService.indexByOrderCategory(categoria), type);
        }else if(nome != null){
            LOGGER.info("Index Order Itens  By name");
            return this.itemIO.toList(this.itemService.indexFilterByName(nome), type);
        }
        return this.itemIO.toList(this.itemService.indexByOrderName(), type);
    }

    @ApiOperation(value = "Update Item")
    @PutMapping({"/{id}/","/{id}"})
    public ResponseEntity<?> update(@Min(value = 1) @PathVariable("id") Long id,
                                    @Valid @RequestBody ItemInput itemInput){
        Item item = this.itemIO.mapTo(itemInput);
        LOGGER.info("Atualizando item");
        this.itemService.update(id, item);
        LOGGER.info("Item com id "+id+" Atualizado");
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete Item")
    @DeleteMapping({"/{id}/","/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        LOGGER.info("Excluindo Item com id "+id);
        this.itemService.delete(id);
        LOGGER.info("Item com id "+id+" excluído");
        return ResponseEntity.ok().build();
    }



}