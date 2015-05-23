-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Време на генериране: 
-- Версия на сървъра: 5.5.32
-- Версия на PHP: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- БД: `quiz_db`
--
CREATE DATABASE IF NOT EXISTS `quiz_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `quiz_db`;

-- --------------------------------------------------------

--
-- Структура на таблица `answers`
--

CREATE TABLE IF NOT EXISTS `answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `correct` tinyint(1) NOT NULL,
  `answer` text NOT NULL,
  `index` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

--
-- Схема на данните от таблица `answers`
--

INSERT INTO `answers` (`id`, `question_id`, `correct`, `answer`, `index`) VALUES
(14, 5, 0, 'No', 0),
(15, 5, 1, 'YES', 1),
(16, 5, 0, 'nO', 2),
(17, 5, 0, 'NO', 3),
(18, 6, 1, 'Dumbo', 0),
(19, 6, 0, 'Bambi', 1),
(20, 6, 0, 'Tarzan', 2),
(21, 6, 0, 'Maugli', 3),
(22, 7, 1, 'Sled esenta', 0),
(23, 7, 0, 'Sled malko', 1),
(24, 7, 0, 'Sled men', 2),
(25, 7, 0, 'Sled teb', 3),
(26, 8, 0, 'a', 0),
(27, 8, 1, 'b', 1),
(28, 8, 0, 'c', 2),
(29, 8, 0, 'd', 3);

-- --------------------------------------------------------

--
-- Структура на таблица `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `reports` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Схема на данните от таблица `questions`
--

INSERT INTO `questions` (`id`, `question`, `user_id`, `reports`) VALUES
(5, 'Will It Work?', 3, 2),
(6, 'Koi e slon.', 5, 0),
(7, 'Koga shte stane zima.', 4, 0),
(8, 'asdasdasd', 4, 0);

-- --------------------------------------------------------

--
-- Структура на таблица `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `points` int(11) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Схема на данните от таблица `user`
--

INSERT INTO `user` (`id`, `username`, `nickname`, `points`, `password`) VALUES
(2, 'wefre@sd.mm', 'mmm', 17, '47bce5c74f589f4867dbd57e9ca9f808'),
(3, 'test@test.ts', 'test', 19, '098f6bcd4621d373cade4e832627b4f6'),
(4, 'spas@bg.bg', 'spas', 19, '91bb4c5bf3d78bdc254931152a7a18b2'),
(5, 'spas@spas.spas', 'spas2', 17, '91bb4c5bf3d78bdc254931152a7a18b2'),
(6, 'sonic_89@abv.bg', 'asasa', 0, 'e10adc3949ba59abbe56e057f20f883e'),
(7, 'sonic_89@abv.bg', 'spass', 0, 'fcea920f7412b5da7be0cf42b8c93759'),
(8, 'blqqq@asas.as', 'klaaa', 0, 'e10adc3949ba59abbe56e057f20f883e'),
(9, 'heki@gmail.com', 'heki', 0, 'e10adc3949ba59abbe56e057f20f883e'),
(10, 'silva@gmail.com', 'silva', 0, 'e10adc3949ba59abbe56e057f20f883e');

-- --------------------------------------------------------

--
-- Структура на таблица `user_answered`
--

CREATE TABLE IF NOT EXISTS `user_answered` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Схема на данните от таблица `user_answered`
--

INSERT INTO `user_answered` (`id`, `user_id`, `question_id`) VALUES
(3, 4, 6),
(4, 4, 5),
(5, 3, 6),
(6, 3, 7),
(7, 3, 8),
(10, 9, 5),
(11, 10, 5);

--
-- Ограничения за дъмпнати таблици
--

--
-- Ограничения за таблица `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`);

--
-- Ограничения за таблица `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ограничения за таблица `user_answered`
--
ALTER TABLE `user_answered`
  ADD CONSTRAINT `user_answered_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  ADD CONSTRAINT `user_answered_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
