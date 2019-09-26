### Features

- Cadastrar itens de forma simples e direta, oferecendo cadastros de diferentes tipos de itens;
- Cadastrar diferentes preços com diferentes locais para os itens;
- Criar Lista de compras, para facilitar na hora de comprar seus produtos;
- Diferentes formas de pesquisas, locais que possuem os itens, preço final da lista de compras para diferentes locais;
- Sugestão do melhor estabelecimento para realizar suas compras;
- Geração automática de lista de compras, diminuindo o trabalho do usuário;


# Lista pra mim©

![](https://github.com/niltonmng/ListaPraMim/blob/master/lista180.jpg)


O Lista pra mim©, é um app que vai revolucionar a forma como você faz compras. Quanto mais você usar o Lista pra mim, mais rápido vai ser gerar novas listas de compras. E você ainda pode economizar anotando preços e locais de compra. Depois é só deixar o  Lista pra mim© indicar o melhor lugar para você fazer suas compras.


# Informações Técnicas

### Diagramas ER: 
   - https://www.lucidchart.com/invitations/accept/eaf9114e-bf8c-49b3-8296-0c5f8c4bc83a
### Diagrama de Classes:
   - https://www.lucidchart.com/invitations/accept/d0daa6c8-9fab-421d-ba6c-c69ff9eb6f0a
### Explicação Arquitetural:
   - https://link.medium.com/L70dBduyDZ
### Como usar?
   1) Clone o projeto para sua máquina
   2) Abra o terminal e entre na pasta do projeto
   3) execute: `$ mvn clean && mvn spring-boot:run`
   
### Acesso aos EndPoints?
   - Acesse, pelo seu navegador: 
      http://localhost:8080/swagger-ui.html#/
   - Login: Todas as rotas estão bloqueadas, só podendo ter acesso após autenticação, usando JWT.
   ##### Estratégia de Autenticação:
   A autenticação foi baseada em JWT, se forma que a cada requisição é preciso um token válido para se ter acesso, todas os endpoints estão bloqueados, somente o de login aberto.
   Toda a segurança do sistema foi feita usando-se Spring Security, de forma com que pra todas as requisições, 
foi criada um interceptador, onde verifica o token e o usuário que está usando a API. Inicialmente temos somente um usuário cadastrado no sistema, criado diretamente no BD, criado no InitialData, usando-se da JPA para isso. Todas as senhas antes de irem para o BD, são criptografadas, usando a biblioteca Bcrypt, e só depois de criptografada, a senha é salva no BD, pra isso foi usado uma classe de Listener para o usuário, para que antes da criação do usuário ou edição da senha do usuário, a senha seja sempre criptografada, antes de chegar ao BD.
   ##### Para autenticação:
         1) acesse a rota /api/user/login com um Json contendo o Password e o Username.
         2) A credenciais estando corretas, acesse o Response headers, e copie o token, contido em "authotization:".
         3) Esse token será o responsável por todo acesso a API.
            3.1 - O token terá validade durante 6 horas.
         4) Adicione esse token ao Authorize da sua aplicação segundo o seguinte padrão:
            Bearer <Token>
