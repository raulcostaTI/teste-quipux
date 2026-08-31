# Playlist API

API REST desenvolvida com Spring Boot para gerenciamento de playlists e músicas, com persistência em banco H2 em memória e autenticação HTTP Basic.

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring Web (MVC)
- Spring Data JPA
- Spring Security (autenticação HTTP Basic)
- Banco de dados H2 (em memória)
- Lombok
- Bean Validation
- JUnit 5 / Mockito
- Maven

## Requisitos

- JDK 25
- Maven (ou use o wrapper `mvnw` incluído no projeto)

## Como executar

Clone o repositório e, na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

> A porta 8080 precisa estar livre.

## Autenticação

Os endpoints (exceto `OPTIONS` e o console do H2) exigem autenticação HTTP Basic:

- **Usuário:** `quipux.admin`
- **Senha:** `123456`

## Banco de dados (H2)

O console do H2 fica disponível em:

```
http://localhost:8080/h2-console
```

Configuração de conexão:

- **JDBC URL:** `jdbc:h2:mem:playlistdb`
- **Usuário:** `sa`
- **Senha:** *(em branco)*

O schema é criado/atualizado automaticamente (`spring.jpa.hibernate.ddl-auto=update`) e os dados são perdidos a cada reinicialização, pois o banco é em memória.

## CORS

Requisições de `http://localhost:4200` (front-end Angular) são liberadas por padrão, configuráveis em `SecurityConfig`.

## Endpoints

Todos os endpoints partem do prefixo `/lists`.

| Método | Endpoint                 | Descrição                                    |
|--------|---------------------------|-----------------------------------------------|
| POST   | `/lists`                  | Cria uma nova playlist                         |
| GET    | `/lists`                  | Lista todas as playlists                       |
| GET    | `/lists/{listName}`       | Busca uma playlist pelo nome                   |
| DELETE | `/lists/{listName}`       | Remove uma playlist pelo nome                  |
| POST   | `/lists/{listName}/musicas` | Adiciona uma música a uma playlist existente |

### Exemplo — Criar playlist

**POST** `/lists`

```json
{
  "nome": "Rock Clássico",
  "descrição": "As melhores do rock"
}
```

### Exemplo — Adicionar música

**POST** `/lists/Rock Clássico/musicas`

```json
{
  "titulo": "Bohemian Rhapsody",
  "artista": "Queen",
  "album": "A Night at the Opera",
  "ano": "1975",
  "genero": "Rock"
}
```

### Modelo de resposta — Playlist

```json
{
  "id": 1,
  "nome": "Rock Clássico",
  "descrição": "As melhores do rock",
  "músicas": [
    {
      "id": 1,
      "titulo": "Bohemian Rhapsody",
      "artista": "Queen",
      "album": "A Night at the Opera",
      "ano": "1975",
      "genero": "Rock"
    }
  ]
}
```

## Regras de negócio

- O nome da playlist é obrigatório e deve ser único.
- O título da música é obrigatório ao ser adicionada a uma playlist.
- Ao excluir uma playlist, todas as suas músicas são removidas em cascata.

## Tratamento de erros

| Situação                                   | Status HTTP |
|---------------------------------------------|-------------|
| Nome de playlist inválido ou duplicado       | 400 Bad Request |
| Título de música inválido                    | 400 Bad Request |
| Playlist não encontrada                      | 404 Not Found |

## Coleção do Postman

O repositório inclui a coleção `Quipux - Playlist - API.postman_collection.json` com exemplos de todas as requisições, pronta para importação no Postman.

## Testes

Para rodar os testes automatizados (JUnit + Mockito):

```bash
./mvnw test
```
