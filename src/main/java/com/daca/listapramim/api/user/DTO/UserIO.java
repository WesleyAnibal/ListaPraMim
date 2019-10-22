package com.daca.listapramim.api.user.DTO;


import com.daca.listapramim.api.precos.DTO.PrecoInput;
import com.daca.listapramim.api.precos.DTO.PrecoOutput;
import com.daca.listapramim.api.precos.MapaDePreco;
import com.daca.listapramim.api.user.Privilege;
import com.daca.listapramim.api.user.UserModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("userIO")
public class UserIO {


    private ModelMapper modelMapper;


    public UserIO() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.addConverter(userInputConverter);
        this.modelMapper.addConverter(userOutputConverter);
    }

    final Converter<UserInput, UserModel> userInputConverter = new Converter<UserInput, UserModel>() {
        @Override
        public UserModel convert(MappingContext<UserInput, UserModel> context) {
            UserInput userInput = context.getSource();
            UserModel user = new UserModel();

            Set<Privilege> arr = new HashSet<Privilege>();
            for (String p: userInput.getPermissao()) {
                arr.add(Privilege.fromKey(p));
            }
            user.setPrivileges(arr);
            user.setPassword(userInput.getPassword());
            user.setEmail(userInput.getEmail());
            user.setNome(userInput.getNome());
            return user;
        }
    };


    final Converter<UserModel, UserOutput> userOutputConverter = new Converter<UserModel, UserOutput>() {
        @Override
        public UserOutput convert(MappingContext<UserModel, UserOutput> mappingContext) {
            UserModel user = mappingContext.getSource();

            UserOutput output = new UserOutput();

            output.setEmail(user.getEmail());
            output.setNome(user.getNome());
            return output;
        }
    };

    public UserModel mapTo(UserInput userInput){
        return this.modelMapper.map(userInput, UserModel.class);
    }

    public UserOutput mapTo(UserModel user){
        return this.modelMapper.map(user, UserOutput.class);
    }

    public List<UserOutput> toList(List<UserModel> user, Type type){
        return this.modelMapper.map(user, type);
    }

}
