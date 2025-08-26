CREATE DATABASE IF NOT EXISTS cs6310_team045
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;
USE cs6310_team045;

DROP TABLE Users;
CREATE TABLE Users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_ VARCHAR(255) NOT NULL,
    role_ VARCHAR(50) NOT NULL
);

INSERT INTO Users (username, password_, role_)
VALUES ('regular_user', 'Test123', 'user');

INSERT INTO Users (username, password_, role_)
VALUES ('admin_user', 'Test123', 'admin');

DROP TABLE Pokemon;
CREATE TABLE Pokemon (
    pokemon_id INT AUTO_INCREMENT PRIMARY KEY,
    name_ VARCHAR(50) NOT NULL,
    type_ VARCHAR(20) NOT NULL,
    hitPoints INT NOT NULL,
    attackList JSON,
    defenseList JSON
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Abra', 'Psychic', 25, 
    JSON_ARRAY('Mega Punch', 'Mega Kick', 'Seismic Toss', 'Psychic'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Bulbasaur', 'Grass', 25, 
    JSON_ARRAY('Tackle', 'Vine Whip', 'Razor Leaf', 'Leaf Storm'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Butterfree', 'Bug', 40, 
    JSON_ARRAY('Poison', 'Gust', 'Whirlwind', 'Solar Beam'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Charmander', 'Fire', 25, 
    JSON_ARRAY('Attack', 'Scratch', 'Ember', 'Flamethrower'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Ditto', 'Normal', 35, 
    JSON_ARRAY('Transform'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Geodude', 'Rock', 25, 
    JSON_ARRAY('Tackle', 'Rock Throw', 'Earth Quake', 'Rock Slide'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Jigglypuff', 'Normal', 25, 
    JSON_ARRAY('Sharpie', 'Sing', 'Pound', 'Double Slap'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Lapras', 'Water', 25, 
    JSON_ARRAY('Mist', 'Ice Shard', 'Ice Beam', 'Sheer Cold'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Mew', 'Psychic', 25, 
    JSON_ARRAY('Charm', 'Pound', 'Imprison', 'Psychic'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Pikachu', 'Electric', 25, 
    JSON_ARRAY('Growl', 'Tail Whip', 'Thunder Shock', 'Thunder'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Slowpoke', 'Psychic', 25, 
    JSON_ARRAY('Tackle', 'Curse', 'Confusion', 'Psychic'), 
    JSON_ARRAY('Heal Pulse', 'Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Snorlax', 'Normal', 40, 
    JSON_ARRAY('Rest', 'Snore'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

INSERT INTO Pokemon (name_, type_, hitPoints, attackList, defenseList)
VALUES 
('Squirtle', 'Water', 25, 
    JSON_ARRAY('Attack', 'Tackle', 'Water Gun', 'Hydro Pump'), 
    JSON_ARRAY('Endure', 'Block', 'Protect')
);

DROP TABLE Moves;
CREATE TABLE Moves (
    move_id INT AUTO_INCREMENT PRIMARY KEY,
    name_ VARCHAR(50) NOT NULL UNIQUE,
    powerLevel INT NOT NULL,
    type_ ENUM('attack', 'defense') NOT NULL
);

INSERT INTO Moves (name_, powerLevel, type_)
VALUES
('Mega Punch', 1, 'attack'),
('Mega Kick', 2, 'attack'),
('Seismic Toss', 3, 'attack'),
('Psychic', 6, 'attack'),
('Tackle', 1, 'attack'),
('Vine Whip', 2, 'attack'),
('Razor Leaf', 3, 'attack'),
('Leaf Storm', 6, 'attack'),
('Poison', 0, 'attack'),
('Gust', 1, 'attack'),
('Whirlwind', 2, 'attack'),
('Solar Beam', 6, 'attack'),
('Attack', 1, 'attack'),
('Scratch', 2, 'attack'),
('Ember', 3, 'attack'),
('Flamethrower', 6, 'attack'),
('Transform', 0, 'attack'),
('Rock Throw', 2, 'attack'),
('Earth Quake', 3, 'attack'),
('Rock Slide', 6, 'attack'),
('Sharpie', 0, 'attack'),
('Sing', 1, 'attack'),
('Pound', 2, 'attack'),
('Double Slap', 10, 'attack'),
('Mist', 1, 'attack'),
('Ice Shard', 2, 'attack'),
('Ice Beam', 3, 'attack'),
('Sheer Cold', 6, 'attack'),
('Charm', 0, 'attack'),
('Imprison', 2, 'attack'),
('Growl', 1, 'attack'),
('Tail Whip', 2, 'attack'),
('Thunder Shock', 3, 'attack'),
('Thunder', 6, 'attack'),
('Curse', 2, 'attack'),
('Confusion', 3, 'attack'),
('Rest', 0, 'attack'),
('Snore', 6, 'attack'),
('Water Gun', 3, 'attack'),
('Hydro Pump', 6, 'attack'),
('Endure', 1, 'defense'),
('Block', 2, 'defense'),
('Protect', 3, 'defense'),
('Heal Pulse', 0, 'defense');

