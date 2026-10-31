# Calculadora de Preços para Impressão 3D

API REST para precificar peças impressas em 3D, construída para o dia a dia da **3DGeekStore**, minha loja de impressão 3D. Ela substitui a planilha que eu usava para calcular o preço de cada peça, com cada etapa do cálculo visível na resposta.

> 🔗 **Front-end (Angular):** _em desenvolvimento_

O projeto tem três objetivos:

- **Aprendizado:** foi o meu primeiro projeto em Java e Spring Boot.
- **Uso real:** é a calculadora que eu uso para precificar as peças que vendo.
- **Portfólio:** um problema real, com regras de negócio de verdade.

---

## Sumário

- [Stack](#stack)
- [Como o preço é calculado](#como-o-preço-é-calculado)
- [Endpoints](#endpoints)
- [Decisões técnicas](#decisões-técnicas)
- [Como rodar localmente](#como-rodar-localmente)
- [Próximos passos](#próximos-passos)

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 4 (Web MVC, Data JPA, Validation) |
| ORM | Hibernate |
| Banco de dados | PostgreSQL (Neon) |
| Migrations | Flyway |
| Segurança | Spring Security e JWT (java-jwt) |
| Outros | Lombok, Docker |
| Deploy | Render (API) e Neon (banco) |
| Testes | JUnit e Mockito _(em andamento)_ |

---

## Como o preço é calculado

O orçamento (`POST /orcamentos`) recebe a impressora, os filamentos usados (com as gramas de cada um), o tempo de impressão, o tempo de acabamento, o nível de desperdício, a margem de lucro e os consumíveis da peça. A partir disso:

1. **Filamentos:** preço do kg × gramas ÷ 1000, para cada filamento da peça.
2. **Desperdício:** um percentual sobre o custo dos filamentos (purga, troca de cor).
3. **Energia:** (potência da impressora com o material + potência do AMS) × horas ÷ 1000 = kWh, × preço do kWh (fatura ÷ consumo).
4. **Depreciação:** (custo de aquisição ÷ vida útil em horas) × horas de impressão.
5. **Custo base** = filamentos + desperdício + energia + depreciação.
6. **Acabamento:** minutos de trabalho manual (pintura, por exemplo) × valor por minuto.
7. **Consumíveis:** soma do preço unitário dos itens usados (sacola, pitão de chaveiro...).
8. **Custo total** = custo base + acabamento + consumíveis.
9. **Valor sugerido** = custo total × (1 + margem de lucro ÷ 100).

A resposta traz **cada uma dessas etapas**, e não só o valor final, para que qualquer número estranho possa ser rastreado até a sua origem.

<details>
<summary>Exemplo de requisição e resposta</summary>

```json
POST /orcamentos
{
  "uuidImpressora": "<uuid-da-impressora>",
  "filamentos": [
    { "uuid": "<uuid-do-filamento>", "gramas": 18.21 }
  ],
  "horasDeImpressao": 1,
  "minutosDeImpressao": 18,
  "uuidNivelDesperdicio": "<uuid-do-nivel-de-desperdicio>",
  "uuidMargemLucro": "<uuid-da-margem-de-lucro>",
  "horasDeAcabamento": 0,
  "minutosDeAcabamento": 0,
  "uuidsConsumiveis": [
    "<uuid-da-sacola>",
    "<uuid-do-pitao>"
  ]
}
```

```json
{
  "filamentos": [
    { "cor": "Preto", "material": "PETG", "gramas": 18.21, "custo": 1.33 }
  ],
  "tempoImpressaoHoras": 1.3000,
  "consumoEnergiaKwh": 0.1023,
  "custoFilamentos": 1.33,
  "custoDesperdicio": 0.04,
  "custoEnergia": 0.10,
  "custoDepreciacao": 0.18,
  "custoBase": 1.65,
  "custoAcabamento": 0.00,
  "custoConsumiveis": 0.97,
  "custoTotal": 2.62,
  "fatorLucro": 3.5000,
  "valorSugerido": 9.18
}
```

</details>

---

## Endpoints

Todos os recursos são identificados por **UUID**. Os ids internos do banco nunca são expostos.

Com exceção do login, **todas as rotas exigem autenticação**. O token devolvido pelo login deve ser enviado no cabeçalho de cada requisição:

```
Authorization: Bearer <token>
```

| Recurso | Rotas |
|---|---|
| Autenticação | `POST` `/auth/login` _(pública)_ |
| Materiais | `GET` `POST` `/materiais` · `PATCH` `DELETE` `/materiais/{uuid}` |
| Filamentos | `GET` `POST` `/filamentos` · `PUT` `DELETE` `/filamentos/{uuid}` |
| Consumíveis | `GET` `POST` `/consumiveis` · `PUT` `DELETE` `/consumiveis/{uuid}` |
| Níveis de desperdício | `GET` `POST` `/niveis-desperdicio` · `PUT` `DELETE` `/niveis-desperdicio/{uuid}` |
| Margens de lucro | `GET` `POST` `/margens-lucro` · `PUT` `DELETE` `/margens-lucro/{uuid}` |
| Impressoras | `GET` `POST` `/impressoras` · `PUT` `DELETE` `/impressoras/{uuid}` |
| Potência por material | `GET` `POST` `/impressoras/{uuidImpressora}/materiais` · `PUT` `DELETE` `/impressoras/{uuidImpressora}/materiais/{uuidMaterial}` |
| Energia | `GET` `PUT` `/energia` |
| Acabamento | `GET` `PUT` `/acabamento` |
| Orçamento | `POST` `/orcamentos` |

### Respostas de erro

Todos os erros seguem o mesmo formato:

```json
{
  "timestamp": "2026-10-28T18:51:28.15-03:00",
  "status": 400,
  "error": "Dados enviados estão incorretos",
  "message": "Um ou mais campos estão inválidos",
  "path": "/orcamentos",
  "errors": [
    { "property": "filamentos", "message": "Informe ao menos um filamento" }
  ]
}
```

| Status | Quando |
|---|---|
| 400 | Validação dos campos, JSON mal formatado ou parâmetro inválido na URL |
| 401 | Token ausente, inválido ou expirado, ou credenciais incorretas no login |
| 404 | Recurso ou rota não encontrados |
| 405 | Método HTTP não suportado na rota |
| 409 | Nome (ou combinação) já cadastrado |
| 422 | Regra de negócio violada (por exemplo, filamentos de materiais diferentes no mesmo orçamento) |
| 500 | Erro inesperado, registrado em log, sem expor detalhes internos |

---

## Decisões técnicas

**Organização por feature.** Cada recurso tem o seu pacote (`material`, `filamento`, `impressora`...), com entidade, repositório, service, controller e DTOs juntos. O orçamento não tem tabela: ele só consulta os outros recursos e calcula.

**UUID público, id interno.** Toda tabela tem um `id` inteiro para as relações e um `uuid` para a API. Assim, ninguém consegue percorrer registros trocando `/materiais/1` por `/materiais/2`.

**Soft delete.** Os registros apagados recebem `deleted = true` e continuam no banco.
- Em material, filamento, consumível e impressora, recadastrar um nome apagado **reativa** o registro antigo, mantendo o mesmo uuid.
- Apagar um material apaga (em soft delete) os filamentos dele, numa única transação.
- As potências de uma impressora são mantidas quando ela é apagada, para voltarem junto se ela for reativada.

**Índices únicos parciais.** Em níveis de desperdício e margens de lucro, nome e percentual são únicos **apenas entre os registros não apagados** (`CREATE UNIQUE INDEX ... WHERE deleted = false`), e o nome é comparado sem diferenciar maiúsculas. Isso evita que um registro apagado continue "ocupando" um valor.

**O banco é a fonte da verdade.** O schema é criado e versionado pelo Flyway, e o Hibernate só confere se as entidades batem com ele (`ddl-auto=validate`). As regras importantes existem nas duas camadas: na validação da API e em restrições do banco (`CHECK`, `UNIQUE`, chaves estrangeiras).

**Precisão monetária.** Todos os valores usam `BigDecimal` e `numeric` no banco, nunca `double`. Os cálculos intermediários guardam 4 casas decimais, as multiplicações vêm antes das divisões, e o arredondamento para centavos acontece uma única vez, na resposta.

**Valores derivados não são guardados.** Preço unitário de consumível, preço do kWh, depreciação por hora e fatores de margem são calculados por métodos das próprias entidades, a partir dos dados originais.

**Validação na entrada.** Os DTOs usam Bean Validation, inclusive em listas aninhadas, em elementos de conjuntos e em regras entre campos (como exigir que o tempo de impressão seja maior que zero). Campos de texto são normalizados (espaços removidos) antes da validação.

**Erros centralizados.** Um `@RestControllerAdvice` converte cada tipo de exceção numa resposta HTTP padronizada, inclusive as exceções do próprio Spring.

**Autenticação com JWT, sem sessão.** O login confere a senha (guardada como hash BCrypt) e devolve um token assinado com HMAC-SHA256, com o uuid do usuário, o papel dele e uma validade de duas horas. Um filtro próprio, executado antes dos controllers, valida o token em cada requisição e confere se o usuário continua ativo, para que um usuário bloqueado perca o acesso na hora, mesmo com um token dentro da validade.
- Usuário inexistente e senha errada recebem **a mesma** resposta, para não revelar quais usernames existem.
- Os usernames são únicos sem diferenciar maiúsculas, e os de contas apagadas continuam reservados.
- O primeiro administrador é criado na inicialização da aplicação, a partir de variáveis de ambiente, sem credenciais no código ou nas migrations.
- As respostas 401 seguem o mesmo formato dos outros erros, mesmo sendo geradas pelo Spring Security, antes de a requisição chegar aos controllers.

---

## Como rodar localmente

**Requisitos:** Java 25 e um banco PostgreSQL (local ou no Neon).

1. Clone o repositório:

   ```bash
   git clone <url-do-repositorio>
   cd <pasta-do-projeto>
   ```

2. Crie um arquivo `.env` na raiz com a URL JDBC do banco e a chave de assinatura dos tokens:

   ```
   DATABASE_URL=jdbc:postgresql://<host>/<banco>?user=<usuario>&password=<senha>&sslmode=require
   JWT_SECRET=<chave-aleatoria>
   ```

   A chave precisa ter pelo menos 256 bits. Uma forma de gerar:

   ```bash
   openssl rand -base64 32
   ```

   O arquivo é lido automaticamente pela aplicação e está no `.gitignore`. Sem a `JWT_SECRET`, a aplicação não sobe.

3. Rode a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

   Na primeira execução, o Flyway cria todas as tabelas. A API sobe na porta `8080`, ou na porta definida na variável `PORT`.

### Com Docker

```bash
docker build -t calculadora-3d .
docker run -p 8080:8080 -e DATABASE_URL="<url-jdbc>" -e JWT_SECRET="<chave>" calculadora-3d
```

---

## Próximos passos

**Técnicos**
- [ ] Testes automatizados (JUnit e Mockito), começando pelo cálculo do orçamento
- [ ] Front-end em Angular
- [ ] Limite de tentativas de login, contra ataques de força bruta

**Regras de negócio**
- [ ] Custo de manutenção por hora da impressora (bicos, placa, peças de desgaste)
- [ ] Tempo fixo de preparação por impressão (fatiar, limpar mesa, remover suportes)
- [ ] Separar a purga (em gramas, informada pelo fatiador) da taxa de falha (percentual sobre o custo inteiro)
- [ ] Preço de mercado opcional no orçamento, mostrando o lucro e a margem reais
- [ ] Peças com mais de um material no mesmo orçamento
- [ ] Catálogo de impressoras com as potências do fabricante já preenchidas
- [ ] Vários usuários, com cadastros independentes

---

## Autor

**Fernando** — Desenvolvedor Full Stack

[LinkedIn](https://www.linkedin.com/in/fernando-chaves-rodrigues-/) · [GitHub](https://github.com/Fernando-CR19)
