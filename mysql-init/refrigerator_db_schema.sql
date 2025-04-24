CREATE TABLE IF NOT EXISTS `fridge_items` (
   `id` binary(16) NOT NULL,
   `best_before_date` date NOT NULL,
   `category` varchar(255) DEFAULT NULL,
   `created_at` datetime(6) NOT NULL,
   `last_updated_at` datetime(6) DEFAULT NULL,
   `name` varchar(255) NOT NULL,
   `notes` varchar(255) DEFAULT NULL,
   `quantity` int DEFAULT NULL,
   `time_stored` datetime(6) NOT NULL,
   `unit` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO fridge_items (id, best_before_date, category, created_at, last_updated_at, name, notes, quantity, time_stored, unit)
VALUES (UUID_TO_BIN(UUID()), '2025-04-30', 'DAIRY', NOW(), NOW(), 'Kiselo mleko', 'nije prokislo!', 300, NOW(), 'MILLILITRES');

INSERT INTO fridge_items (id, best_before_date, category, created_at, last_updated_at, name, notes, quantity, time_stored, unit)
VALUES (UUID_TO_BIN(UUID()), '2025-05-09', 'FRUITS_AND_VEGETABLES', NOW(), NOW(), 'Breskve', 'najbolje voce!', 2, NOW(), 'KILOGRAMS');

INSERT INTO fridge_items (id, best_before_date, category, created_at, last_updated_at, name, notes, quantity, time_stored, unit)
VALUES (UUID_TO_BIN(UUID()), '2025-04-25', 'PROTEINS', NOW(), NOW(), 'Junece meso', 'najbolje meso!', 500, NOW(), 'GRAMS');