-- À exécuter à la main dans MySQL Workbench, après un premier démarrage de l'application
-- (les tables sont créées par Hibernate au démarrage)
USE lab3_db;
SET NAMES utf8mb4;

INSERT INTO categories (name) VALUES
    ('Informatique'),
    ('Accessoires');

INSERT INTO products (name, price, category_id) VALUES
    ('Ordinateur portable', 899.99, 1),
    ('Écran 24 pouces', 159.00, 1),
    ('Souris sans fil', 19.90, 2),
    ('Clavier mécanique', 49.99, 2);

INSERT INTO orders (order_date) VALUES
    (NOW());

INSERT INTO order_items (order_id, product_id, quantity) VALUES
    (1, 1, 1),
    (1, 3, 2);
