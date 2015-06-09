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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=72 ;

--
-- Схема на данните от таблица `answers`
--

INSERT INTO `answers` (`id`, `question_id`, `correct`, `answer`, `index`) VALUES
(34, 10, 1, 'Bring presents.', 0),
(35, 10, 0, 'Steal cookies.', 1),
(36, 10, 0, 'Sleep on christmass.', 2),
(37, 10, 0, 'Run maratons.', 3),
(38, 11, 0, 'In Australia', 0),
(39, 11, 1, 'In Europe.', 1),
(40, 11, 0, 'In America', 2),
(41, 11, 0, 'In Asia', 3),
(42, 12, 0, 'H2O3', 0),
(43, 12, 0, 'H02', 1),
(44, 12, 1, 'H2O', 2),
(45, 12, 0, 'C2O', 3),
(46, 13, 1, '681', 0),
(47, 13, 0, '788', 1),
(48, 13, 0, '10298', 2),
(49, 13, 0, '689', 3),
(50, 14, 0, 'Brasil', 0),
(51, 14, 0, 'Italy', 1),
(52, 14, 1, 'Germany', 2),
(53, 14, 0, 'Bulgaria', 3),
(54, 15, 0, 'Yes', 0),
(55, 15, 1, 'No', 1),
(56, 16, 0, 'Yes', 0),
(57, 16, 1, 'No', 1),
(58, 17, 1, 'Yes', 0),
(59, 17, 0, 'No', 1),
(60, 18, 1, 'Yes', 0),
(61, 18, 0, 'No', 1),
(62, 18, 0, 'I do not know', 2),
(63, 18, 0, 'Get lost', 3),
(64, 19, 1, 'indeed', 0),
(65, 19, 0, 'yes', 1),
(66, 19, 0, 'no', 2),
(67, 19, 0, 'kill', 3),
(68, 20, 0, 'yes', 0),
(69, 20, 0, 'yes yes', 1),
(70, 20, 0, 'yes ', 2),
(71, 20, 1, 'yes', 3);

-- --------------------------------------------------------

--
-- Структура на таблица `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `reports` int(11) DEFAULT '0',
  `quiz_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Схема на данните от таблица `questions`
--

INSERT INTO `questions` (`id`, `question`, `user_id`, `reports`, `quiz_id`) VALUES
(10, 'What Santa do?', 27, 0, NULL),
(11, 'Where is Bulgaria?', 27, 0, NULL),
(12, 'Which one is water?', 27, 0, NULL),
(13, 'When is Bulgaria founded?', 27, 0, NULL),
(14, 'Who won the world cup in 2014?', 27, 0, NULL),
(15, 'Very hard question?', 27, 0, NULL),
(16, 'Very hard question?', 27, 0, NULL),
(17, 'Very hard question?', 27, 0, NULL),
(18, 'Very hard question?', 27, 0, NULL),
(19, 'Good questions?', 33, 0, 6),
(20, 'Another one bites the dust?', 33, 0, 6);

-- --------------------------------------------------------

--
-- Структура на таблица `quiz`
--

CREATE TABLE IF NOT EXISTS `quiz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Схема на данните от таблица `quiz`
--

INSERT INTO `quiz` (`id`, `userId`, `password`, `name`, `identifier`) VALUES
(1, 33, 'e10adc3949ba59abbe56e057f20f883e', 'my quiz', 'myquiz'),
(2, 33, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'myquizname', 'myquizaa'),
(4, 33, 'e7d707a26e7f7b6ff52c489c60e429b1', 'tr', 'trtr'),
(5, 33, 'e10adc3949ba59abbe56e057f20f883e', 'trtrtr', 'trtrtr'),
(6, 33, 'e10adc3949ba59abbe56e057f20f883e', 'name', 'eha');

-- --------------------------------------------------------

--
-- Структура на таблица `quiz_history`
--

CREATE TABLE IF NOT EXISTS `quiz_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `quiz_id` int(11) NOT NULL,
  `success_rate` decimal(5,0) NOT NULL,
  `wrong_answered` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
  `number_answered` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=34 ;

--
-- Схема на данните от таблица `user`
--

INSERT INTO `user` (`id`, `username`, `nickname`, `points`, `password`, `number_answered`) VALUES
(27, 'testuser@gmail.com', 'john', 17, 'e10adc3949ba59abbe56e057f20f883e', 24),
(28, 'bambuk@gmail.com', 'Bartador', 14, '7b71d9fdfe5b15a2d1a4968c195f93ae', 26),
(29, 'gogogog@gmail.com', 'Gorgoroth', 71, 'sdfdsf', 27),
(30, 'robokob@gmail.com', 'robokob', 0, 'e10adc3949ba59abbe56e057f20f883e', 0),
(31, 'someemail@gmail.com', 'sam', 0, 'e10adc3949ba59abbe56e057f20f883e', 0),
(32, 'someeamil@gmail.com', 'some', 0, 'e10adc3949ba59abbe56e057f20f883e', 0),
(33, 'kredit@gmail.com', 'kredit', 20, 'e10adc3949ba59abbe56e057f20f883e', 31);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Схема на данните от таблица `user_answered`
--

INSERT INTO `user_answered` (`id`, `user_id`, `question_id`) VALUES
(1, 30, 10),
(2, 30, 11),
(3, 30, 12),
(4, 30, 13),
(5, 31, 10),
(6, 31, 11),
(7, 31, 12),
(8, 31, 13),
(9, 33, 10),
(10, 33, 11),
(11, 33, 12),
(12, 33, 13),
(13, 33, 14),
(14, 33, 16),
(15, 33, 17),
(16, 33, 18);

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
