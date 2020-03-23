-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2019. Már 31. 16:08
-- Kiszolgáló verziója: 10.1.38-MariaDB
-- PHP verzió: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `quiz`
--
CREATE DATABASE IF NOT EXISTS `quiz` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci;
USE `quiz`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `alkategoriamagyarazat`
--

CREATE TABLE `alkategoriamagyarazat` (
  `katAzon` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `fokatid` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `katLeiras` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `pelda` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `alkategoriamagyarazat`
--

INSERT INTO `alkategoriamagyarazat` (`katAzon`, `fokatid`, `katLeiras`, `pelda`) VALUES
('kressz 1', 'Kressz', '1. tananyag', NULL),
('nyelvtan', 'angol', 'nyelvtan', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `diakered`
--

CREATE TABLE `diakered` (
  `diakid` int(11) NOT NULL,
  `fokatid` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `alkatid` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `kerdesidsorrend` varchar(150) COLLATE utf8_hungarian_ci NOT NULL,
  `elertpontszamsorrend` varchar(150) COLLATE utf8_hungarian_ci NOT NULL,
  `diakvalaszai` varchar(500) COLLATE utf8_hungarian_ci NOT NULL,
  `beadasidopont` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `diakered`
--

INSERT INTO `diakered` (`diakid`, `fokatid`, `alkatid`, `kerdesidsorrend`, `elertpontszamsorrend`, `diakvalaszai`, `beadasidopont`) VALUES
(16, 'Kressz', 'kressz 1', '20', '2', 'igen', '2019-03-30 17:36:10');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `felhasznalok`
--

CREATE TABLE `felhasznalok` (
  `id` int(11) NOT NULL,
  `felhasznaloNev` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `teljesNev` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `jelszo` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `szint` int(11) NOT NULL DEFAULT '1',
  `tipus` int(11) NOT NULL DEFAULT '0',
  `joValaszDb` int(11) NOT NULL DEFAULT '0',
  `rosszValaszDb` int(11) NOT NULL DEFAULT '0',
  `aktiv` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `felhasznalok`
--

INSERT INTO `felhasznalok` (`id`, `felhasznaloNev`, `teljesNev`, `jelszo`, `szint`, `tipus`, `joValaszDb`, `rosszValaszDb`, `aktiv`) VALUES
(13, 'admin', 'Lovas Bertalan', '21232f297a57a5a743894ae4a801fc3', 1, 2, 0, 0, 0),
(16, 'diak', 'tanulo', '71e18038a663ec2680b7c0af92aa68a1', 1, 0, 0, 0, 0),
(25, 'tanar', 'tanár', 'd47b837e163f20c78e38a99467b9ccc', 1, 1, 0, 0, 0);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `fokategoriamagyarazat`
--

CREATE TABLE `fokategoriamagyarazat` (
  `katAzon` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `katLeiras` varchar(255) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `fokategoriamagyarazat`
--

INSERT INTO `fokategoriamagyarazat` (`katAzon`, `katLeiras`) VALUES
('angol', 'angol nyelvi quiz'),
('Kressz', 'Kressz tanfolyamhoz készült főkategória');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `kerdes`
--

CREATE TABLE `kerdes` (
  `id` int(11) NOT NULL,
  `foKategoria` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `alKategoria` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `tipus` int(11) NOT NULL,
  `kerdesSzovege` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `valasz` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `valaszlehetosegek` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `pontszam` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `kerdes`
--

INSERT INTO `kerdes` (`id`, `foKategoria`, `alKategoria`, `tipus`, `kerdesSzovege`, `valasz`, `valaszlehetosegek`, `pontszam`) VALUES
(7, 'angol', 'nyelvtan', 0, 'It is bad luck if a black cat crosses the street ;in front of; you.', 'in front of', '', 1),
(8, 'angol', 'nyelvtan', 0, 'Gold is(value) ;more valuable; than silver.', 'more valuable', '', 1),
(9, 'angol', 'nyelvtan', 0, 'George is (fast);the fastest; runner in his class.', 'the fastest', '', 2),
(10, 'angol', 'nyelvtan', 1, 'Which of these animals is <u>not</u> a mammal?', 'mammal4.jpg', 'mammal1.jpg;mammal2.jpg;mammal3.jpg;mammal4.jpg', 1),
(11, 'angol', 'nyelvtan', 1, 'Which of these pictures is the best description for present continuous?', 'presentC1.jpg', 'presentC1.jpg;presentC2.jpg;presentC3.jpg;presentC4.jpg', 3),
(12, 'angol', 'nyelvtan', 1, 'Which of these pictures is a gadget?', 'gadget1.jpg', 'gadget1.jpg;gadget2.jpg;gadget3.jpg;gadget4.jpg', 3),
(13, 'angol', 'nyelvtan', 2, 'What do you see on the picture?;coffee.jpg', 'coffee', '', 1),
(14, 'angol', 'nyelvtan', 2, 'What do you see on the picture?;cake.jpg', 'cake', '', 1),
(15, 'angol', 'nyelvtan', 3, 'Can I help you?', 'Segíthetek?; Segíthetek önnek?;Segíthetek neked?', '', 2),
(16, 'angol', 'nyelvtan', 3, 'Ez <b>egy</b> macska.', 'It is a cat.', '', 1),
(17, 'angol', 'nyelvtan', 4, 'What is the color of the winner\'s medal?', 'gold', '', 1),
(18, 'angol', 'nyelvtan', 4, 'You can take pictures or record some video with it. What is this?', 'camera', '', 1),
(19, 'angol', 'nyelvtan', 4, 'What has a face and two hands but no arms or legs?', 'clock', '', 5),
(20, 'Kressz', 'kressz 1', 2, 'Ön a kormánykerékkel ábrázolt gépkocsit vezeti. Van-e elsőbbségadási kötelezettsége? (igen/nem);1216.jpg', 'igen;Igen', '', 2);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `naplo`
--

CREATE TABLE `naplo` (
  `bejelentkezesdatum` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `felhasznid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `alkategoriamagyarazat`
--
ALTER TABLE `alkategoriamagyarazat`
  ADD UNIQUE KEY `katAzon` (`katAzon`);

--
-- A tábla indexei `diakered`
--
ALTER TABLE `diakered`
  ADD KEY `alkatid` (`alkatid`),
  ADD KEY `diakid` (`diakid`),
  ADD KEY `fokatid` (`fokatid`);

--
-- A tábla indexei `felhasznalok`
--
ALTER TABLE `felhasznalok`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `felhasznaloNev` (`felhasznaloNev`);

--
-- A tábla indexei `fokategoriamagyarazat`
--
ALTER TABLE `fokategoriamagyarazat`
  ADD UNIQUE KEY `katAzon` (`katAzon`);

--
-- A tábla indexei `kerdes`
--
ALTER TABLE `kerdes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `alKategoria` (`alKategoria`),
  ADD KEY `foKategoria` (`foKategoria`);

--
-- A tábla indexei `naplo`
--
ALTER TABLE `naplo`
  ADD KEY `felhasznid` (`felhasznid`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `felhasznalok`
--
ALTER TABLE `felhasznalok`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT a táblához `kerdes`
--
ALTER TABLE `kerdes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `diakered`
--
ALTER TABLE `diakered`
  ADD CONSTRAINT `diakered_ibfk_1` FOREIGN KEY (`alkatid`) REFERENCES `alkategoriamagyarazat` (`katAzon`),
  ADD CONSTRAINT `diakered_ibfk_2` FOREIGN KEY (`diakid`) REFERENCES `felhasznalok` (`id`),
  ADD CONSTRAINT `diakered_ibfk_3` FOREIGN KEY (`fokatid`) REFERENCES `fokategoriamagyarazat` (`katAzon`);

--
-- Megkötések a táblához `kerdes`
--
ALTER TABLE `kerdes`
  ADD CONSTRAINT `kerdes_ibfk_1` FOREIGN KEY (`alKategoria`) REFERENCES `alkategoriamagyarazat` (`katAzon`),
  ADD CONSTRAINT `kerdes_ibfk_2` FOREIGN KEY (`foKategoria`) REFERENCES `fokategoriamagyarazat` (`katAzon`);

--
-- Megkötések a táblához `naplo`
--
ALTER TABLE `naplo`
  ADD CONSTRAINT `naplo_ibfk_1` FOREIGN KEY (`felhasznid`) REFERENCES `felhasznalok` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
