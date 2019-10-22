package com.daca.listapramim.api.precos;

import com.daca.listapramim.api.precos.DTO.PrecoIO;
import com.daca.listapramim.api.precos.DTO.PrecoInput;
import com.daca.listapramim.api.precos.DTO.PrecoOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/preco")
@Api(tags = "Preco")
@CrossOrigin
public class PrecoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrecoController.class.getSimpleName());

    private PrecoIO precoIO;
    private PrecoService precoService;

    @Autowired
    public PrecoController( PrecoIO precoIO, PrecoService precoService) {
        this.precoIO = precoIO;
        this.precoService = precoService;
    }

    @PreAuthorize("hasAuthority('" + "PR" + "')")
    @PostMapping({"/",""})
    @ApiOperation(value = "Create Preco")
    public ResponseEntity create(@Valid @RequestBody PrecoInput itemInput){
        LOGGER.info("Criando Preco");
        MapaDePreco item = this.precoIO.mapTo(itemInput);
        this.precoService.create(item);
        LOGGER.info("Preco criado");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*@ApiOperation(value =  "Get all Precos By Item")
    @GetMapping({"/item/{itemId}/", "/item/{itemId}"})
    public List<PrecoOutput> indexByItemId(@PathVariable("itemId") Long itemId){
        LOGGER.info("Index Preco");
        Type type = new TypeToken<List<PrecoOutput>>() {}.getType();
        return this.precoIO.toList(this.precoService.getByItemId(itemId), type);
    }*/

    @PreAuthorize("hasAuthority('" + "PR" + "')")
    @ApiOperation(value = "Get a Preco")
    @GetMapping({"/{id}/","/{id}"})
    public PrecoOutput show(@PathVariable("id") Long id){
        LOGGER.info("Show Preco");
        return this.precoIO.mapTo(this.precoService.show(id));
    }

    @PreAuthorize("hasAuthority('" + "PR" + "')")
    @ApiOperation(value = "Update Preco")
    @PutMapping({"/{id}/","/{id}"})
    public ResponseEntity<?> update(@Min(value = 1) @PathVariable("id") Long id,
                                    @Valid @RequestBody PrecoInput precoInput){
        MapaDePreco preco = this.precoIO.mapTo(precoInput);
        LOGGER.info("Atualizando preco");
        this.precoService.update(id, preco);
        LOGGER.info("Preco com id "+id+" Atualizado");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAuthority('" + "PR" + "')")
    @ApiOperation(value = "Delete Preco")
    @DeleteMapping({"/{id}/","/{id}"})
    public ResponseEntity<?> delete(@Min(value = 1) @PathVariable("id") Long id){
        LOGGER.info("Deletando preco");
        this.precoService.delete(id);
        LOGGER.info("Preco com id "+id+" Deletado");
        return ResponseEntity.status(HttpStatus.OK).build();
    }





}
