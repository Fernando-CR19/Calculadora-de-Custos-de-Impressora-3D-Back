-- Material

ALTER TABLE material ADD COLUMN usuario_id INT;

UPDATE material SET usuario_id = (SELECT id FROM usuario WHERE papel = 'ADMIN');

ALTER TABLE material ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE material ADD CONSTRAINT fk_material_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

ALTER TABLE material DROP CONSTRAINT "material_nome_key";

CREATE UNIQUE INDEX "uq_material_usuario_nome" ON "material" ("usuario_id", (lower(nome)));

-- Consumível

ALTER TABLE consumivel ADD COLUMN usuario_id INT;

UPDATE consumivel SET usuario_id = (SELECT id FROM usuario WHERE papel = 'ADMIN');

ALTER TABLE consumivel ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE consumivel ADD CONSTRAINT  fk_consumivel_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

ALTER TABLE consumivel DROP CONSTRAINT "consumivel_nome_key";

CREATE UNIQUE INDEX "uq_consumivel_usuario_nome" ON "consumivel" ("usuario_id", (lower(nome)));

-- Impressora

ALTER TABLE impressora ADD COLUMN usuario_id INT;

UPDATE impressora SET usuario_id = (SELECT id FROM usuario WHERE papel = 'ADMIN');

ALTER TABLE impressora ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE impressora ADD CONSTRAINT fk_impressora_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

ALTER TABLE impressora DROP CONSTRAINT "impressora_nome_key";

CREATE UNIQUE INDEX "uq_impressora_usuario_nome" ON "impressora" ("usuario_id", (lower(nome)));

-- Filamento

ALTER TABLE filamento ADD COLUMN usuario_id INT;

UPDATE filamento SET usuario_id = (SELECT id FROM usuario WHERE papel = 'ADMIN');

ALTER TABLE filamento ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE filamento ADD CONSTRAINT  fk_filamento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

-- Nível desperdicio

ALTER TABLE nivel_desperdicio ADD COLUMN usuario_id INT;

UPDATE nivel_desperdicio SET usuario_id = (SELECT id from usuario WHERE papel = 'ADMIN');

ALTER TABLE nivel_desperdicio ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE nivel_desperdicio ADD CONSTRAINT fk_nivel_desperdicio_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

DROP INDEX "uq_nivel_desperdicio_nome_ativo";

DROP INDEX "uq_nivel_desperdicio_percentual_ativo";

CREATE UNIQUE INDEX "uq_nivel_desperdicio_usuario_nome" ON "nivel_desperdicio" ("usuario_id", (lower(nome))) WHERE deleted = false;

CREATE UNIQUE INDEX "uq_nivel_desperdicio_usuario_percentual" ON "nivel_desperdicio" ("usuario_id", "percentual") WHERE deleted = false;

-- Margem lucro

ALTER TABLE margem_lucro ADD COLUMN usuario_id INT;

UPDATE margem_lucro SET usuario_id = (SELECT id from usuario WHERE papel = 'ADMIN');

ALTER TABLE margem_lucro ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE margem_lucro ADD CONSTRAINT fk_margem_lucro_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

DROP INDEX "uq_margem_lucro_nome_ativo";

DROP INDEX "uq_margem_lucro_percentual_ativo";

CREATE UNIQUE INDEX "uq_margem_lucro_usuario_nome" ON "margem_lucro" ("usuario_id", (lower(nome))) WHERE deleted = false;

CREATE UNIQUE INDEX "uq_margem_lucro_usuario_percentual" ON "margem_lucro" ("usuario_id", "percentual") WHERE deleted = false;

-- Energia

ALTER TABLE energia ADD COLUMN usuario_id INT;

UPDATE energia SET usuario_id = (SELECT id from usuario WHERE papel = 'ADMIN');

ALTER TABLE energia ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE energia ADD CONSTRAINT fk_energia_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

CREATE UNIQUE INDEX "uq_energia_usuario" ON "energia" ("usuario_id");

-- Acabamento

ALTER TABLE acabamento ADD COLUMN usuario_id INT;

UPDATE acabamento SET usuario_id = (SELECT id from usuario WHERE papel = 'ADMIN');

ALTER TABLE acabamento ALTER COLUMN usuario_id SET NOT NULL;

ALTER TABLE acabamento ADD CONSTRAINT fk_acabamento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

CREATE UNIQUE INDEX "uq_acabamento_usuario" ON "acabamento" ("usuario_id");
