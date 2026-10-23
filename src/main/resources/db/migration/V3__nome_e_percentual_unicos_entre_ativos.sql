ALTER TABLE nivel_desperdicio DROP CONSTRAINT "nivel_desperdicio_nome_key";

CREATE UNIQUE INDEX uq_nivel_desperdicio_nome_ativo ON "nivel_desperdicio" (lower("nome")) WHERE deleted = false;

CREATE UNIQUE INDEX uq_nivel_desperdicio_percentual_ativo ON "nivel_desperdicio" ("percentual") WHERE deleted = false;

ALTER TABLE margem_lucro DROP CONSTRAINT "margem_lucro_nome_key";

CREATE UNIQUE INDEX uq_margem_lucro_nome_ativo ON "margem_lucro" (lower("nome")) WHERE deleted = false;

CREATE UNIQUE INDEX uq_margem_lucro_percentual_ativo ON "margem_lucro" ("percentual") WHERE deleted = false;
